package com.devng.spark

import java.time.LocalDate

data class UserDto(val id: Long, var email: String, var firstName: String?, var lastName: String?, var birthday: LocalDate?)

data class MessageDto(var message: String) {

    constructor(message: String, vararg args: String) : this(message.format(*args))

    constructor(e: Exception) : this(e.message ?: "Error")

}