package com.collect.invest.plugins.controllers.financialServiceControllers
import com.collect.invest.dao.WalletsDao
import com.collect.invest.dao.entity.WalletsEntity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.walletController(walletsDao: WalletsDao){


    put("/updateStatus/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            val newStatus: String = call.receive<String>()
            if (id != null) {
                walletsDao.updateStatus(id, newStatus)
            }else{
                call.respond("Error while changing wallet status")
            }
        }catch (e: Throwable){
            call.respond("Error while changing wallet status")
        }
    }
    put("/topupBalance/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            val amount: Int = call.receive<Int>()
            if (id != null) {
                walletsDao.topupBalance(id, amount)
            }else{
                call.respond("Error while changing balance")
            }
        }catch (e: Throwable){
            call.respond("Error while changing balance")
        }
    }
    put("/withdrawBalance/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            val amount: Int = call.receive<Int>()
            if (id != null) {
                walletsDao.withdrawBalance(id, amount)
            }else{
                call.respond("Error while changing balance")
            }
        }catch (e: Throwable){
            call.respond("Error while changing balance")
        }
    }
    get("/getWallet/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val wallet: WalletsEntity? =  walletsDao.getById(id)
                if(wallet != null){
                    call.respond(wallet)
                }else{
                    call.respond("Error while getting wallet")
                }
            }else{
                call.respond("Error while getting wallet")
            }
        }catch (e: Throwable){
            call.respond("Error while getting wallet")
        }
    }
}