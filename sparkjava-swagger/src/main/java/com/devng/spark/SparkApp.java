package com.devng.spark;

import static com.devng.spark.dto.JsonUtil.*;
import static spark.Spark.*;

import com.devng.spark.controller.ErrorController;
import com.devng.spark.controller.SwaggerController;
import com.devng.spark.controller.UserController;
import com.devng.spark.dto.MessageDto;
import com.devng.spark.service.UserService;

public class SparkApp {

	public static void main(final String[] args) {
		new SwaggerController();
		new UserController(new UserService());
		new ErrorController();

		get("/hello", (req, res) -> new MessageDto("Hello world!"), json());

		afterAfter((req, res) -> res.type("application/json"));
	}
}