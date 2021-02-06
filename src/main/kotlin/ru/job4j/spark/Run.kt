package ru.job4j.spark

import com.google.gson.Gson
import ru.job4j.model.Item
import ru.job4j.model.User
import ru.job4j.service.ServiceDaoHbm
import ru.job4j.service.ServiceDaoUsers
import spark.Spark.*

fun main() {
    port(8080)
    val gson = Gson()
    before("/") { req, resp ->
        val session = req.session()
        val user = session.attribute<User>("user")
        if (user != null) {
            resp.redirect("/items", 301)
        } else {
            resp.redirect("/login", 301)
        }
    }
    get("/login") { req, resp ->
        resp.type("application/json")
        val user = gson.fromJson(req.body(), User::class.java)
        if (user == null) {
            halt(401, "Input name or password")
        }
        val dao = ServiceDaoUsers()
        val existUser = dao.getAllUsers()
                .stream()
                .filter { it.name == user.name && it.password == user.password }
                .findFirst().isPresent
        if (existUser) {
            val newUser = dao.getAllUsers()
                    .stream()
                    .filter { it.name == user.name && it.password == user.password }
                    .findFirst()
            req.session().attribute("user", newUser)
            resp.redirect("/items")
        } else {
            halt(401, "Incorrect name or password")
        }
    }
    get("/items") { req, resp ->
        gson.toJson(ServiceDaoHbm().findAll())
    }
    post("/create") { req, resp ->
        ServiceDaoHbm().save(gson.fromJson(req.body(), Item::class.java))
    }
    put("/update") { req, resp ->
        ServiceDaoHbm().update(gson.fromJson(req.body(), Item::class.java))
    }
    delete("/delete/:id") { req, resp ->
        ServiceDaoHbm().delete(req.params(":id").toInt())
    }
}






