package com.ceeredhat

import com.google.gson.Gson
import com.google.gson.JsonElement
import templateSurvey_spec.Survey_spec
import templateWorkflowTower.TowerWorkflow


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
    for (aIndex in 0 until jsonObj.size() - 1) {
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
                if (valueTableJson.keySet().elementAt(0) == "default")
                    vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
                // complete options like {text: xxx, value: xxx }
                vueoptions = "{ text: '${valueTableJson.keySet().elementAt(0)}', " +
                        "value: '${valueTableJson.entrySet().elementAt(0).value.asString}' }"
                if (valueTableJson.size() > 1)
                    for (bIndex in 1 until valueTableJson.size()) {
                        if (valueTableJson.keySet().elementAt(bIndex) != "default") {
                            vueoptions += ", { text: '${valueTableJson.keySet().elementAt(bIndex)}', " +
                                    "value: '${valueTableJson.entrySet().elementAt(bIndex).value.asString}' }"
                        } else
                            vuedefault = valueTableJson.entrySet().elementAt(bIndex).value.asString
                    }
            }
            "manual" ->
                vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
            "auto" ->
                vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
        }
        // try to find in array second field contains aElementParentString
        var vueparent = arrayParent.firstOrNull { it[1].contains(aElementParentString) }?.get(0).toString()
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
 * @return Array with key pairs store Parent-Child
 */
private fun fillParentArray(jsonStr: String): Array<Array<String>> {
    val gson = Gson() // Creates new instance of Gson
    val element = gson.fromJson(jsonStr, JsonElement::class.java) //Converts the json string to JsonElement without POJO
    val jsonObj = element.asJsonObject //Converting JsonElement to JsonObject
    var arrayParent = arrayOf<Array<String>>()
    var vuedefault: String
    for (aIndex in 0 until jsonObj.size() - 1) {
        val aElementParentString = jsonObj.keySet().elementAt(aIndex)
        val aElementChildren = jsonObj[aElementParentString].asJsonObject
        val vuename = aElementChildren.get("variableName").asString
        val vuetype = aElementChildren.get("inputType").asString
        val valueTableJson = aElementChildren.get("valueTable").asJsonObject
        // Depending on the value type, a list of values is generated
        when (vuetype.toString()) {
            "list" -> {
                // default value from valueTable
                if (valueTableJson.keySet().elementAt(0) == "default") {
                    vuedefault = valueTableJson.entrySet().elementAt(0).value.asString
                    arrayParent += arrayOf(vuename, vuedefault)
                }
                // if value is not empty, then store the name of child method,
                // format arrayOf("parent name", "child name")
                if (valueTableJson.entrySet().elementAt(0).value.asString != "")
                    arrayParent += arrayOf(
                        vuename, valueTableJson.entrySet().elementAt(0).value
                            .asString
                    )
                if (valueTableJson.size() > 1)
                    for (bIndex in 1 until valueTableJson.size()) {
                        if (valueTableJson.keySet().elementAt(bIndex) != "default") {
                            // if value is not empty, then store the name of child method,
                            // format arrayOf("parent name", "child name")
                            if (valueTableJson.entrySet().elementAt(bIndex).value.asString != "")
                                arrayParent += arrayOf(
                                    vuename,
                                    valueTableJson.entrySet().elementAt(bIndex).value.asString
                                )
                        } else {
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

/**
 * JSON string parsing and assembly of form items for the Vue framework.
 * Form version based on data from Tower
 *
 * @jsonStr Input JSON as string.
 */
fun jsonFromTower(myJson: String) {
    val gson = Gson()
    val mTower = gson.fromJson(myJson, TowerWorkflow::class.java)
    // default for first Form Option
    var modelName = "template_name: []"
    var clearField = "this.model.template_name = [];"
    var schemaType = "type: \"select\""
    var schemaLabel = "label: \"Tower Workflow Template\""
    var schemaModel = "model: \"template_name\""
    var schemaReadonly = "readonly: true"
    var schemaFeatured = "featured: true"
    var schemaRequired = "required: true"
    var schemaDisabled = "disabled: false"
    var schemaDefault = ""
    var schemaPlaceholder = ""
    var schemaValidator = ""
    var schemaVisible = ""
    var schemaValues = ""
    val surveySpec = arrayListOf<String>()
    for (aIndex in 0 until mTower.results.count()) {
        if (mTower.results[aIndex].description != "") {
            surveySpec.add(mTower.results[aIndex].name + ":" + mTower.results[aIndex].related.survey_spec)
            //println(mTower.results[aIndex].description)
            schemaValues += if (schemaValues == "")
                "values: function() {\n" +
                        "                        return [" +
                "{ id: \"${mTower.results[aIndex].name}\", " +
                        "name: \"${mTower.results[aIndex].description}\" }"
            else
                ", { id: \"${mTower.results[aIndex].name}\", " +
                        "name: \"${mTower.results[aIndex].description}\" }"
        }
    }
    schemaValues += "]\n" +
            "                    }"
    var newEntry = FormVue(
        clearField, modelName, schemaType, schemaLabel, schemaModel, schemaReadonly, schemaFeatured,
        schemaRequired, schemaDisabled, schemaDefault, schemaPlaceholder, schemaValidator, schemaVisible, schemaValues
    )
    formVues[0] = newEntry
    // adding survey to Form
    surveySpec.forEachIndexed { x, surveyName ->
        val surveyJson = sendGet(towerhost + surveyName.split(":")[1], toweradm, towerpass)
         //surveyName.split(":")[0]
        val mSurvey = gson.fromJson(surveyJson, Survey_spec::class.java)
        mSurvey.spec.forEachIndexed { y, it ->
            schemaDefault = ""
            schemaPlaceholder = ""
            schemaVisible = "visible: (model) => model.template_name === '${surveyName.split(":")[0]}'"
            schemaValues = ""
            if (it.choices == "") {
                clearField = "this.model.question_name$x$y = \"\";"
                modelName = "question_name$x$y: \"\""
                if (it.type == "integer") {
                    schemaType = "type: \"input\", inputType: \"number\""
                    schemaValidator = "validator: VueFormGenerator.validators.number"
                }
                else {
                    schemaType = "type: \"input\", inputType: \"text\""
                    schemaValidator = "validator: VueFormGenerator.validators.string"
                }
            }
            else {
                clearField = "this.model.question_name$x$y = [];"
                modelName = "question_name$x$y: []"
                schemaType = "type: \"select\""
                schemaValues = "values: " +
                    it.choices.split("\n").joinToString(prefix = "[\"", postfix = "\"]", separator = "\",\"")
                schemaValidator = ""
            }
            schemaLabel = "label: \"${it.question_name}\""
            schemaModel = "model: \"question_name$x$y\""
            schemaReadonly = "readonly: false"
            schemaFeatured = "featured: false"
            schemaRequired = "required: ${it.required}"
            schemaDisabled = "disabled: false"
            newEntry = FormVue(
                clearField, modelName, schemaType, schemaLabel, schemaModel, schemaReadonly, schemaFeatured,
                schemaRequired, schemaDisabled, schemaDefault, schemaPlaceholder, schemaValidator, schemaVisible, schemaValues
            )
            formVues.add(newEntry)
        }
    }
}