package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup

class QuestionActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val selectedTopicIndex : Int = intent.getIntExtra("topicPos", -1)

        val questions = arrayOf()

        val radioGroup : RadioGroup = findViewById(R.id.answerGroup)
        val rb1 : RadioButton = findViewById(R.id.rb1)
        val rb2 : RadioButton = findViewById(R.id.rb2)
        val rb3 : RadioButton = findViewById(R.id.rb3)
        val rb4 : RadioButton = findViewById(R.id.rb4)

        val submitButton : Button = findViewById(R.id.submitButton)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                // A radio button is selected, so make the submit button visible
                submitButton.visibility = View.VISIBLE
            } else {
                // No radio button is selected, so hide the submit button
                submitButton.visibility = View.GONE
            }
        }

        submitButton.setOnClickListener {
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("topicPos", selectedTopicIndex)
            startActivity(intent)

        }
        /*
        if (radioGroup.checkedRadioButtonId != -1) {
            submitButton.visibility = View.VISIBLE
            val selectedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val selectedText = selectedRadioButton.text.toString()


        } else {
            // No radio button is selected
            submitButton.visibility = View.INVISIBLE

        }*/
    }
}