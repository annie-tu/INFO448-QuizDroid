package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    private lateinit var topicListView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        Log.i("MainActivity", filesDir.toString())
        Log.i("MainActivity", getExternalFilesDir(null).toString())

        val topicTitles: List<String> = QuizApp.instance.topicRepository.getTopics().map { it.title }
        // Log.i("Main","topic titles $topicTitles")
        val quizDroidTextView: TextView = findViewById(R.id.quizDroidTextView)
        quizDroidTextView.textSize = 25f
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.preferences_bar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, PreferencesActivity::class.java)
        startActivity(intent)
        return true
    }
}