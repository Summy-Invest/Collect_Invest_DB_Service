package com.collect.invest.plugins.controllers
import com.collect.invest.dao.WalletsDao
import com.collect.invest.dao.entity.WalletsEntity
import io.ktor.http.*
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
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond("Error while changing wallet status")
            }
        }catch (e: Throwable){
            call.respond("Error while changing wallet status")
        }
    }
    put("/topUpBalance/{id}/{amount}") {
        try {
            val id = call.parameters["id"]?.toLong()
            val amount = call.parameters["amount"]?.toInt()
            if (id != null) {
                walletsDao.topupBalance(id, amount!!)
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond("Error while changing balance id is null")
            }
        }catch (e: Throwable){
            call.respond(e.toString())
        }
    }
    put("/withdrawBalance/{id}/{amount}") {
        try {
            val id = call.parameters["id"]?.toLong()
            val amount = call.parameters["amount"]?.toInt()
            if (id != null) {
                walletsDao.withdrawBalance(id, amount!!)
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond(HttpStatusCode.BadRequest,"Error while withdraw balance id is null")
            }
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest,"Error while withdraw balance adfartewr")
        }
    }
    get("/getWallet/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val wallet: WalletsEntity? =  walletsDao.getById(id)
                if(wallet != null){
                    call.respond(HttpStatusCode.OK, wallet)
                }else{
                    call.respond(HttpStatusCode.BadRequest,"Error while getting wallet")
                }
            }else{
                call.respond(HttpStatusCode.BadRequest,"Error while getting wallet")
            }
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest,"Error while getting wallet")
        }
    }
}
