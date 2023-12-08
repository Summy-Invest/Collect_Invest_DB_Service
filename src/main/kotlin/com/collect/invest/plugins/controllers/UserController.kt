package com.collect.invest.plugins.controllers

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.WalletsDao
import com.collect.invest.dao.entity.UsersEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.userController(usersDao: UsersDao, walletsDao: WalletsDao){


    //Обработка запроса на добавление юзера
    get("/saveUser") {
        try {
            val user = call.receive<UsersEntity>()
            val userId = usersDao.saveUser(user)
            walletsDao.createWallet(userId)
            call.respond(HttpStatusCode.OK, mapOf("id" to userId))
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, e.toString())
        }
    }

    
    get("/authenticateUser/{email}/{password}") {
        try {
            val email = call.parameters["email"]!!
            val password = call.parameters["password"]!!
            println("\n " + email)
            val user = usersDao.getByEmail(email)
            if (user != null && usersDao.checkPassword(password, user.password)) {
                call.respond(HttpStatusCode.OK, mapOf("id" to user.id))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid email or password")
            }
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, "Error while processing request")
        }
    }
}
