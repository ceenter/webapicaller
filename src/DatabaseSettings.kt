package com.ceeredhat

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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

fun settingsParam(params: Parameters) {
    transaction {
        params.forEach { s, list ->
            if (s.trim() == "toweradm" && list[0] != toweradm) {
                settingsUpdate("toweradm", list[0])
                toweradm = list[0]
            }
            if (s.trim() == "towerpass" && list[0] != towerpass) {
                settingsUpdate("towerpass", list[0])
                towerpass = list[0]
            }
            if (s.trim() == "towerhost" && list[0] != towerhost) {
                settingsUpdate("towerhost", list[0])
                towerhost = list[0]
            }
            if (s.trim() == "towerworkflowtemplates" && list[0] != towerworkflowtemplates) {
                settingsUpdate("towerworkflowtemplates", list[0])
                towerworkflowtemplates = list[0]
            }
            if (s.trim() == "towerjobtemplates" && list[0] != towerjobtemplates) {
                settingsUpdate("towerjobtemplates", list[0])
                towerjobtemplates = list[0]
            }
            if (s.trim() == "towerlaunch" && list[0] != towerlaunch) {
                settingsUpdate("towerlaunch", list[0])
                towerlaunch = list[0]
            }
        }
    }
}

/**
 * Cover for SQL command:
 *  Update ceecaller.settings set value = XXX where parameter = XXX
 *
 * @parameter which parameter
 * @value value
 */
fun settingsUpdate(parameter: String, value: String) {
    CeecallerSettings.update({ CeecallerSettings.parameter eq parameter }) {
        it[CeecallerSettings.value] = value
    }
}