package templateSurvey_spec

data class Spec(
    val choices: String,
    val default: Any,
    val max: Int,
    val min: Int,
    val new_question: Boolean,
    val question_description: String,
    val question_name: String,
    val required: Boolean,
    val type: String,
    val variable: String
)