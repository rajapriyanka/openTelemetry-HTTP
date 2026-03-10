package com.telemetry.open.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telemetry.open.entity.User;



@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	List<User> findByIsDeleted(String isDeleted);

	User findByUsernameAndIsDeleted(String username, String isDeleted);

	Optional<User> findByUsername(String username);

}