package com.collect.invest.plugins.controllers.userServiceControllers

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.entity.UsersEntity
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
    get("/getUserById/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val user: UsersEntity? = usersDao.getById(id)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond("Error while getting user")
                }
            } else {
                call.respond("Error while getting user")
            }
        } catch (e: Throwable) {
            call.respond("Error while getting user")
        }

    }

}