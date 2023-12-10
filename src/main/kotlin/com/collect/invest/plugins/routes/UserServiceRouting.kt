package com.collect.invest.plugins.routes

import com.collect.invest.dao.UsersDao
import com.collect.invest.dao.WalletsDao
import com.collect.invest.plugins.controllers.userController
import io.ktor.server.routing.*

fun Route.userRotes(usersDao: UsersDao, walletsDao: WalletsDao) {

    route("/user") {
        userController(usersDao, walletsDao)
    }


}
