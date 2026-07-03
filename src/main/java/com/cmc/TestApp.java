package com.cmc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cmc.auth.entity.User;
import com.cmc.auth.entity.UserRole;
import com.cmc.auth.repository.UserRepository;
import com.cmc.util.enums.EUserRole;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class TestApp implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		if(userRepository.count() == 0) {
			User lotteAdminuser = User.builder()
					.userName("lotteAdmin")
					.password(passwordEncoder.encode("12345"))
					.role(UserRole.builder().name(EUserRole.ADMIN).build())
					.build();
			userRepository.save(lotteAdminuser);
		}
	}

}
