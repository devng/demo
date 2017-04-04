package com.devng.spark;

import static spark.Spark.*;

public class SparkApp {

    public static void main(final String[] args) {
		get("/", (req, res) -> "Hello World");
	}
}