package ru.job4j.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(@Id
           @GeneratedValue(strategy = GenerationType.IDENTITY)
           val id: Int = 0,
           val name: String = "",
           val password: String = "")