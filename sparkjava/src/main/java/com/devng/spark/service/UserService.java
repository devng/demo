package com.devng.spark.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.devng.spark.dto.UserDto;

public class UserService {

	private final List<UserDto> allUsers;

	public UserService() {
		allUsers = new ArrayList<>();
		createUser(1, "john@example.com", "John", "Doe", LocalDate.of(1991, 03, 24));
		createUser(2, "max@mustermann.com", "Max", "Mustermann", LocalDate.of(1979, 06, 12));
		createUser(3, "cla.dor@mail.net", "Claudia", "Doris", LocalDate.of(1987, 12, 21));
	}

	public void createUser(final long id, final String email, final String firstName, final String lastName, final LocalDate birthDay) {
		final UserDto user = new UserDto();
		user.setId(id);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setBirthday(birthDay);
		createUser(user);
	}

	public void createUser(UserDto user) {
		if (user == null) {
			throw new IllegalArgumentException("Cannot create empty user.");
		}
		if (user.getId() == null || user.getId() <= 0) {
			throw new IllegalArgumentException("User id must be positive.");
		}
		if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
			throw new IllegalArgumentException("Invalid user email");
		}
		allUsers.remove(user); // removes any user with the same id from the list
		allUsers.add(user);
	}

	public List<UserDto> getUsers() {
		return Collections.unmodifiableList(allUsers);
	}

	public List<UserDto> getUsersByLastName(final String lastName) {
		if (lastName == null || lastName.isEmpty()) {
			return Collections.emptyList();
		}
		final String lastNameLower = lastName.toLowerCase().trim();
		return allUsers.stream().filter(e -> e.getLastName().toLowerCase().startsWith(lastNameLower)).collect(Collectors.toList());
	}

	public UserDto getUserById(final String id) {
		final Long idLong;
		try {
			idLong = Long.parseLong(id);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("User id must be a number.");
		}
		return allUsers.stream().filter(e -> e.getId().equals(idLong)).findFirst().orElse(null);
	}
}
