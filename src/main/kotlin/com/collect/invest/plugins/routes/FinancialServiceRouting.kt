package com.collect.invest.plugins.routes

import com.collect.invest.dao.TransactionsDao
import com.collect.invest.dao.WalletsDao
import com.collect.invest.plugins.controllers.transactionController
import com.collect.invest.plugins.controllers.walletController
import io.ktor.server.routing.*

fun Route.financialRoutes(walletsDao: WalletsDao, transactionsDao: TransactionsDao) {

        route("/wallet") {
            walletController(walletsDao)
        }

        route("/transaction") {
            transactionController(transactionsDao)
        }
}
