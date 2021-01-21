package ru.job4j.service

import ru.job4j.dao.DaoJdbc
import ru.job4j.model.Item

class ServiceDAO(private val daoJdbc: DaoJdbc) : ServiceItem {
    override fun save(item: Item): Item = daoJdbc.save(item)

    override fun findById(id: Int) = daoJdbc.findItemById(id)

    override fun findAll() = daoJdbc.getAllItem()

    override fun delete(id: Int) = daoJdbc.delete(id)

    override fun update(item: Item) = daoJdbc.update(item)
}