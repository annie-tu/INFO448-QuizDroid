package edu.uw.ischool.annietu8.quizdroid

data class Quiz(
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex : Int
)