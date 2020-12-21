package com.example.projectdva232v1.ui.learning_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.learning_activities.classes.Choice
import com.example.projectdva232v1.ui.learning_activities.classes.ListeningQuiz
import com.example.projectdva232v1.ui.learning_activities.classes.Question
import com.example.projectdva232v1.ui.learning_activities.classes.ReadingQuiz
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class ListeningActivity : AppCompatActivity() {
    lateinit var quiz: ListeningQuiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening)

        getData()
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
}