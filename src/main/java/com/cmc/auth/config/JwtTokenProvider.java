package com.cmc.auth.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cmc.util.enums.TokenType;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${app.auth.token.secretKey}")
	private String atSecret;
	
	@Value("${app.auth.refreshToken.secretKey}")
	private String rtSecret;

	@Value("${app.auth.token.expInMilSec}")
	private long atExp;

	@Value("${app.auth.token.refreshInMilSec}")
	private long rtExp;


	
	private Key keys(TokenType tokenType) throws Exception {
		return switch(tokenType) {
		case AT ->  Keys.hmacShaKeyFor(Decoders.BASE64.decode(atSecret));
		case RT -> Keys.hmacShaKeyFor(Decoders.BASE64.decode(rtSecret));
		default -> throw new Exception("Token type is not valid");
		};
	}
	
	public String genTokenFromUsername(String userName, TokenType tokenType) {
		try {

			return Jwts.builder()
					.setSubject(userName)
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime()+(tokenType == TokenType.AT ? atExp : rtExp)))
					.signWith(keys(tokenType))
					.compact();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public String genTokenFromAuthen(Authentication authentication, TokenType tokenType) {
		UserDetailsCoreImpl userDetailsCoreImpl = (UserDetailsCoreImpl) authentication.getPrincipal();
		return genTokenFromUsername(userDetailsCoreImpl.getUsername(),tokenType);
	}
	
	public String getUsernameFromToken(String token,TokenType tokenType) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(keys(tokenType)).build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public boolean validateToken(String token,TokenType tokenType) throws Exception {
		try {
			Jwts.parserBuilder().setSigningKey(keys(tokenType)).build().parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
            log.error("Error Log :::::::: Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Error Log :::::::: Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Error Log :::::::: Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Error Log :::::::: Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("Error Log :::::::: JWT claims string is empty.");
        }
		return false;
	}
}
