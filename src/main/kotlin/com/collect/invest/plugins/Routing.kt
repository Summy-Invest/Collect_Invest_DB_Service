package com.collect.invest.plugins

import com.collect.invest.dao.jdbc.UsersDaoJdbc
import com.collect.invest.plugins.routes.userRotes
import com.collect.invest.service.UserService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {


    val dbUrl = System.getProperty("db.url")
    val dbUsername = System.getProperty("db.username")
    val dbPassword = System.getProperty("db.password")


    val userService = UserService(UsersDaoJdbc(dbUrl, dbUsername, dbPassword))

    routing {
        route("userService"){
            userRotes(userService)
        }
    }
}