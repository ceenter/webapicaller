package com.ceeredhat

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URL

data class IndexData(val items: List<Int>)
data class FormEntry(val type: String, val name: String, val label: String, val parent: String,
                     val default: String, val options: String)
data class FormSettings(val parameter: String, val value: String)

val jsonConfigURL =
    URL("https://raw.githubusercontent.com/ceenter/ceenter/master/api-caller/menu-map.json").readText()

var formEntries= mutableListOf(
    FormEntry(
        "",
        "",
        "",
        "",
        "",
        ""
    )
)

var formSettings= mutableListOf(
    FormSettings(
        "",
        ""
    )
)

// Read setting parameters -------------------------------------------------------------------------------------------->
var toweradm = transaction {
    CeecallerSettings.select{CeecallerSettings.parameter eq "toweradm"}.map { it[CeecallerSettings
        .value] }[0]
}

var towerpass = transaction {
    CeecallerSettings.select{CeecallerSettings.parameter eq "towerpass"}.map { it[CeecallerSettings
        .value] }[0]
}
var towerhost = transaction {
    CeecallerSettings.select{CeecallerSettings.parameter eq "towerhost"}.map { it[CeecallerSettings
        .value] }[0]
}
var towerworkflowtemplates = transaction {
    CeecallerSettings.select{CeecallerSettings.parameter eq "towerworkflowtemplates"}.map { it[CeecallerSettings
        .value] }[0]
}
var towerjobtemplates = transaction {
    CeecallerSettings.select{CeecallerSettings.parameter eq "towerjobtemplates"}.map { it[CeecallerSettings
        .value] }[0]
}
var towerlaunch = transaction {
    CeecallerSettings.select{CeecallerSettings.parameter eq "towerlaunch"}.map { it[CeecallerSettings
        .value] }[0]
}
// -------------------------------------------------------------------------------------------------------------------->