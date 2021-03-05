package com.ceeredhat

import com.fasterxml.jackson.databind.SerializationFeature
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update


const val version = "0.1.6"

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

//---------------------------------------------------------------------------------------------------------------- GET /
        get("/") {
            //call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            // mapOf(   "name" to roomName, "username" to userSession.name,  "chat" to chatMsgs )
            call.respond(
                FreeMarkerContent("index.ftl", mapOf(
                    "version" to version),
                    ""))
        }

//----------------------------------------------------------------------------------------------------------- GET /order
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

//------------------------------------------------------------------------------------------------------ GET /ordertower
        get("/ordertower") {
            // call Tower
            val response =
                sendGet(towerhost + towerworkflowtemplates, toweradm, towerpass)
            formVues.clear()
            formVues= mutableListOf(
                FormVue(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
                )
            jsonFromTower(response)
            call.respond(
                FreeMarkerContent("ordertower.ftl", mapOf(
                    "version" to version,
                    "formdata" to formVues),
                    ""))
        }

//------------------------------------------------------------------------------------------------------ GET /jsonConfig
        get("/jsonConfig") {
            call.respond(
                FreeMarkerContent("jsonConfig.ftl", mapOf(
                    "version" to version,
                    "jsondataURL" to jsonConfigURL,
                ),
                    ""))
        }

//--------------------------------------------------------------------------------------------------------- GET /setting
        get("/setting") {
            formSettings.clear()
            transaction {
                // Statements here
                //addLogger(StdOutSqlLogger)
                val query = CeecallerSettings.selectAll()
                query.forEachIndexed { index, resultRow ->
                    val newEntry = FormSettings(resultRow[CeecallerSettings.parameter], resultRow[CeecallerSettings.value])
                    formSettings.add(index, newEntry)
                }
            }
            call.respond(
                FreeMarkerContent("setting.ftl", mapOf(
                    "version" to version,
                    "data" to formSettings),
                    ""))
        }

//--------------------------------------------------------------------------------------------------------- GET /refresh
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

//---------------------------------------------------------------------------------------------------- GET /json/jackson
        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }

//---------------------------------------------------------------------------------------------------- POST /savesetting
        post("/savesetting") {
            val params = call.receiveParameters()
            settingsParam(params) // Update settings table based on changed parameters
            call.respondHtml {
                body {
                    style {
                        +"""
                    table {
                        font: 1em Arial;
                        border: 1px solid black;
                        width: 50%;
                    }
                    th {
                        background-color: #ccc;
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
                        +"Settings received correctly!"
                    }
                    p {
                        +"Parameters that you sent:"
                            table {
                                tr {
                                    th {
                                        text("Parameter")
                                    }
                                    th {
                                        text("Value")
                                    }
                                }
                                params.forEach { s, list ->
                                    tr {
                                        td { +s.trim() }
                                        td { +list[0] }
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

//---------------------------------------------------------------------------------------------------------- GET /dbtest
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




