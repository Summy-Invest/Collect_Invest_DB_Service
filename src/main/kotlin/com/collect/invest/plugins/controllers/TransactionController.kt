package com.collect.invest.plugins.controllers

import com.collect.invest.dao.TransactionsDao
import com.collect.invest.dao.entity.TransactionsEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.transactionController(transactionsDao: TransactionsDao){


    put("/createTransiction") {
        try {
            val transaction = call.receive<TransactionsEntity>()
            val transactionId = transactionsDao.saveTransaction(transaction)
            call.respond(HttpStatusCode.OK, mapOf("id" to transactionId))
        } catch (e: Throwable) {
            call.respond("Error while creating transaction")
        }
    }


    patch("/updateTransiction") {
        try {
            val transaction = call.receive<TransactionsEntity>()
            transactionsDao.updateStatus(transaction)
            call.respond(HttpStatusCode.OK)
        } catch (e: Throwable) {
            call.respond("Error while updating status")
        }
    }

}
