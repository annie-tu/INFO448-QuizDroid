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
    data class Question (
        val id: Int,
        val subject: String,
        val question: String,
        val optionOne: String,
        val optionTwo: String,
        val optionThree: String,
        val optionFour: String,
        val correctAnswer: Int
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val selectedTopic: String? = intent.getStringExtra("topic")
        var i: Int = intent.getIntExtra("questionIndex", 0)
        var numCorrect: Int = intent.getIntExtra("numCorrect", 0)
        Toast.makeText(this, selectedTopic, Toast.LENGTH_SHORT).show()


        val questionList = getQuestions(selectedTopic.toString())

        val questionTextView: TextView = findViewById(R.id.questionTextView)
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
        Log.i("QuestionActivity", "i=$i questionList size = ${questionList.size}")

        var answer: Int = -1
        questionTextView.text = questionList[i].question
        rb1.text = questionList[i].optionOne
        rb2.text = questionList[i].optionTwo
        rb3.text = questionList[i].optionThree
        rb4.text = questionList[i].optionFour
        answer = questionList[i].correctAnswer


        submitButton.setOnClickListener {
            val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
            val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
            val position: Int = radioGroup.indexOfChild(selectedRadioButton)
            if (position == answer - 1) {
                numCorrect++
                // Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
            } else {
                // Toast.makeText(this, "$selectedRadioButtonId $answer", Toast.LENGTH_SHORT).show()

            }
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("topic", selectedTopic)
            intent.putExtra("correct", position == answer - 1)
            intent.putExtra("answer", questionList[i].correctAnswer) // might be wrong
            intent.putExtra("questionIndex", i)
            intent.putExtra("totalQuestions", questionList.size)
            intent.putExtra("numCorrect", numCorrect)
            startActivity(intent)
        }
    }

    private fun getQuestions(topic: String): ArrayList<Question> {
        val questionList = listOf(
            Question(1, "Math", "What is 2+2?", "1", "2",
                "3", "4", 4),
            Question(2, "Math", "What is 3+3?", "6", "0", "-5", "2", 1),
            Question(3, "Physics", "F=?", "va", "ma", "-9.8", "x", 2),
            Question(4, "Physics",
            "How many centimeters in a meter?",
            "1", "100", "100", "1000", 3),
            Question(5, "Marvel Superheroes",
            "What is Iron Man's real name?",
            "Tony Stark", "Steve Rogers", "Bruce Banner", "Pepper Potts", 1),
            Question(6, "Marvel Superheroes",
            "What was the first Avengers Movie (by US Release Date)?",
            "Iron Man",
            "Captain America: The First Avenger",
            "Thor",
            "Doctor Strange", 1))
        val filteredList = questionList.filter {it.subject == topic}
        return ArrayList(filteredList)
    }

}