package ru.job4j.dao

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import ru.job4j.model.Item

class DaoHibernate {
    private val registry = StandardServiceRegistryBuilder().configure().build()
    private val sf = getSF()

    private fun getSF(): SessionFactory {
        return MetadataSources(registry).buildMetadata().buildSessionFactory()
    }

    private fun destroySF() {
        StandardServiceRegistryBuilder.destroy(registry)
    }

    private fun <T> SessionFactory.tx(block: Session.() -> T): T {
        val session = openSession()
        session.beginTransaction()
        val model = session.block()
        session.transaction.commit()
        session.close()
        return model
    }

    fun create(item: Item): Item =
            sf.tx { save(item); item }

    fun update(item: Item?) {
        sf.tx { update(item) }
    }

    fun delete(id: Int) {
        val item = findById(id)
        sf.tx { delete(item) }
    }

    private infix fun <T> SessionFactory.select(query: String): List<T> {
        val session = openSession()
        session.beginTransaction()
        val model = session.createQuery(query).list() as List<T>
        session.transaction.commit()
        session.close()
        return model
    }

    fun findAll(): List<Item> = sf select "from ru.job4j.model.Item"

    fun findById(id: Int?): Item =
            sf.tx { get(Item::class.java, id) }

    fun run() {
        try {
            val sf = getSF()
            val item = create(Item(name = "Learn Hibernate"))
            println(item)
            item.name = "Learn Hibernate 5."
            update(item)
            println(item)
            val rsl = findById(item.id)
            println(rsl)
            delete(rsl.id)
            val list = findAll()
            for (it in list) {
                println(it)
            }
        } finally {
            destroySF()
        }
    }
}

fun main() = DaoHibernate().run()

