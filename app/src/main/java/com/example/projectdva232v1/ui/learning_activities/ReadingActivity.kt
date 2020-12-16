package com.example.projectdva232v1.ui.learning_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.learning_activities.classes.Question
import com.example.projectdva232v1.ui.learning_activities.classes.ReadingQuiz
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.google.android.material.chip.ChipGroup
import com.fasterxml.jackson.module.kotlin.*
import org.json.JSONObject

class ReadingActivity : AppCompatActivity() {
    lateinit var btn: Button
    lateinit var chips: ChipGroup
    lateinit var progressBar: ProgressBar
    lateinit var questions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        getData()
        initView()
    }

    private fun getData() {
        // Right now the full data including instructions is read at this page, this may be moved to the pre quiz page
        // in the future.

        val jsonFileString = getJsonDataFromAsset(applicationContext,
                "reading_sample.json")

        if (jsonFileString != null) {
            val mapper = jacksonObjectMapper()
            var quiz: ReadingQuiz = mapper.readValue<ReadingQuiz>(jsonFileString)

            Log.d("DEBUG", quiz.instructions)

            // Populate questions
        } else {
            // TODO
            // Could not read the quiz json
            // Error
        }
    }

    private fun initView() {
        btn = findViewById(R.id.button)
        chips = findViewById(R.id.chips)
        progressBar = findViewById(R.id.progressBar)

        // Next button should not be enabled until an answer has been selected
        btn.isEnabled = false
        btn.setOnClickListener {
            // Clear all selections
            chips.clearCheck()

            // TODO: Remove below code
            progressBar.progress++
        }

        // For when a chip is clicked
        chips.setOnCheckedChangeListener { _, _ ->
            // Enable continue button

            // Make sure the button does not get enabled when a chip is deselected
            btn.isEnabled = chips.checkedChipId != View.NO_ID
        }
    }
}