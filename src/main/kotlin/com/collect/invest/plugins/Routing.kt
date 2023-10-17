package com.collect.invest.plugins

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.entity.UsersEntity
import com.collect.invest.dao.jdbc.UsersDaoJdbc
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
        post("userService/saveUser") {
            val user = call.receive<UsersEntity>()

            if (userService.validateUser(user)) {
                val processedUser = userService.processUserData(user)
                userService.saveUser(processedUser)
                call.respondText("Пользователь сохранен: ${user.name}")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Неверные данные пользователя")
            }
        }
    }
}