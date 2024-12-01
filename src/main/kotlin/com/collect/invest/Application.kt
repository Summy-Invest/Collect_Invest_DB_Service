package com.collect.invest

import com.collect.invest.plugins.configureRouting
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*

fun main() {

    System.setProperty("db.url", "jdbc:postgresql://postgresql:5432/postgres")
    System.setProperty("db.username", "postgres")
    System.setProperty("db.password", "password")
    System.setProperty("image.storage.url", "src/main/resources/ImageStorage/")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

}

fun Application.module() {


    install(ContentNegotiation) {
        gson(contentType = ContentType.Any) {

        }
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }

    configureRouting()
}
