package ru.job4j.service

import ru.job4j.dao.DaoHibernate
import ru.job4j.model.Item

class ServiceDaoHbm : ServiceItem {
    private val dao: DaoHibernate = DaoHibernate()
    override fun save(item: Item) = dao.create(item)

    override fun findById(id: Int): Item? = dao.findById(id)

    override fun findAll(): List<Item> = dao.findAll()

    override fun delete(id: Int) = dao.delete(id)

    override fun update(item: Item) = dao.update(item)
}