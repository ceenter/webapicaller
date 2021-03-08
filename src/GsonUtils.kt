package com.ceeredhat

import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import templateSurvey_spec.Survey_spec
import templateWorkflowTower.TowerWorkflow
import java.io.IOException


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
            surveySpec.add(mTower.results[aIndex].id.toString() + ":" + mTower.results[aIndex].related.survey_spec)
            //println(mTower.results[aIndex].description)
            schemaValues += if (schemaValues == "")
                "values: function() {\n" +
                        "                        return [" +
                        "{ id: \"${mTower.results[aIndex].id}\", " +
                        "name: \"${mTower.results[aIndex].description}\" }"
            else
                ", { id: \"${mTower.results[aIndex].id}\", " +
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
    surveySpec.forEach { surveyName ->
        val surveyJson = sendGet(towerhost + surveyName.split(":")[1], toweradm, towerpass)
        //surveyName.split(":")[0]
        val mSurvey = gson.fromJson(surveyJson, Survey_spec::class.java)
        mSurvey.spec.forEach {
            schemaDefault = ""
            schemaPlaceholder = ""
            schemaVisible = "visible: (model) => model.template_name === '${surveyName.split(":")[0]}'"
            schemaValues = ""
            if (it.choices == "") {
                // test if it is a number
                if (it.default.toString().toDoubleOrNull() != null) {
                    val fieldInt = (it.default as Double).toInt()
                    clearField = "this.model.${it.variable}${surveyName.split(":")[0]} = \"$fieldInt\";"
                    modelName = "${it.variable}${surveyName.split(":")[0]}: \"$fieldInt\""
                } else {
                    clearField =
                        if (it.default != "") "this.model.${it.variable}${surveyName.split(":")[0]} = \"${it.default}\";"
                        else "this.model.${it.variable}${surveyName.split(":")[0]} = \"\";"
                    modelName = if (it.default != "") "${it.variable}${surveyName.split(":")[0]}: \"${it.default}\""
                    else "${it.variable}${surveyName.split(":")[0]}: \"\""
                }
                if (it.type == "integer") {
                    schemaType = "type: \"input\", inputType: \"number\""
                    schemaValidator = "validator: VueFormGenerator.validators.number"
                } else {
                    schemaType = "type: \"input\", inputType: \"text\""
                    schemaValidator = "validator: VueFormGenerator.validators.string"
                }
            } else {
                clearField = "this.model.${it.variable}${surveyName.split(":")[0]} = [];"
                modelName = "${it.variable}${surveyName.split(":")[0]}: []"
                schemaType = "type: \"select\""
                schemaValues = "values: " +
                        it.choices.split("\n").joinToString(prefix = "[\"", postfix = "\"]", separator = "\",\"")
                schemaValidator = ""
            }
            schemaLabel = "label: \"${it.question_name}\""
            schemaModel = "model: \"${it.variable}${surveyName.split(":")[0]}\""
            schemaReadonly = "readonly: false"
            schemaFeatured = "featured: false"
            schemaRequired = "required: ${it.required}"
            schemaDisabled = "disabled: false"
            newEntry = FormVue(
                clearField,
                modelName,
                schemaType,
                schemaLabel,
                schemaModel,
                schemaReadonly,
                schemaFeatured,
                schemaRequired,
                schemaDisabled,
                schemaDefault,
                schemaPlaceholder,
                schemaValidator,
                schemaVisible,
                schemaValues
            )
            formVues.add(newEntry)
        }
    }
}

/**
 * Create API call from JSON string sending by FORM.
 *
 * @jsonStr Input JSON as string.
 */
fun jsonCommandAPI(jsonStr: String): String {
    val (templateName, extraVars) = generateAPIvalues(jsonStr)
    return "curl -f -k -H 'Content-Type: application/json' -H 'Authorization:Basic '$toweradm:$towerpass' -d " +
            "'{\"extra_vars\":{$extraVars}}' $towerhost/api/v2/workflow_job_templates/$templateName/launch/"
}

/**
 * Create API values
 *  return pair: template_name, extra_vars
 *
 * @jsonStr Input JSON as string.
 */
fun generateAPIvalues(jsonStr: String): Pair<String, String> {
    val gson = Gson() // Creates new instance of Gson
    val element = gson.fromJson(jsonStr, JsonElement::class.java) //Converts the json string to JsonElement without POJO
    var extravars = ""
    val jsonObj = element.asJsonObject //Converting JsonElement to JsonObject
    var templateName = "" // template name should be filled in as first!!!
    for (aIndex in 0 until jsonObj.size()) {
        if (jsonObj.entrySet().elementAt(aIndex).key.toString() == "template_name")
            templateName = jsonObj.entrySet().elementAt(aIndex).value.toString().replace("\"", "")
        // find key for according element -> it composing like name+template_name
        val elementName = jsonObj.entrySet().elementAt(aIndex).key.toString()
        var elementValue = jsonObj.entrySet().elementAt(aIndex).value.toString()
        // if elementValue is number then remove double quotas
        if (elementValue.replace("\"", "").toDoubleOrNull() != null)
            elementValue = elementValue.replace("\"", "")
        if (elementName.contains(templateName))
            if (extravars == "")
            // the reason plus 2 is here because key value is with two chars of double quotas
                extravars = "\"" + elementName.substring(
                    0,
                    elementName.length - templateName.length
                ) + "\"" + ":" + elementValue
            else
                extravars += ",\"" + elementName.substring(
                    0,
                    elementName.length - templateName.length
                ) + "\"" + ":" + elementValue
    }
    return Pair(templateName.replace("\"", ""),extravars)
}

/**
 * Create API execute from JSON string sending by FORM.
 * HTTP POST to send a request body to a service.
 *
 * @jsonStr Input JSON as string.
 */
fun jsonExecuteAPI(jsonStr: String): String {
    val (templateName, extraVars) = generateAPIvalues(jsonStr)
    val client = OkHttpClient.Builder()
        .authenticator(object : Authenticator {
            @Throws(IOException::class)
            override fun authenticate(route: Route?, response: Response): Request? {
                if (response.request.header("Authorization") != null) {
                    return null // Give up, we've already attempted to authenticate.
                }

                println("Authenticating for response: $response")
                println("Challenges: ${response.challenges()}")
                val credential = Credentials.basic(toweradm, towerpass)
                return response.request.newBuilder()
                    .header("Authorization", credential)
                    .build()
            }
        })
        .build()

    val json = "{\"extra_vars\":{$extraVars}}"
    val mediaType = "application/json; charset=utf-8".toMediaType()
    val body = json.toRequestBody(mediaType)
    val url = "$towerhost/api/v2/workflow_job_templates/$templateName/launch/"

    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

    val response = client.newCall(request).execute()

    //Response
    //println("Response Body: $responseBody")

    return response.body!!.string()
}