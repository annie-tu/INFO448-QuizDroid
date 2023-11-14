package edu.uw.ischool.annietu8.quizdroid

interface TopicRepository {
    fun getTopics(): List<Topic>
    fun getTopicTitles(): List<String>
}