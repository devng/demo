package com.devng.spark

import com.devng.spark.JsonUtil.fromJson
import com.devng.spark.JsonUtil.json
import com.devng.spark.JsonUtil.toJson
import spark.Route
import spark.Spark.*

fun main(args: Array<String>) {
    val userService = UserService()

    get("/hello") { req, res -> "Hello world from Kotlin and Spark!" }

    path("/users") {
        get("", Route { req, res ->
            val lastName = req.queryParams("lastName")
            if (lastName != null && !lastName!!.isEmpty()) {
                return@Route userService.getUsersByLastName(lastName)
            } else {
                return@Route userService.users
            }
        }, json())

        post("", Route { req, res ->
            val body = req.body()
            val userDto = fromJson(body, UserDto::class.java)
            userService.createUser(userDto)
            userDto
        }, json())

        get("/:id", Route { req, res ->
            val id = req.params(":id")
            val user = userService.getUserById(id)
            if (user != null) {
                return@Route user
            }
            res.status(404)
            MessageDto("User with id '%s' was not found", id)
        }, json())
    }

    notFound(toJson(MessageDto("Not found.")))

    internalServerError(toJson(MessageDto("Internal server error.")))

    exception(IllegalArgumentException::class.java) { e, req, res ->
        res.status(400)
        res.body(toJson(MessageDto(e)))
    }

    afterAfter { req, res -> res.type("application/json") }
}
