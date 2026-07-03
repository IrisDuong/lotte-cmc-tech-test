package com.cmc.auth.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cmc.auth.entity.User;
import com.cmc.auth.entity.UserRole;
import com.cmc.util.enums.EUserRole;

public class UserDetailsCoreImpl implements UserDetails{

	private Integer id;
	private String userName;
	private String password;
	private Collection<GrantedAuthority> authorities;
	
	public UserDetailsCoreImpl() {
		super();
	}
	public UserDetailsCoreImpl(Integer id, String userName, String password,Collection<GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}
	public static UserDetailsCoreImpl build(User user) {
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().getName().name()));
		return new UserDetailsCoreImpl(user.getId(), user.getUserName(), user.getPassword(), authorities);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public Integer getId() {
		return id;
	}
}
