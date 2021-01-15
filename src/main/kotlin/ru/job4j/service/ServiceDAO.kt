package ru.job4j.service

import ru.job4j.dao.DaoJdbc
import ru.job4j.model.Item

class ServiceDAO(private val daoJdbc: DaoJdbc) {

    fun save(item: Item) = daoJdbc.save(item)

    fun findById(id: Int) = daoJdbc.findItemById(id)

    fun findAll() = daoJdbc.getAllItem()

    fun delete(id: Int) = daoJdbc.delete(id)

    fun update(item: Item) = daoJdbc.update(item)
}