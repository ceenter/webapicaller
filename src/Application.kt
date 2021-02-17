package com.ceeredhat

import com.fasterxml.jackson.databind.SerializationFeature
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


const val version = "0.1.1"

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {

    val env = environment.config.propertyOrNull("ktor.environment")?.getString()
    println("Environment: $env")

    // Initialization of the connection to the database.
    initDB(env)

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
            val toweradm = params["toweradm"] ?: ""
            val towerpass = params["towerpass"]?: ""
            call.respondHtml {
                body {
                    h1 {
                        +"Settings saved!"
                    }
                    p {
                        +"Parameters that have been saved:"
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

            call.respondHtml {
                body {
                    style {
                        +"""
                    table {
                        font: 1em Arial;
                        border: 1px solid black;
                        width: 100%;
                    }
                    th {
                        background-color: #ccc;
                        width: 200px;
                    }
                    td {
                        background-color: #eee;
                    }
                    th, td {
                        text-align: left;
                        padding: 0.5em 1em;
                    }
                """.trimIndent()
                    }
                    h1 {
                        +"Postgres list!"
                    }
                    p {
                        +"Parameters that have been saved:"
                        hr {  }
                        table {
                            tr {
                                th {
                                    text("Parameter")
                                }
                                th {
                                    text("Value")
                                }
                            }
                            // select query
                            transaction {
                                // Statements here
                                //addLogger(StdOutSqlLogger)
                                val query = CeecallerSettings.selectAll()
                                query.forEach {
                                    tr {
                                        td { text(it[CeecallerSettings.parameter]) }
                                        td { text(it[CeecallerSettings.value]) }
                                    }
                                }
                            }
                        }
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




