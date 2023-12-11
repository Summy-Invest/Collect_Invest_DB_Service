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

    post("/saveUser") {
        try {
            val user = call.receive<UsersEntity>()
            usersDao.saveUser(user)
            val newUser = usersDao.getByEmail(user.email)
            if (newUser != null) {
                walletsDao.createWallet(newUser.id)
                call.respond(HttpStatusCode.OK, mapOf("id" to newUser.id, "name" to newUser.name))
            }else{
                call.respond(HttpStatusCode.InternalServerError, "fadofneqrofnqeorwnforjmf3")
            }
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, e.toString())
        }
    }

    get("/authenticateUser/{email}/{password}") {
        try {
            val email = call.parameters["email"]!!
            val password = call.parameters["password"]!!
            val user = usersDao.getByEmail(email)
            if (user != null && usersDao.checkPassword(password, user.password)) {
                call.respond(HttpStatusCode.OK, mapOf("id" to user.id, "name" to user.name))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid email or password")
            }
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, "Error while processing request")
        }
    }
}
