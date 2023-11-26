package com.example.memoriary.ui.quiz

data class ShortAnswerQuiz(
    val question: String,
    val correctAnswer: String
) : Quiz {
    override fun getType(): QuizType = QuizType.SHORT_ANSWER
}