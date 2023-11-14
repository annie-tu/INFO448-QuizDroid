package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var topicListView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topicTitles: List<String> = QuizApp.instance.topicRepository.getTopicTitles()
        Log.i("Main","topic titles $topicTitles")

        topicListView =  findViewById(R.id.topicListView)
        topicListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topicTitles)
        topicListView.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("topic", topicTitles[position])
            intent.putExtra("topicPos", position)
            startActivity(intent)
        }
    }
}