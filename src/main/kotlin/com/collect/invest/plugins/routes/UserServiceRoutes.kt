package com.collect.invest.plugins.routes

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.entity.UsersEntity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRotes(usersDao: UsersDao){

    //Обработка запроса на добавление юзера
    post("userService/saveUser") {
        try{
            val user = call.receive<UsersEntity>()
            usersDao.saveUser(user)
            call.respond("User added")
        }catch(e: Throwable){
            call.respond("Error while creating user")
        }
    }

    //Обработка запроса на получение юзера по его id
    get("userService/getUserById/{id}"){
    try {
        val idParam = call.parameters["id"]?.toLongOrNull()
        if (idParam != null) {
            val user: UsersEntity? = usersDao.getById(idParam)
            if(user != null) {
                call.respond(user)
            }else{
                call.respond("Error while getting user")
            }
        }else{
            call.respond("Error while getting user")
        }
    }catch (e: Throwable){
        call.respond("Error while getting user")
    }

    }
}