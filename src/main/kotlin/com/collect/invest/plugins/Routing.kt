package com.collect.invest.plugins

import com.collect.invest.dao.jdbc.UsersDaoJdbc
import com.collect.invest.dao.jdbc.WalletsDaoJdbc
import com.collect.invest.plugins.routes.financialRotes
import com.collect.invest.plugins.routes.userRotes
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {


    val dbUrl = System.getProperty("db.url")
    val dbUsername = System.getProperty("db.username")
    val dbPassword = System.getProperty("db.password")


    val usersDao = UsersDaoJdbc(dbUrl, dbUsername, dbPassword)
    val walletsDao = WalletsDaoJdbc(dbUrl, dbUsername, dbPassword)

    routing {
        route("userService"){
            userRotes(usersDao)
        }
        route("financialService"){
            financialRotes(walletsDao)
        }
    }
}