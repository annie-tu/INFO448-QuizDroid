package edu.uw.ischool.annietu8.quizdroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
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
        scheduleDownloadService()

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
    private fun scheduleDownloadService() {
        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, DownloadService::class.java)
        val alarmIntent = PendingIntent.getService(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val frequency = getFrequencyFromPreferences()

        alarmMgr.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            frequency.toLong(),
            alarmIntent
        )
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
    private fun getFrequencyFromPreferences(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("frequency", "60") ?: "60"
    }


}