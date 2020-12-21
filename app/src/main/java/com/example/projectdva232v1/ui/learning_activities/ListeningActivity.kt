package com.example.projectdva232v1.ui.learning_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.learning_activities.classes.Choice
import com.example.projectdva232v1.ui.learning_activities.classes.ListeningQuiz
import com.example.projectdva232v1.ui.learning_activities.classes.Question
import com.example.projectdva232v1.ui.learning_activities.classes.ReadingQuiz
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class ListeningActivity : AppCompatActivity() {
    lateinit var continueButton: Button
    lateinit var progressBar: ProgressBar
    lateinit var questionTextView: TextView
    lateinit var contentTextView: TextView
    lateinit var quiz: ListeningQuiz

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

        // Load text from quiz onto the main text view
        loadText()
    }

    private fun loadText() {
        // Displays the main text as well as the gaps that need to be filled in
        // The gap for the current question is highlighted
        // Answered gaps are filled with the chosen answer

        // TODO: fill, also see whether the function can be made independent of activity
    }
}