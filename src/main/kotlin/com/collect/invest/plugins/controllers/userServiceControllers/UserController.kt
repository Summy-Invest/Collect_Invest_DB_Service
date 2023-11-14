package com.collect.invest.plugins.controllers.userServiceControllers

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.entity.UsersEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.userController(usersDao: UsersDao){


//Обработка запроса на добавление юзера
    post("/saveUser") {
        try {
            val user = call.receive<UsersEntity>()
            usersDao.saveUser(user)
            call.respond("User added")
        } catch (e: Throwable) {
            call.respond(e.toString())
        }
    }

//Обработка запроса на получение юзера по его id
    get("/authenticateUser/{email}/{password}") {
        try {
            val email = call.parameters["email"]!!
            val password = call.parameters["password"]!!
            val user = usersDao.getByEmail(email)
            if (user != null && usersDao.checkPassword(password, user.password)) {
                call.respond(mapOf("id" to user.id))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid email or password")
            }
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, "Error while processing request")
        }
    }


}
data class LoginRequest(val email: String, val password: String)
