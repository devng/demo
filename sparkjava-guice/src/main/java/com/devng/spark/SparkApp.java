package com.devng.spark;

import static com.devng.spark.dto.JsonUtil.*;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.devng.spark.controller.ErrorController;
import com.devng.spark.controller.RequestInfoController;
import com.devng.spark.controller.SparkController;
import com.devng.spark.controller.UserController;
import com.devng.spark.dto.MessageDto;

import spark.servlet.SparkApplication;

public class SparkApp implements SparkApplication {

	private final List<SparkController> appControllers = new ArrayList<>();

	private String helloMessage;

	// Note that we cannot use constructor injection in the SparApplication class because then we cannot create a servlet context

	@Inject
	void setHelloMessage(@Named("hello-message") final String helloMessage) {
		this.helloMessage = helloMessage;
	}

	@Inject
	void setUserController(final UserController userController) {
		appControllers.add(userController);
	}

	@Inject
	void setRequestInfoController(final RequestInfoController requestInfoController) {
		appControllers.add(requestInfoController);
	}

	@Inject
	void setErrorController(final ErrorController errorController) {
		appControllers.add(errorController);
	}

	@Override
	public void init() {
		appControllers.forEach(SparkController::init);

		get("/hello", (req, res) -> new MessageDto(helloMessage), json());

		afterAfter((req, res) -> res.type("application/json"));
	}
}
