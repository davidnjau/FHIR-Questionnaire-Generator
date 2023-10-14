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
data class Extension(
        val url: String,
        val valueExpression: ValueExpression?,
        val valueCode: String?
)

data class ValueExpression(
        val language: String,
        val expression: String,
        val name: String
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

data class Item(
        val text: String,
        val linkId: String,
        val type: String,
        val repeats: Boolean,
        val extension: List<Extension>?,
        val answerOption: List<AnswerOption>?,
        val enableWhen: List<EnableWhen>?,
        val definition: String?,
        val initial: List<ValueCoding>?
)

data class Root(
        val title: String,
        val status: String,
        val version: String,
        val publisher: String,
        val resourceType: String,
        val subjectType: List<String>,
        val extension: List<Extension>,
        val item: List<Item>
)
enum class Standards{
    ACTIVE,
    Questionnaire,
    Encounter
}