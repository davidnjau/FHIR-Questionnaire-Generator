package com.questionnaire.fhir

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
        return Random().nextInt(length).toString()
    }


}