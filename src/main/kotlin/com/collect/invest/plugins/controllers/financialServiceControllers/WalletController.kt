package com.collect.invest.plugins.controllers.financialServiceControllers
import com.collect.invest.dao.WalletsDao
import com.collect.invest.dao.entity.WalletsEntity
import com.collect.invest.plugins.controllers.financialServiceControllers.entity.Status
import com.collect.invest.plugins.controllers.financialServiceControllers.entity.TopUp
import com.collect.invest.plugins.controllers.financialServiceControllers.entity.Withdraw
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.walletController(walletsDao: WalletsDao){


    put("/updateStatus/") {
        try {
            val newStatus: Status = call.receive<Status>()
            walletsDao.updateStatus(newStatus.userId, newStatus.status)
        }catch (e: Throwable){
            call.respond("Error while changing wallet status")
        }
    }
    put("/topUpBalance/") {
        try {
            val topUp: TopUp = call.receive<TopUp>()
            walletsDao.topupBalance(topUp.userId, topUp.moneyAmount)
        }catch (e: Throwable){
            call.respond("Error while changing balance")
        }
    }
    put("/withdrawBalance/") {
        try {
            val withdraw: Withdraw = call.receive<Withdraw>()
            walletsDao.withdrawBalance(withdraw.userId, withdraw.moneyAmount)
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