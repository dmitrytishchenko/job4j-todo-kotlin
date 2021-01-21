package ru.job4j.service

import ru.job4j.model.Item

interface ServiceItem {
    fun save(item: Item): Item

    fun findById(id: Int): Item?

    fun findAll(): List<Item>

    fun delete(id: Int)

    fun update(item: Item)
}