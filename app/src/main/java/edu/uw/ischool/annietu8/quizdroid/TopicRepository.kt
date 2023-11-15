package edu.uw.ischool.annietu8.quizdroid

interface TopicRepository {
    fun getTopics(): List<Topic>
    fun getQuizByTopic(topicTitle: String): List<Quiz>
}