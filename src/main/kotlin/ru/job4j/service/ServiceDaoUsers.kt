package ru.job4j.service

import ru.job4j.dao.UsersDAO
import ru.job4j.model.User

class ServiceDaoUsers : ServiceUsers {
    private val dao: UsersDAO = UsersDAO()
    override fun create(user: User) = dao.create(user)

    override fun findById(id: Int): User = dao.findById(id)

    override fun delete(id: Int) = dao.delete(id)

    override fun getAllUsers(): List<User> = dao.getAllUsers()
}