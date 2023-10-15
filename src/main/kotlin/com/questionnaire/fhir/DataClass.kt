package com.questionnaire.fhir


data class Results(
        val statusCode: Int,
        val details: Any
)
data class DbResults(
        val details: String
)
data class DbEncounter(
        val title: String,
        val publisher: String,
        val item: List<DbItems>
)
data class DbItems(
        val text: String,
        val isPageable: Boolean?,
        val questionItems: List<DbQuestionItems>
)
data class DbQuestionItems(
        val question: String,
        val type: String,
        val allowRepeat:Boolean?,
        val answerOption: List<ValueCoding>
)



data class ValueCodeableConcept(
        val text: String?,
        val coding: List<ValueCoding>
)


data class AnswerOption(
        val valueCoding: ValueCoding
)

data class ValueCoding(
        val code: String,
        val display: String,
        val system: String
)

data class EnableWhen(
        val question: String,
        val operator: String,
        val answerCoding: ValueCoding
)



data class Root(
        val title: String,
        val status: String,
        val version: String,
        val publisher: String,
        val resourceType: String,
        val subjectType: List<String>,
        val extension: List<Level1Extension>,
        val item: List<FhirItem>
)
data class Level1Extension(
        val url: String,
        val valueExpression: ValueExpression?,
        val valueCode: String?,
        val valueCodeableConcept:ValueCodeableConcept?,
        val valueBoolean:Boolean?
)
data class ValueExpression(
        val language: String,
        val expression: String,
        val name: String
)
data class FhirItem(
        val text: String?,
        val linkId: String,
        val type: String,
        val extension: List<Level1Extension>?,

        //2nd Level item
        val repeats: Boolean?,
        val answerOption: List<AnswerOption>?,

        //3rd Level item
        val enableWhen: List<EnableWhen>?,
        val item: List<FhirItem>?,

        //4th Level item
        val definition:String?,
        val initial: List<ValueCoding>?
)


enum class Standards{
    active,
    Questionnaire,
    Encounter,
    group,
    page,
    Checkbox
}