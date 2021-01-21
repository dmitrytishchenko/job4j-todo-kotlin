package ru.job4j.dao

import ru.job4j.model.Item
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class DaoJdbc {

    private val url = "jdbc:postgresql://localhost:5432/postgres"
    private val username = "postgres"
    private val password = "password"

    private fun getConn(): Connection {
        var conn: Connection? = null
        try {
            conn = DriverManager.getConnection(url, username, password)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return conn!!
    }

    fun save(item: Item): Item {
        val sql = "insert into items(id, name, description)" +
                " values(${item.id}, '${item.name}', '${item.description}')"
        getConn().prepareStatement(sql).executeUpdate()
        return item
    }

    fun update(item: Item) {
        val sql = "update items set " +
                "name = '${item.name}', " +
                "description = '${item.description}' " +
                "where id = ${item.id}"
        getConn().prepareStatement(sql).executeUpdate()
    }

    fun getAllItem(): List<Item> {
        val list = ArrayList<Item>()
        val sql = "select * from items"
        val ps = getConn().prepareStatement(sql)
        val rs = ps.executeQuery()
        while (rs.next()) {
            list.add(
                    Item(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    )
            )
        }
        return list
    }

    fun delete(id: Int) {
        val sql = "delete from items where id = $id"
        getConn().prepareStatement(sql).executeUpdate()
    }

    fun findItemById(id: Int): Item? {
        var result: Item? = null
        val sql = "select from items where id = $id"
        val rs = getConn().prepareStatement(sql).executeQuery()
        while (rs.next()) {
            result = Item(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"))
        }
        return result
    }
}
