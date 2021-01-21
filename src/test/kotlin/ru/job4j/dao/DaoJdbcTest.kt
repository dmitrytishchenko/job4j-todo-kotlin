package ru.job4j.dao

import io.kotlintest.specs.FunSpec
import io.mockk.*
import ru.job4j.model.Item
import ru.job4j.service.ServiceDAO

class DaoJdbcTest: FunSpec({
 val dao = mockk<DaoJdbc>()
    val service = ServiceDAO(dao)
    val item = mockk<Item>()

    test("addItem") {
        every { dao.save(item) } returns item
        service.save(item)
        verify(exactly = 1) { dao.save(item) }
    }

    test("updateItem") {
        every { dao.update(item) } returns Unit
        service.update(item)
        verify(exactly = 1) { dao.update(item) }
    }

    test("deleteItem") {
        every { dao.delete(1) } returns Unit
        service.delete(1)
        verify(exactly = 1) { dao.delete(1) }
    }

    test("findItemById") {
        every { dao.findItemById(1) } returns item
        service.findById(1)
        verify(exactly = 1) { dao.findItemById(1) }
    }

    test("AllItem") {
        every { dao.getAllItem() } returns ArrayList<Item>()
        service.findAll()
        verify(exactly = 1) { dao.getAllItem() }
    }
})
