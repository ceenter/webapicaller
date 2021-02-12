package com.ceeredhat

import java.net.URL

data class IndexData(val items: List<Int>)
data class FormEntry(val type: String, val name: String, val label: String, val parent: String,
                     val default: String, val options: String)

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