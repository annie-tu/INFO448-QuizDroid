package edu.uw.ischool.annietu8.quizdroid

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
        //topics = getTopicsFromJsonFile()
        //Log.i("TopicRepository", "topics $topics.toString()")
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
        val filePath = "/storage/emulated/0/Android/data/edu.uw.ischool.annietu8.quizdroid/files" + "/questions.json"
        val file = File(filePath)
        Log.i("TopicRepository", file.toString())

        try {
            val reader = BufferedReader(InputStreamReader(FileInputStream(file)))
            val jsonString = StringBuilder()

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                jsonString.append(line)
            }
            return parseJsonArray(jsonString.toString())
        } catch (e: Exception) {
            // Handle exceptions (file not found, IO errors, etc.)
            e.printStackTrace()
        }

        return emptyList()
    }

    private fun parseJsonArray(jsonString: String): List<Topic> {
        Log.i("TopicRepository", "parsing")
        val topics = mutableListOf<Topic>()
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonTopic = jsonArray.getJSONObject(i)
            Log.i("TopicRepository", jsonTopic.toString())
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
                Log.i("TopicRepository", "answer array $j $answersArray")
                val quiz = Quiz(questionText, jsonArrayToList(answersArray), answer - 1)
                quizObjects.add(quiz)
            }
            val topic = Topic(title, shortDescription, longDescription, quizObjects)
            Log.i("TopicRepository", "topic $topic")

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
class InMemoryTopicRepository : TopicRepository {
    // Hard-coded list of topics and quizzes
    private val topic1 = Topic(
        "Math",
        "math is super fun!",
        "Mathematics is a subject that deals with numbers, shapes, logic, quantity, and arrangements.",
        listOf(
            Quiz("What is 2+2?", listOf("1", "2", "3", "4"), 3),
            Quiz("What is 3+3", listOf("6", "0", "-5", "2"), 0),
            Quiz("What is the formula for the volume of a cone?",
                listOf("V = 1/2 * b * h",
                    "V = a^3",
                    "V = (1/3) * pi * r^2 * h",
                    "V = sin(x)"), 2)
        )
    )

    private val topic2 = Topic(
        "Physics",
        "love physics!",
        "Physics involves the study of matter and its motion and behavior through space and time.",
        listOf(
            Quiz("F = ?", listOf("va", "ma", "-9.8", "x"), 1),
            Quiz(
                "Momentum is...",
                listOf(
                    "mass * velocity",
                    "Earth's gravitational pull",
                    "A fictional concept",
                    "not related to magnitude or direction"
                ),
                0
            )
        )
    )

    private val topic3 = Topic(
        "Marvel Superheroes",
        "cool movies",
        "Marvel Comics is an American comic book publisher and popular movie franchise.",
        listOf(
            Quiz(
                "What is Iron Man's real name?",
                listOf("Tony Stark", "Steve Rogers", "Bruce Banner", "Pepper Potts"), 0
            ),
            Quiz(
                "What was the first Avengers movie (by US release date?",
                listOf(
                    "Iron Man",
                    "Captain America: The First Avenger",
                    "Thor",
                    "Doctor Strange"
                ), 0
            )
        )
    )
    private val topics: List<Topic> =  listOf(topic1, topic2, topic3)

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
        TODO("Not yet implemented")
    }
}