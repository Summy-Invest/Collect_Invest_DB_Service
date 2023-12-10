package com.collect.invest.plugins.routes

import com.collect.invest.dao.CollectablesDao
import com.collect.invest.plugins.controllers.collectablesController
import io.ktor.server.routing.*

fun Route.collectableRoutes(collectablesDao: CollectablesDao) {

    // Маршруты для кошельков
    route("/collectable") {
        collectablesController(collectablesDao)
    }
}
