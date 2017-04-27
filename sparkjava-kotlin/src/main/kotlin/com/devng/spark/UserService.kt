package com.devng.spark

import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

class UserService {

    private val allUsers: MutableList<UserDto>

    init {
        allUsers = ArrayList<UserDto>()
        createUser(1, "john@example.com", "John", "Doe", LocalDate.of(1991, 3, 24))
        createUser(2, "max@mustermann.com", "Max", "Mustermann", LocalDate.of(1979, 6, 12))
        createUser(3, "cla.dor@mail.net", "Claudia", "Doris", LocalDate.of(1987, 12, 21))
    }

    fun createUser(id: Long, email: String, firstName: String?, lastName: String?, birthDay: LocalDate?) {
        val user = UserDto(id, email, firstName, lastName, birthDay)
        createUser(user)
    }

    fun createUser(user: UserDto) {
        if (user.id == null || user.id <= 0) {
            throw IllegalArgumentException("User id must be positive.")
        }
        if (user.email == null || user.email.isNullOrEmpty() || !user.email.contains("@")) {
            throw IllegalArgumentException("Invalid user email")
        }
        allUsers.remove(user) // removes any user with the same id from the list
        allUsers.add(user)
    }

    val users: List<UserDto>
        get() = Collections.unmodifiableList(allUsers)

    fun getUsersByLastName(lastName: String?): List<UserDto> {
        if (lastName == null || lastName.isEmpty()) {
            return Collections.emptyList<UserDto>()
        }
        val lastNameLower = lastName.toLowerCase().trim({ it <= ' ' })
        return allUsers.stream().filter { e -> e.lastName != null && e.lastName!!.toLowerCase().startsWith(lastNameLower) }.collect(Collectors.toList<UserDto>())
    }

    fun getUserById(id: String): UserDto {
        val idLong: Long?
        try {
            idLong = java.lang.Long.parseLong(id)
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("User id must be a number.")
        }

        return allUsers.stream().filter({ (id1) -> id1 == idLong }).findFirst().orElse(null)
    }
}
