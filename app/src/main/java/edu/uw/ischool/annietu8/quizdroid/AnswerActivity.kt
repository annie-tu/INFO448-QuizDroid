package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class AnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val selectedTopic: String? = intent.getStringExtra("topic")
        val isCorrect: Boolean = intent.getBooleanExtra("correct", false)
        val givenAnswer: String? = intent.getStringExtra("answer")
        var questionIndex: Int = intent.getIntExtra("questionIndex", 0)
        var totalQuestions: Int = intent.getIntExtra("totalQuestions", 0)
        var numCorrect: Int = intent.getIntExtra("numCorrect", 0)
        val answer: String? = intent.getStringExtra("answer")
        //Toast.makeText(this, questionIndex, Toast.LENGTH_SHORT).show()

        val givenAnswerTextView: TextView = findViewById(R.id.givenAnswerTextView)
        val correctAnswerTextView: TextView = findViewById(R.id.correctAnswerTextView)
        val numCorrectTextView: TextView = findViewById(R.id.numCorrectTextView)
        givenAnswerTextView.text = givenAnswer
        correctAnswerTextView.text = answer
        var sentence = "You have $numCorrect out of $totalQuestions correct"
        numCorrectTextView.text = sentence
        val nextButton: Button = findViewById(R.id.nextButton)
        if (questionIndex == totalQuestions) {
            nextButton.text = "Finish"
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        nextButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("topic", selectedTopic)
            intent.putExtra("questionIndex", questionIndex++)
            intent.putExtra("numCorrect", numCorrect)
            startActivity(intent)
        }
    }
}