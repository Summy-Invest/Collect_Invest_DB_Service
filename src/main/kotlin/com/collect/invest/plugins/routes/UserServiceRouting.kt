package com.collect.invest.plugins.routes

import com.collect.invest.dao.UsersDao
import com.collect.invest.plugins.controllers.userServiceControllers.userController
import io.ktor.server.routing.*

fun Route.userRotes(usersDao: UsersDao) {

    route("/user") {
        userController(usersDao)
    }


}