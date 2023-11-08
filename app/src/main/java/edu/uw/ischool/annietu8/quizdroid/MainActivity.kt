package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var topicListView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topics = arrayOf(
            "Math",
            "Physics",
            "Marvel Superheroes")

        topicListView =  findViewById(R.id.topicListView)
        topicListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topics)
        topicListView.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            Toast.makeText(this, "You selected ${position}: ${topics[position]}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("topic", topics[position].toString())
            intent.putExtra("topicPos", position)
            /*val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.TOPIC, "Hi Grandma!")
                type = "text/plain"
            }*/

            startActivity(intent)
            //finish()
        }
    }
}