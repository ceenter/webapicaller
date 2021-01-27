package com.ceeredhat

import com.google.gson.Gson
import com.google.gson.JsonElement

/*
    JSON string parsing and assembly of form items for the knockout framework.
 */
fun jsonToFormEntry(jsonStr:String) {
    val gson = Gson() // Creates new instance of Gson
    val element = gson.fromJson(jsonStr, JsonElement::class.java) //Converts the json string to JsonElement without POJO
    val jsonObj = element.asJsonObject //Converting JsonElement to JsonObject
    for (aIndex in 0 until jsonObj.size()-1) {
        val aElementParentString = jsonObj.keySet().elementAt(aIndex)
        val aElementChildren = jsonObj[aElementParentString].asJsonObject
        val variableName = aElementChildren.get("variableName").asString
        val inputType = aElementChildren.get("inputType").asString
        val valueTableJson = aElementChildren.get("valueTable").asJsonObject
        var variableNamePrevious = ""
        var formTableValue = ""
        var koTableValues = ""
        if (aIndex > 0) {
            // because second Form needs data from previous then I save it by index minus 1
            variableNamePrevious = jsonObj[jsonObj.keySet().elementAt(aIndex-1)].asJsonObject.get("variableName")
                .asString
            formTableValue = jsonObj[jsonObj.keySet().elementAt(aIndex-1)].asJsonObject.get("valueTable").asJsonObject
                .keySet().elementAt(0)
        }
        // Depending on the value type, a list of values is generated
        when (inputType.toString()) {
            "list" -> {
                koTableValues = "["
                if (valueTableJson.size() > 1)
                    for (bIndex in 0 until valueTableJson.size())
                        koTableValues += "'${valueTableJson.keySet().elementAt(bIndex)}',"
                koTableValues += "]"
            }
            "manual" -> {
                koTableValues = valueTableJson.keySet().elementAt(0)
            }
            "auto" -> {
                koTableValues = valueTableJson.keySet().elementAt(0)
            }
        }
        val newEntry = FormEntry(inputType, aElementParentString,variableName+"_values",variableName+"_options",
            "if: "+variableNamePrevious+"_values() === '$formTableValue'",koTableValues)
        if (aIndex==0) formEntries[0] = newEntry else formEntries.add(aIndex, newEntry)
    }
}