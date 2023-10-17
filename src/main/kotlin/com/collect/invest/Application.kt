package com.collect.invest

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.collect.invest.plugins.*

fun main() {

    System.setProperty("db.url", "jdbc:postgresql://postgresql:5432/postgres")
    System.setProperty("db.username", "postgres")
    System.setProperty("db.password", "password")

    val dbUrl = System.getProperty("db.url")
    val dbUsername = System.getProperty("db.username")
    val dbPassword = System.getProperty("db.password")


    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}