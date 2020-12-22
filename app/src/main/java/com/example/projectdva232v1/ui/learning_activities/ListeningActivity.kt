package com.example.projectdva232v1.ui.learning_activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.widget.addTextChangedListener
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.learning_activities.classes.ListeningQuiz
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class ListeningActivity : AppCompatActivity() {
    lateinit var continueButton: Button
    lateinit var progressBar: ProgressBar
    lateinit var questionTextView: TextView
    lateinit var contentTextView: TextView
    lateinit var answerField: EditText
    lateinit var quiz: ListeningQuiz
    var currentQuestion = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening)

        getData()
        try {
            initView()
        }
        catch (e: UninitializedPropertyAccessException) {
            // Data could not be loaded, return to other page
            val intent = Intent(this, TestSelector::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        // Get data from the JSON file and save it in local variables

        val jsonFileString = getJsonDataFromAsset(applicationContext, "listening_sample.json")

        if (jsonFileString != null) {
            val mapper = jacksonObjectMapper()
            quiz = mapper.readValue<ListeningQuiz>(jsonFileString)

            // TODO: Do I need a list of "questions"?
            // TODO: Maybe a list of answers is needed
        } else {
            // Data could not be loaded
            Toast.makeText(this, "Failed to load the listening test", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        continueButton = findViewById(R.id.continue_button_listening)
        progressBar = findViewById(R.id.progressBar_listening)
        questionTextView = findViewById(R.id.textViewQuestion_listening)
        contentTextView = findViewById(R.id.textViewReading_listening)
        answerField = findViewById(R.id.editTextListeningAnswer)

        // Load text from quiz onto the main text view
        loadText()
        loadQuestion(currentQuestion)

        answerField.addTextChangedListener {
            // Enable continue when there is some sort of input text
            continueButton.isEnabled = answerField.text.isNotEmpty()
        }

        // Next button should not be enabled until an answer has been selected
        continueButton.isEnabled = false
    }

    private fun loadText() {
        // Displays the main text as well as the gaps that need to be filled in
        // The gap for the current question is highlighted
        // Answered gaps are filled with the chosen answer

        var htmlText = ""

        // The space displayed on both sides of the gap number
        val tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"

        for ((i, item) in quiz.items.withIndex()) {
            htmlText += item.text

            // Current question (highlight)
            // htmlText += "<span style=\"background-color: #f8ff00\"><u>" + tab + (i + 1).toString() + tab + "</u></span>"

            // Unanswered question
            htmlText += "<span style=\"background-color: #DCDCDC\"><u>" + tab + (i + 1).toString() + tab + "</u></span>"

            // Answered question
            // htmlText += "<span style=\"background-color: #DCDCDC\">$answer</span>"
        }
        contentTextView.text = HtmlCompat.fromHtml(htmlText, 0)
    }

    private fun loadQuestion(currentQuestion: Int) {
        // Loads the specified question

        questionTextView.text = "Complete sentence " + currentQuestion.toString()
    }
}