package com.ceeredhat

import com.google.gson.Gson
import com.google.gson.JsonElement


/**
 * JSON string parsing and assembly of form items for the Vue framework.
 *
 * @jsonStr Input JSON as string.
 */
fun jsonToFormEntry(jsonStr: String) {
    val gson = Gson() // Creates new instance of Gson
    val element = gson.fromJson(jsonStr, JsonElement::class.java) //Converts the json string to JsonElement without POJO
    val jsonObj = element.asJsonObject //Converting JsonElement to JsonObject
    val arrayParent = fillParentArray(jsonStr) // for store of relation to Parent, format: value,Parent
    var vuedefault: String
    var vueoptions: String
    for (aIndex in 0 until jsonObj.size()-1) {
        val aElementParentString = jsonObj.keySet().elementAt(aIndex)
        val aElementChildren = jsonObj[aElementParentString].asJsonObject
        val vuename = aElementChildren.get("variableName").asString
        val vuetype = aElementChildren.get("inputType").asString
        val valueTableJson = aElementChildren.get("valueTable").asJsonObject
        vuedefault = ""
        vueoptions = "''"
        // Depending on the value type, a list of values is generated
        when (vuetype.toString()) {
            "list" -> {
                // default value from valueTable
                if (valueTableJson.keySet().elementAt(0)=="default")
                    vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
                // complete options like {text: xxx, value: xxx }
                vueoptions = "{ text: '${valueTableJson.keySet().elementAt(0)}', " +
                        "value: '${valueTableJson.entrySet().elementAt(0).value.asString}' }"
                if (valueTableJson.size() > 1)
                    for (bIndex in 1 until valueTableJson.size())
                    {
                        if (valueTableJson.keySet().elementAt(bIndex) != "default")
                        {
                            vueoptions += ", { text: '${valueTableJson.keySet().elementAt(bIndex)}', " +
                                    "value: '${valueTableJson.entrySet().elementAt(bIndex).value.asString}' }"
                        }
                        else
                            vuedefault = valueTableJson.entrySet().elementAt(bIndex).value.asString
                    }
            }
            "manual" ->
                vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
            "auto" ->
                vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
        }
        // try to find in array second field contains aElementParentString
        var vueparent = arrayParent.firstOrNull { it[1].contains(aElementParentString)}?.get(0).toString()
        if (vueparent == "null") vueparent = ""
        val newEntry = FormEntry(vuetype, vuename, aElementParentString, vueparent, vuedefault, vueoptions)
        if (aIndex == 0) formEntries[0] = newEntry else formEntries.add(aIndex, newEntry)
    }
}

/**
 * Helper for jsonToFormEntry string
 * fun goes through the whole JSON to find all the Parent-Child pairs and store them in the Array.
 *
 * @jsonStr Input JSON as string = same as fun jsonToFormEntry
 */
private fun fillParentArray(jsonStr: String): Array<Array<String>> {
    val gson = Gson() // Creates new instance of Gson
    val element = gson.fromJson(jsonStr, JsonElement::class.java) //Converts the json string to JsonElement without POJO
    val jsonObj = element.asJsonObject //Converting JsonElement to JsonObject
    var arrayParent = arrayOf<Array<String>>()
    var vuedefault: String
    for (aIndex in 0 until jsonObj.size()-1) {
        val aElementParentString = jsonObj.keySet().elementAt(aIndex)
        val aElementChildren = jsonObj[aElementParentString].asJsonObject
        val vuename = aElementChildren.get("variableName").asString
        val vuetype = aElementChildren.get("inputType").asString
        val valueTableJson = aElementChildren.get("valueTable").asJsonObject
        // Depending on the value type, a list of values is generated
        when (vuetype.toString()) {
            "list" -> {
                // default value from valueTable
                if (valueTableJson.keySet().elementAt(0)=="default") {
                    vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
                    arrayParent += arrayOf(vuename, vuedefault)
                }
                // if value is not empty, then store the name of child method,
                // format arrayOf("parent name", "child name")
                if (valueTableJson.entrySet().elementAt(0).value.asString!="")
                    arrayParent += arrayOf(vuename, valueTableJson.entrySet().elementAt(0).value
                        .asString)
                if (valueTableJson.size() > 1)
                    for (bIndex in 1 until valueTableJson.size())
                    {
                        if (valueTableJson.keySet().elementAt(bIndex) != "default")
                        {
                            // if value is not empty, then store the name of child method,
                            // format arrayOf("parent name", "child name")
                            if (valueTableJson.entrySet().elementAt(bIndex).value.asString!="")
                                arrayParent += arrayOf(vuename, valueTableJson.entrySet().elementAt(bIndex).value.asString)
                        }
                        else {
                            vuedefault = valueTableJson.entrySet().elementAt(bIndex).value.asString
                            arrayParent += arrayOf(vuename, vuedefault)
                        }
                    }
            }
            "manual" -> {
                vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
                arrayParent += arrayOf(vuename, vuedefault)
            }
            "auto" -> {
                vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
                arrayParent += arrayOf(vuename, vuedefault)
            }
        }
    }
    return arrayParent
}