package com.ceeredhat

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table

object CeecallerSettings : Table("ceecaller.settings"){
    val parameter = varchar("parameter", 50)
    val value = varchar("value", 50)
}


/**
 * Initializing the connection to the database.
 *  see examples https://www.baeldung.com/kotlin/exposed-persistence
 *               https://github.com/JetBrains/Exposed/wiki/Transactions
 *
 * @env If it is not null, it contains the name of the environment, eg prod or dev.
 */
fun initDB(env: String?) {
    var config = HikariConfig("/hikaridev.properties")
    if (env == "prod") config = HikariConfig("/hikari.properties")
    val ds = HikariDataSource(config)
    Database.connect(ds)
}