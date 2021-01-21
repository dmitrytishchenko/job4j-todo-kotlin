package ru.job4j.service

import ru.job4j.model.User

interface ServiceUsers {
    fun create(user: User): User
    fun findById(id: Int): User
    fun delete(id: Int)
    fun getAllUsers(): List<User>
}