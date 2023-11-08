package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class TopicOverviewActivity : AppCompatActivity() {
    //private lateinit var intent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        //intent = intent
        val selectedTopicIndex : Int = intent.getIntExtra("topicPos", -1)
        val selectedTopic : String? = intent.getStringExtra("topic")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val topicDescriptions = arrayOf(
            "Math description",
            "Physics description",
            "Marvel description")
        Log.i("in topic overview", selectedTopic.toString())
        // fillPage(selectedTopic, selectedTopicIndex)

        var topicTextView :TextView = findViewById(R.id.topicTextView)
        var topicDescriptionTextView :TextView = findViewById(R.id.topicDescriptionTextView)
        var numQuestionsTextView: TextView = findViewById(R.id.numQuestionsTextView)

        topicTextView.text = selectedTopic
        topicDescriptionTextView.text = topicDescriptions[selectedTopicIndex]

    }

    /* private fun fillPage(s:String, i:Int) {
        var topicTextView :TextView = findViewById(R.id.topicTextView)
        var topicDescriptionTextView :TextView = findViewById(R.id.topicDescriptionTextView)
        var numQuestionsTextView: TextView = findViewById(R.id.numQuestionsTextView)

    }*/
}