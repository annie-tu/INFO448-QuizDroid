package edu.uw.ischool.annietu8.quizdroid

data class Topic(
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val questions: List<Quiz>
)