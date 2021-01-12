package com.ceeredhat

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import freemarker.cache.*
import io.ktor.freemarker.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.http.content.*

const val version = "v0.1"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        static("/static"){
            resources("static")
        }

        get("/") {
            //call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            // mapOf(   "name" to roomName, "username" to userSession.name,  "chat" to chatMsgs )
            call.respond(
                FreeMarkerContent("index.ftl", mapOf(
                    "version" to version),
                    ""))
        }

        get("/jsonConfig") {
            //call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            call.respond(
                FreeMarkerContent("jsonConfig.ftl", mapOf(
                    "version" to version,
                    "data" to IndexData(listOf(1, 2, 3))),
                    ""))
        }

        get("/dashboard") {
            //call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            call.respond(
                FreeMarkerContent("dashboard.ftl", mapOf(
                    "version" to version),
                    ""))
        }


        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}

