package com.example.memoriary.ui.quiz

enum class QuizType {
    MULTIPLE_CHOICE,
    SHORT_ANSWER,
    TRUE_FALSE
}

interface Quiz {
    fun getType(): QuizType
}