package com.example.projectdva232v1.ui.learning_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.learning_activities.classes.Choice
import com.example.projectdva232v1.ui.learning_activities.classes.Question
import com.example.projectdva232v1.ui.learning_activities.classes.ReadingQuiz
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.google.android.material.chip.ChipGroup
import com.fasterxml.jackson.module.kotlin.*
import com.google.android.material.chip.Chip
import kotlin.system.exitProcess

class ReadingActivity : AppCompatActivity() {
    lateinit var continueButton: Button
    lateinit var chips: ChipGroup
    lateinit var progressBar: ProgressBar
    lateinit var questionTextView: TextView
    lateinit var contentTextView: TextView
    lateinit var questions: MutableList<Question>
    lateinit var quiz: ReadingQuiz
    lateinit var answers: MutableList<Choice>
    var currentQuestion = 1
    var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        getData()
        initView()
    }

    private fun getData() {
        // Right now the full data including instructions is read at this page, this may be moved to the pre quiz page
        // in the future.

        val jsonFileString = getJsonDataFromAsset(
            applicationContext,
            "reading_sample.json"
        )

        if (jsonFileString != null) {
            val mapper = jacksonObjectMapper()
            quiz = mapper.readValue<ReadingQuiz>(jsonFileString)

            // Add questions to questions list
            questions = mutableListOf<Question>()
            var i = 1
            for (item in quiz.items) {
                val mutableList = mutableListOf<Choice>()
                for (choice in item.choices) {
                    mutableList.add(Choice(choice.text, choice.correct))
                }
                questions.add(Question("Choose answer $i", mutableList))
                i++
            }

        } else {
            // TODO
            // Could not read the quiz json
            // Error
        }
    }

    private fun initView() {
        continueButton = findViewById(R.id.button)
        chips = findViewById(R.id.answerChips)
        progressBar = findViewById(R.id.progressBar)
        questionTextView = findViewById(R.id.textViewQuestion)
        contentTextView = findViewById(R.id.textViewReading)

        answers = mutableListOf<Choice>()

        // Set progressbar max to number of total questions
        progressBar.max = questions.size

        // Load text from quiz into the main text view
        loadText()

        loadQuestion(currentQuestion)

        // Next button should not be enabled until an answer has been selected
        continueButton.isEnabled = false
        continueButton.setOnClickListener {
            // Check if answer was correct
            // TODO: replace with just storing the bool in answers
            val answer = findViewById<Chip>(chips.checkedChipId).text
            for (choice in questions[currentQuestion - 1].choices) {
                if (choice.text == answer && choice.correct) correctAnswers++
            }

            // Add answer to answers list
            for (choice in questions[currentQuestion - 1].choices) {
                if (choice.text == answer) answers.add(choice)
            }

            // Update progress
            progressBar.progress = currentQuestion

            // Check whether quiz has been completed
            if (currentQuestion == questions.size) {
                // Send to result page
                // TODO: Change to results page
                val intent = Intent(this, TestSelector::class.java)
                intent.putExtra("correctAnswers", correctAnswers)
                intent.putExtra("totalAnswers", questions.size)
                intent.putExtra("activity", "reading")
                startActivity(intent)
            }

            // Clear all selections
            chips.clearCheck()

            // Continue to next question
            currentQuestion++
            loadQuestion(currentQuestion)

            // Reload text so that the next question can be highlighted and previous answers filled in
            loadText()
        }

        // For when a chip is clicked
        chips.setOnCheckedChangeListener { _, _ ->
            // Enable continue button

            // Make sure the button does not get enabled when a chip is deselected
            continueButton.isEnabled = chips.checkedChipId != View.NO_ID
        }
    }

    private fun loadText() {
        // Sets up the text for the scrolling content
        var htmlText = ""

        for ((i, item) in quiz.items.withIndex()) {
            htmlText += item.text1
            if (i + 1 == currentQuestion) {
                // Highlighted selection
                htmlText += "<span style=\"background-color: #f8ff00\">answer " + (i + 1).toString() + "</span>"
            } else if (i + 1 > currentQuestion) {
                // Non answered question
                htmlText += "<span style=\"background-color: #DCDCDC\">answer " + (i + 1).toString() + "</span>"
            } else if (i + 1 < currentQuestion) {
                // Answered question
                val answer = answers[i].text
                htmlText += "<span style=\"background-color: #DCDCDC\">$answer</span>"
            }
            htmlText += item.text2
        }
        contentTextView.text = HtmlCompat.fromHtml(htmlText, 0)
    }

    private fun loadQuestion(currentQuestion: Int) {
        // Changes the chips and text to respond to the given question number

        // Make sure the input is not a question which does not exist
        if (currentQuestion <= questions.size) {
            val q = questions[currentQuestion - 1]

            // TODO: consider possibility that number of answers may not always be 4
            if (q.choices.size > 4) throw IllegalArgumentException("Number of choices has to be 4 as of now")

            questionTextView.text = q.text
            for ((i, choice) in q.choices.withIndex()) {
                (chips.getChildAt(i) as Chip).text = choice.text
            }

            if (currentQuestion == questions.size) {
                // Show that it is the last one by changing continue button text to complete
                continueButton.text = "complete"
            }
        }
    }
}