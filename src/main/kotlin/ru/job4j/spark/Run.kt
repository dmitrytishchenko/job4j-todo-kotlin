package ru.job4j.spark

import ru.job4j.dao.DaoHibernate
import ru.job4j.dao.UsersDAO
import ru.job4j.model.Item
import ru.job4j.model.User
import ru.job4j.service.ServiceDaoHbm
import ru.job4j.service.ServiceDaoUsers
import spark.Request
import spark.Spark.*

fun getTable(list: List<Item>): String {
    val table = StringBuilder()
    table.apply {
        append("<table class=\" table-bordered\">")
        append("<thead><tr>")
        append("<th scope=\"col\">#</th>")
        append("<th scope=\"col\">Name</th>")
        append("<th scope=\"col\">Description</th>")
        append("</tr></thead>")
        append("<tbody>")
        for (item in list) {
            append("<tr>")
            append("<th scope=\"row\">${item.id}</th>")
            append("<td>${item.name}</td>")
            append("<td>${item.description}</td>")
            append("</tr>")
        }
        append("</body>")

        append("</table>")
    }
    return table.toString()
}

fun Request.qp(key: String): String = this.queryParams(key)

fun main() {
    before("/") { req, resp ->
        val session = req.session()
        val user = session.attribute<User>("user")
        if (user != null) {
            resp.redirect("items")
        } else {
            resp.redirect("login")
        }
    }

    get("/login") { req, resp ->
        val name = req.qp("name")
        val password = req.qp("password")
        val dao = ServiceDaoUsers(UsersDAO())
        val existUser = dao.getAllUsers()
                .stream()
                .filter { it.name == name && it.password == password }
                .findFirst().isPresent
        if (existUser) {
            val newUser = dao.getAllUsers()
                    .stream()
                    .filter { it.name == name && it.password == password }
                    .findFirst()
            req.session().attribute("user", newUser)
            resp.redirect("/items")
        } else {
            halt(401, "Incorrect name or password")
        }
        get("/items") { req, resp ->
            getTable(ServiceDaoHbm(DaoHibernate()).findAll())
        }
    }
}




