package com.cmc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cmc.auth.entity.User;
import com.cmc.auth.entity.UserRole;
import com.cmc.auth.repository.UserRepository;
import com.cmc.auth.repository.UserRoleRepository;
import com.cmc.util.enums.EUserRole;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class TestApp implements CommandLineRunner {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		if(userRepository.count() == 0 && userRoleRepository.count() == 0) {
			UserRole roleAdmin = UserRole.builder().name(EUserRole.ADMIN).build();
			UserRole roleStaff = UserRole.builder().name(EUserRole.STAFF).build();
			userRoleRepository.save(roleAdmin);
			userRoleRepository.save(roleStaff);
			
			// Create test user
			User lotteAdmin = User.builder()
					.userName("lotteAdmin")
					.password(passwordEncoder.encode("12345"))
					.role(roleAdmin)
					.build();
			userRepository.save(lotteAdmin);
			

			User lotteStaff = User.builder()
					.userName("lotteStaff")
					.password(passwordEncoder.encode("12345"))
					.role(roleStaff)
					.build();
			userRepository.save(lotteStaff);
		}
	}

}
