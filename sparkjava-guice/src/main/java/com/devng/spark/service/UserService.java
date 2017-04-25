package com.devng.spark.service;

import java.time.LocalDate;
import java.util.List;

import com.devng.spark.dto.UserDto;

public interface UserService {

	void createUser(long id, String email, String firstName, String lastName, LocalDate birthDay);

	void createUser(UserDto user);

	List<UserDto> getUsers();

	List<UserDto> getUsersByLastName(String lastName);

	UserDto getUserById(String id);
}
