package com.cmc.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmc.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByUserName(String userName);
	Optional<User> findByUserName(String userName);
}
