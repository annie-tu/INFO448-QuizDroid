package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class TopicOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val selectedTopicIndex : Int = intent.getIntExtra("topicPos", -1)
        val selectedTopic : String? = intent.getStringExtra("topic")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val quizApp = QuizApp.instance.topicRepository.getTopics()

        val topicDescriptions: List<String> = quizApp.map { it.descr }
        val numQuestions = quizApp.map {it.questions.size}

        var topicTextView :TextView = findViewById(R.id.topicTextView)
        var topicDescriptionTextView :TextView = findViewById(R.id.topicDescriptionTextView)
        var numQuestionsTextView: TextView = findViewById(R.id.numQuestionsTextView)

        topicTextView.text = selectedTopic
        topicTextView.textSize = 20f
        topicDescriptionTextView.text = topicDescriptions[selectedTopicIndex]
        numQuestionsTextView.text = "Number of Questions: " + numQuestions[selectedTopicIndex].toString()

        var beginButton : Button = findViewById(R.id.beginButton)
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("topic", selectedTopic)
            startActivity(intent)
        }
    }
}