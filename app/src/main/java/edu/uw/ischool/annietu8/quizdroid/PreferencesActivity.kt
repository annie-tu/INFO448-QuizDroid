package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        val urlEditText: EditText = findViewById(R.id.urlEditText)
        val frequencyEditText: EditText = findViewById(R.id.frequencyEditText)

        val currentUrl = preferences.getString("question_url", "/storage/emulated/0/Android/data/edu.uw.ischool.annietu8.quizdroid/files" + "/questions.json")

        val currentFrequency = preferences.getInt("update_frequency", 10)

        urlEditText.setText(currentUrl)
        frequencyEditText.setText(currentFrequency.toString())

        // Save button click listener
        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val editor = preferences.edit()
            editor.putString("question_url", urlEditText.text.toString())
            editor.putInt("update_frequency", frequencyEditText.text.toString().toInt())
            editor.apply()
            finish()
        }
    }
}