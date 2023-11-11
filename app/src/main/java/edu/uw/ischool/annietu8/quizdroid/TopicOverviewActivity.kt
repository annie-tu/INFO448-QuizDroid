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

        val topicDescriptions = arrayOf(
            "Mathematics is a subject that deals with numbers, shapes, logic, quantity, and arrangements.",
            "Physics involves the study of matter and its motion and behavior through space and time.",
            "Marvel Comics is an American comic book publisher and popular movie franchise.")

        val numQuestions = arrayOf(2, 2, 2)
        var topicTextView :TextView = findViewById(R.id.topicTextView)
        var topicDescriptionTextView :TextView = findViewById(R.id.topicDescriptionTextView)
        var numQuestionsTextView: TextView = findViewById(R.id.numQuestionsTextView)

        topicTextView.text = selectedTopic
        topicDescriptionTextView.text = topicDescriptions[selectedTopicIndex]
        numQuestionsTextView.text = "Number of Questions: " + numQuestions[selectedTopicIndex.toInt()].toString()

        var beginButton : Button = findViewById(R.id.beginButton)
        beginButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("topic", selectedTopic)
            startActivity(intent)
        }
    }
}