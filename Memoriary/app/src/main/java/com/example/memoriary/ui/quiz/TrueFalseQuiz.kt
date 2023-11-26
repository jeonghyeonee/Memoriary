package com.example.memoriary.ui.quiz

data class TrueFalseQuiz(
    val question: String,
    val correctAnswer: Boolean
) : Quiz {
    override fun getType(): QuizType = QuizType.TRUE_FALSE
}