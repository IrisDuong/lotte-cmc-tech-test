package com.cmc.util.service;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.util.ObjectUtils;

import com.cmc.auth.config.UserDetailsCoreImpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditingImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		UserDetailsCoreImpl userDetailsCoreImpl = (UserDetailsCoreImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(Boolean.FALSE.equals(ObjectUtils.isEmpty(userDetailsCoreImpl))) {
			return Optional.of((String) userDetailsCoreImpl.getUsername());
		}
		return Optional.of("Anonymous");
	}

}