package edu.uw.ischool.annietu8.quizdroid

interface TopicRepository {
    suspend fun getTopics(): List<Topic>
}