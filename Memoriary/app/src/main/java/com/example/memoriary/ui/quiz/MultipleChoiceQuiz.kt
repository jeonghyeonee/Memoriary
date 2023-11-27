package com.example.memoriary.ui.quiz

data class MultipleChoiceQuiz(
    val question: String,
    val choices: List<String>,
    val correctAnswerIndex: Int
) : Quiz {
    override fun getType(): QuizType = QuizType.MULTIPLE_CHOICE
}
