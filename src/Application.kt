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
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import kotlinx.html.*


const val version = "0.0.4"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    val env = environment.config.propertyOrNull("ktor.environment")?.getString()
    println(env)
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

        post("/savesetting") {
            val params = call.receiveParameters()
            //val headline = params["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            //val body = params["body"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val towercall = params["towercall"] ?: "0"
            val toweradm = params["toweradm"] ?: ""
            val towerpass = params["towerpass"]?: ""
            call.respondHtml {
                body {
                    h1 {
                        +"Settings saved!"
                    }
                    p {
                        +"Parameters that have been saved:"
                        br { +towercall }
                        br { +toweradm }
                        br { +towerpass }
                    }
                    hr {  }
                    p {
                        +"Database status = OK!"
                    }
                    a("/") {
                        +"Go back"
                    }
                }
            }
        }

        // postgres test
        get("/dbtest") {
            initDB()
            call.respondHtml {
                body {
                    h1 {
                        +"Postgres list!"
                    }
                    p {
                        +"Parameters that have been saved:"
                    }
                    hr {  }
                    p {
                        +"Database status = OK!"
                    }
                    a("/") {
                        +"Go back"
                    }
                }
            }
        }
    }
}



