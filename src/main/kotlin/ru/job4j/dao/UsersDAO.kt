package ru.job4j.dao

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import ru.job4j.model.User

class UsersDAO {

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

    fun create(user: User): User = sf.tx { save(user); user }


    fun getAllUsers(): List<User> = sf select "from ru.job4j.model.User"

    private infix fun <T> SessionFactory.select(query: String): List<T> {
        val session = openSession()
        session.beginTransaction()
        val model = session.createQuery(query).list() as List<T>
        session.transaction.commit()
        session.close()
        return model
    }

    fun delete(id: Int) {
        val user = findById(id)
        sf.tx { delete(user) }
    }

    fun findById(id: Int?): User = sf.tx { get(User::class.java, id) }

    fun run() {
        try {
            val sf = getSF()
            val user1 = create(User(name = "admin", password = "admin"))
            val user2 = create(User(name = "user", password = "user"))
            println(user1)
            println(user2)
            val rsl = findById(user1.id)
            println(rsl)
            delete(rsl.id)
            val list = getAllUsers()
            for (it in list) {
                println(it)
            }
        } finally {
            destroySF()
        }
    }
}

fun main() {
    UsersDAO().run()
}