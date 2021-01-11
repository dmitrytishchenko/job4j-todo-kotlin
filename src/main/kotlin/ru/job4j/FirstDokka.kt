package ru.job4j

/**
 * This class the first application the plugin Dokka
 * @param <String> test type
 * @author Dmitry Tishchenko
 * @constructor creates with 1 arg
 * @since version 1.0
 *
 */
class FirstDokka(private val name: String) {
    /**
     * add surname
     * @return name
     */
    fun addSurname(surname: String): String {
        return "$name  $surname"
    }
}

fun main() {
    val res = FirstDokka("Dmitry").addSurname("Tishchenko")
    println(res)
}

