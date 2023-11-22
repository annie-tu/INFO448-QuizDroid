package edu.uw.ischool.annietu8.quizdroid

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
data class Topic(
    val title: String,
    val shortDescription: String,
    val descr: String,
    val questions: List<Quiz>,
)
data class Quiz(
    val text: String,
    val answers: List<String>,
    val answer : Int
)
interface TopicRepository {
    fun getTopics(): List<Topic>
    fun getQuizByTopic(topicTitle: String): List<Quiz>
    fun getTopicsFromJsonFile(): List<Topic>
}
class JsonFileTopicRepository(private val context: Context) : TopicRepository {
    private val topics: List<Topic>
    init {
        Log.i("TopicRepository", "starting")
        topics = getTopicsFromJsonFile()
    }
    override fun getTopics(): List<Topic> {
        return topics
    }
    override fun getQuizByTopic(topicTitle: String): List<Quiz> {
        return topics
            .firstOrNull { it.title == topicTitle }
            ?.questions
            ?: emptyList()
    }

    override fun getTopicsFromJsonFile(): List<Topic> {
        val filePath = "/storage/emulated/0/Android/data/edu.uw.ischool.annietu8.quizdroid/files" + "/extra_questions.json"
        val file = File(filePath)
        val url = URL("http://tednewardsandbox.site44.com/questions.json")
        val connection = url.openConnection() as HttpURLConnection
        Log.i("TopicRepository", "connection $connection")

        try {
            val reader = InputStreamReader(FileInputStream(file))
            //val reader = InputStreamReader(connection.inputStream)
            Log.i("TopicRepository", "reader $reader")
            val content = reader.readText()
            Log.i("TopicRepository", content)
            return parseJsonArray(content)
        } catch (e: Exception) {
            // Handle exceptions (file not found, IO errors, etc.)
            e.printStackTrace()
        }

        return emptyList()
    }

    private fun parseJsonArray(jsonString: String): List<Topic> {
        val topics = mutableListOf<Topic>()
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonTopic = jsonArray.getJSONObject(i)
            val title = jsonTopic.getString("title")
            val shortDescription = jsonTopic.getString("desc")
            val longDescription = jsonTopic.getString("desc")
            val questionArray = jsonTopic.getJSONArray("questions")
            val quizObjects = mutableListOf<Quiz>()
            for (j in 0 until questionArray.length()) {
                val questionObject = questionArray.getJSONObject(j)
                val questionText = questionObject.getString("text")
                val answer = questionObject.getInt("answer")
                val answersArray = questionObject.getJSONArray("answers")
                val quiz = Quiz(questionText, jsonArrayToList(answersArray), answer - 1)
                quizObjects.add(quiz)
            }
            val topic = Topic(title, shortDescription, longDescription, quizObjects)
            topics.add(topic)
        }
        return topics
    }
    private fun jsonArrayToList(jsonArray: JSONArray): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }
}
