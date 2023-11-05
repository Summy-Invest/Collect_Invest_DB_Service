package com.collect.invest.plugins

import com.collect.invest.dao.jdbc.TransactionsDaoJdbc
import com.collect.invest.dao.jdbc.UsersDaoJdbc
import com.collect.invest.dao.jdbc.WalletsDaoJdbc
import com.collect.invest.plugins.routes.userRotes
import financialRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting() {


    val dbUrl = System.getProperty("db.url")
    val dbUsername = System.getProperty("db.username")
    val dbPassword = System.getProperty("db.password")


    val usersDao = UsersDaoJdbc(dbUrl, dbUsername, dbPassword)
    val walletsDao = WalletsDaoJdbc(dbUrl, dbUsername, dbPassword)
    val transactionsDao = TransactionsDaoJdbc(dbUrl, dbUsername, dbPassword)

    routing {

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml", )


        route("userService") {
            userRotes(usersDao)
        }
        route("financialService") {
            financialRoutes(walletsDao, transactionsDao)
        }
    }
}