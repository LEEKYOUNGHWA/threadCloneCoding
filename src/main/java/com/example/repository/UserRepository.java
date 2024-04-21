package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.model.User;

public interface UserRepository extends CrudRepository<User, String>{
	List<User> findAll();
	Optional<User> findByUseridAndPassword(String userId, String password);
	Optional<User> findByUserid(String userId);
	User save(User user);
}
