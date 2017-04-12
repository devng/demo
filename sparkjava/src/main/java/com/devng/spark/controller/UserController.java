package com.devng.spark.controller;

import static com.devng.spark.dto.JsonUtil.*;
import static spark.Spark.*;

import com.devng.spark.dto.MessageDto;
import com.devng.spark.dto.UserDto;
import com.devng.spark.service.UserService;

/**
 * Handles all user related requests.
 */
public class UserController {

	public UserController(final UserService userService) {
		get("/users", (req, res) -> {
			final String lastName = req.queryParams("lastName");
			if (lastName != null && !lastName.isEmpty()) {
				return userService.getUsersByLastName(lastName);
			} else {
				return userService.getUsers();
			}
		}, json());

		post("/users", (req, res) -> {
			final String body = req.body();
			final UserDto userDto = fromJson(body, UserDto.class);
			userService.createUser(userDto);
			return userDto;
		}, json());

		get("/users/:id", (req, res) -> {
			final String id = req.params(":id");
			final UserDto user = userService.getUserById(id);
			if (user != null) {
				return user;
			}
			res.status(404);
			return new MessageDto("User with id '%s' was not found", id);
		}, json());
	}
}
