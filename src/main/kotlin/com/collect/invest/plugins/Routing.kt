package com.collect.invest.plugins

import com.collect.invest.dao.jdbc.*
import com.collect.invest.plugins.routes.collectableRoutes
import com.collect.invest.plugins.routes.financialRoutes
import com.collect.invest.plugins.routes.userRotes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {




    val dbUrl = System.getProperty("db.url")
    val dbUsername = System.getProperty("db.username")
    val dbPassword = System.getProperty("db.password")

    val imageStoragePath = System.getProperty("image.storage.url")


    val usersDao = UsersDaoJdbc(dbUrl, dbUsername, dbPassword)
    val walletsDao = WalletsDaoJdbc(dbUrl, dbUsername, dbPassword)
    val transactionsDao = TransactionsDaoJdbc(dbUrl, dbUsername, dbPassword)
    val collectablesDao = CollectablesDaoJdbc(dbUrl, dbUsername, dbPassword)
    val stockPortfolioDao = StockPortfolioDaoJdbc(dbUrl, dbUsername, dbPassword)

    val imageStorage = File(imageStoragePath)

    routing {

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml", )

        staticFiles("image", imageStorage)

        route("userService") {
            userRotes(usersDao, walletsDao)
        }
        route("financialService") {
            financialRoutes(walletsDao, transactionsDao)
        }
        route("collectableService") {
            collectableRoutes(collectablesDao, stockPortfolioDao)
        }
    }
}
