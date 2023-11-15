package edu.uw.ischool.annietu8.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class QuestionActivity : AppCompatActivity() {
    private var onFirstQuestionPage: Boolean = true
    private var i: Int = 0
    // private val submittedQuestionIds = HashSet<Int>() // prevent double-counting points
    data class Question (
        val id: Int,
        val subject: String,
        val question: String,
        val optionOne: String,
        val optionTwo: String,
        val optionThree: String,
        val optionFour: String,
        val correctAnswer: String
    )

    override fun onBackPressed() {
        if (onFirstQuestionPage) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            super.onBackPressed()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val selectedTopic: String = intent.getStringExtra("topic").toString()
        var i: Int = intent.getIntExtra("questionIndex", 0)
        var numCorrect: Int = intent.getIntExtra("numCorrect", 0)

        if (i > 0) {
            onFirstQuestionPage = false
        }

        val questionList = QuizApp.instance.topicRepository.getQuizByTopic(selectedTopic)
        // Log.i("QuestionActivity", "ql2 $questionList")

        val questionTextView: TextView = findViewById(R.id.questionTextView)
        val radioGroup : RadioGroup = findViewById(R.id.answerGroup)
        val rb1 : RadioButton = findViewById(R.id.rb1)
        val rb2 : RadioButton = findViewById(R.id.rb2)
        val rb3 : RadioButton = findViewById(R.id.rb3)
        val rb4 : RadioButton = findViewById(R.id.rb4)
        val submitButton : Button = findViewById(R.id.submitButton)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                submitButton.visibility = View.VISIBLE
            } else {
                submitButton.visibility = View.GONE
            }
        }
        val quiz = questionList[i]
        var answer: String = quiz.answers[quiz.correctAnswerIndex]
        questionTextView.text = quiz.question
        rb1.text = quiz.answers[0]
        rb2.text = quiz.answers[1]
        rb3.text = quiz.answers[2]
        rb4.text = quiz.answers[3]

        submitButton.setOnClickListener {
            val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
            val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
            val givenAnswer = selectedRadioButton.text
            // prevent double-counting points if user clicks back button and resubmits
            /*val questionId = questionList[i].id
            if (!submittedQuestionIds.contains(questionId)) {
                if (givenAnswer == answer) {
                    numCorrect++
                    submittedQuestionIds.add(questionId)
                }
            }*/
            if (givenAnswer == answer) {
                numCorrect++
            }
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("topic", selectedTopic)
            intent.putExtra("answer", answer)
            intent.putExtra("givenAnswer", givenAnswer)
            intent.putExtra("questionIndex", i)
            intent.putExtra("totalQuestions", questionList.size)
            intent.putExtra("numCorrect", numCorrect)
            startActivity(intent)
        }
    }
}
