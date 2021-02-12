package com.ceeredhat

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import freemarker.cache.*
import io.ktor.freemarker.*
import com.fasterxml.jackson.databind.*
import freemarker.core.HTMLOutputFormat
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.http.content.*

const val version = "0.0.4"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
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

        get("/order") {
            formEntries.clear()
            formEntries= mutableListOf(
                FormEntry(
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                ))
            jsonToFormEntry(jsonConfigURL)
            call.respond(
                FreeMarkerContent("order.ftl", mapOf(
                    "version" to version,
                    "formdata" to formEntries),
                    ""))
        }

        get("/jsonConfig") {
            call.respond(
                FreeMarkerContent("jsonConfig.ftl", mapOf(
                    "version" to version,
                    "jsondataURL" to jsonConfigURL,
                ),
                    ""))
        }

        get("/setting") {
                    //call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
                    // mapOf(   "name" to roomName, "username" to userSession.name,  "chat" to chatMsgs )
                    call.respond(
                        FreeMarkerContent("setting.ftl", mapOf(
                            "version" to version),
                            ""))
                }

        get("/refresh") {
            formEntries.clear()
            formEntries= mutableListOf(
                FormEntry(
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                ))
            jsonToFormEntry(jsonConfigURL)
            call.respond(
                // mapOf(   "name" to roomName, "username" to userSession.name,  "chat" to chatMsgs )
                FreeMarkerContent("refresh.ftl", mapOf("formdata" to formEntries), "")
            )
        }


        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}



