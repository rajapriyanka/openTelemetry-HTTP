package com.telemetry.open.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.telemetry.open.entity.User;
import com.telemetry.open.repo.UserRepo;

@Service
public class UserService {

	private final UserRepo userRepository;

	public UserService(UserRepo userRepository) {
		this.userRepository = userRepository;

	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id).filter(user -> "NO".equals(user.getIsDeleted()));
	}

	public List<User> getAllUsers() {
		return userRepository.findAll().stream().filter(user -> "NO".equals(user.getIsDeleted())).toList();
	}

}
