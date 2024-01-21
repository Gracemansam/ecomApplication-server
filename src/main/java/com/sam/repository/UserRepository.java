package com.sam.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.model.User;

public interface UserRepository extends JpaRepository<User, Long> {



	Optional<User> findByEmail(String email);
	
	public List<User> findAllByOrderByCreatedAtDesc();

}
