package com.questionnaire.fhir

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.springframework.http.*
import java.util.*


class FormatterHelper {

    fun getResponseEntity(addedResults: Results): ResponseEntity<*> {
        val statusCode = addedResults.statusCode
        val results = addedResults.details
        return when (statusCode) {
            201 -> { ResponseEntity(results, HttpStatus.CREATED) }
            200 -> { ResponseEntity(results, HttpStatus.OK) }
            else -> { ResponseEntity.badRequest().body(DbResults(results.toString())) }
        }
    }

    fun generateNumber(length: Int): String {
        return (1..length).shuffled().first().toString()
    }

    fun removeNullValues(root: Root):JsonObject {
        val gson = Gson()
        val json = gson.toJsonTree(root).asJsonObject
        val keysToRemove = json.entrySet().filter { it.value.isJsonNull }.map { it.key }
        keysToRemove.forEach { json.remove(it) }
        return json
    }


}