package com.collect.invest.plugins.routes

import com.collect.invest.dao.entity.UsersEntity
import com.collect.invest.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRotes(userService: UserService){

    //Обработка запроса на добавление юзера
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

    //Обработка запроса на получение юзера по его id
    get("userService/getUserById/{id}"){
        val idParam = call.parameters["id"]
        if (idParam != null) {
            val id = idParam.toLongOrNull()
            if (id != null) {
                val user: UsersEntity? = userService.getById(id)
                if(user != null){
                    call.respond(user)
                }else{
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "ID is missing")
        }
    }
}