package com.cmc.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmc.auth.entity.UserRole;
import com.cmc.util.enums.EUserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

	Optional<UserRole> findByName(EUserRole name);
}
