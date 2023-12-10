package com.collect.invest.plugins.controllers

import com.collect.invest.dao.CollectablesDao
import com.collect.invest.dao.entity.CollectablesEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.collectablesController(collectablesDao: CollectablesDao){

    get("/getCollectableById/{id}") {
        try {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val collectable: CollectablesEntity? = collectablesDao.getById(id)
                if(collectable != null){
                    call.respond(HttpStatusCode.OK, collectable)
                }else{
                    call.respond(HttpStatusCode.BadRequest,"Error while getting collectable 1")
                }
            }else{
                call.respond(HttpStatusCode.BadRequest,"Error while getting collectable 2")
            }
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest,"Error while getting collectable 3")
        }
    }


    get("/getAllCollectibles") {
        try {
            val collectable: List<CollectablesEntity> = collectablesDao.getAllCollectables()
            call.respond(HttpStatusCode.OK, collectable)
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest,"Error while getting collectables")
        }
    }


    get("/getPrice/{id}"){

        try {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val collectablePrice: Double = collectablesDao.getPrice(id)
                call.respond(HttpStatusCode.OK, mapOf("currentPrice" to collectablePrice))
            }else{
                call.respond(HttpStatusCode.BadRequest,"Error while getting price")
            }
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest,"Error while getting price")
        }

    }


    patch("/updatePrice"){
        try {
            val transaction = call.receive<CollectablesEntity>()
            val id = transaction.id
            val newPrice = transaction.currentPrice
            collectablesDao.updatePrice(id, newPrice)
            call.respond(HttpStatusCode.OK)
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest,"Error while updating price")
        }

    }

}
