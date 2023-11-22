package edu.uw.ischool.annietu8.quizdroid

// PreferencesActivity.kt

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PreferencesActivity : AppCompatActivity() {

    private lateinit var urlEditText: EditText
    private lateinit var frequencyEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        // Initialize views
        urlEditText = findViewById(R.id.urlEditText)
        frequencyEditText = findViewById(R.id.frequencyEditText)

        // Load saved preferences
        loadPreferences()

        // Save button click listener
        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            savePreferences()
        }
    }

    private fun loadPreferences() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("your_preferences", Context.MODE_PRIVATE)

        val savedUrl: String? = sharedPreferences.getString("url", "default_url")
        val savedFrequency: String? = sharedPreferences.getString("frequency", "15")

        urlEditText.setText(savedUrl)
        frequencyEditText.setText(savedFrequency)
    }

    private fun savePreferences() {
        val url: String = urlEditText.text.toString()
        val frequency: String = frequencyEditText.text.toString()

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("your_preferences", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("url", url)
        editor.putString("frequency", frequency)
        editor.apply()

        Toast.makeText(this, "Preferences saved", Toast.LENGTH_SHORT).show()
        finish()
    }
}
