package com.ceeredhat

import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * The function calls the passed URL and uses basic authentication.
 *
 * @url input URL
 * @username username for authentication
 * @password password for authentication
 * @return return value from GET in text form
 */
fun sendGet(url: String, username: String, password: String): String {
    val connection = URL(url).openConnection() as HttpURLConnection
    val auth = Base64.getEncoder().encode(("$username:$password").toByteArray()).toString(Charsets.UTF_8)
    connection.addRequestProperty("Authorization", "Basic $auth")
    connection.setRequestProperty("Content-Type", "application/json")
    connection.setRequestProperty("Accept", "application/json")
    connection.connect()
    return connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
}