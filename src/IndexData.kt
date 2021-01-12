package com.ceeredhat

import java.io.File

data class IndexData(val items: List<Int>)

val jsonConfigText = File("callerConfig.json").readText(Charsets.UTF_8)