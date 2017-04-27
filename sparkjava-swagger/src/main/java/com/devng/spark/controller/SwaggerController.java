package com.devng.spark.controller;

import static com.devng.spark.dto.JsonUtil.*;
import static spark.Spark.*;

import spark.route.SwaggerRouteOverview;

public class SwaggerController {

	public SwaggerController() {
		staticFiles.location("/swaggerui");
		get("swagger.json", (request, response) -> SwaggerRouteOverview.getSwaggerJson(), json());
	}
}
