package com.devng.spark;

import static com.devng.spark.dto.JsonUtil.*;
import static spark.Spark.*;

import com.devng.spark.dto.MessageDto;
import com.devng.spark.dto.UserDto;
import com.devng.spark.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;

public class SparkApp {

	private static final Logger log = LoggerFactory.getLogger(SparkApp.class);

	public static void main(final String[] args) {
		final UserService userService = new UserService();
		final TemplateEngine templateEngine = new ThymeleafTemplateEngine();

		get("/hello", (req, res) -> "Hello world!", json());

		path("/rest/users", () -> {
			get("", (req, res) -> {
				final String lastName = req.queryParams("lastName");
				if (lastName != null && !lastName.isEmpty()) {
					return userService.getUsersByLastName(lastName);
				} else {
					return userService.getUsers();
				}
			}, json());

			post("", (req, res) -> {
				final String body = req.body();
				final UserDto userDto = fromJson(body, UserDto.class);
				userService.createUser(userDto);
				return userDto;
			}, json());

			get("/:id", (req, res) -> {
				final String id = req.params(":id");
				final UserDto user = userService.getUserById(id);
				if (user != null) {
					return user;
				}
				res.status(404);
				return new MessageDto("User with id '%s' was not found", id);
			}, json());
		});

		get("/users/:id", (req, res) -> {
			final String id = req.params(":id");
			final UserDto user = userService.getUserById(id);
			final ModelAndView mv = new ModelAndView(user != null ? user.asMap() : new HashMap<>(), "userprofile");
			res.type("text/html");
			return mv;
		}, templateEngine);

		notFound(toJson(new MessageDto("Not found.")));

		internalServerError(toJson(new MessageDto("Internal server error.")));

		exception(IllegalArgumentException.class, (e, req, res) -> {
			log.warn("An error occurred",  e);
			res.status(400);
			res.body(toJson(new MessageDto(e)));
		});

		afterAfter((req, res) -> {
			if (res.type() == null) {
				res.type("application/json");
			}
		});
	}
}