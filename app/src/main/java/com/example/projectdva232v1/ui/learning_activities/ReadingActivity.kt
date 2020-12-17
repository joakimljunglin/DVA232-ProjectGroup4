package com.example.projectdva232v1.ui.learning_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

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

        val jsonFileString = getJsonDataFromAsset(applicationContext, "reading_sample.json")

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
            // Data could not be loaded
            Toast.makeText(this, "Failed to load the reading test", Toast.LENGTH_LONG).show()
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

        // Load text from quiz onto the main text view
        loadText()
        loadQuestion(currentQuestion)

        // Next button should not be enabled until an answer has been selected
        continueButton.isEnabled = false
        continueButton.setOnClickListener {
            // Add answer to answers list
            val answer = findViewById<Chip>(chips.checkedChipId).text
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
                val correctAnswers = controlAnswers(answers)
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
            // Enable continue button when a chip is selected
            continueButton.isEnabled = chips.checkedChipId != View.NO_ID
        }
    }

    private fun controlAnswers(answers: MutableList<Choice>): Int {
        // Count and return the number of correct answers

        var correct = 0
        for (item in answers) if (item.correct) correct++
        return correct
    }

    private fun loadText() {
        // Displays the main text as well as the gaps that need to be filled in
        // The gap for the current question is highlighted
        // Answered gaps are filled with the chosen answer

        var htmlText = ""

        // The space displayed on both sides of the gap number
        val tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"

        for ((i, item) in quiz.items.withIndex()) {
            htmlText += item.text1
            // Current logic needs to be redone if going back to a previous question or jumping between them becomes an option
            if (i + 1 == currentQuestion) {
                // Current question (highlight)
                htmlText += "<span style=\"background-color: #f8ff00\"><u>" + tab + (i + 1).toString() + tab + "</u></span>"
            } else if (i + 1 > currentQuestion) {
                // Unanswered question
                htmlText += "<span style=\"background-color: #DCDCDC\"><u>" + tab + (i + 1).toString() + tab + "</u></span>"
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
        // Loads the specified question
        // All chips are reset and changed to the new answers
        // As of now it only works for questions with exactly 4 answer options

        // Make sure the input is not a question which does not exist
        if (currentQuestion <= questions.size) {
            val q = questions[currentQuestion - 1]

            // TODO: consider possibility that the number of answers may not always be 4
            if (q.choices.size > 4) throw IllegalArgumentException("Number of choices has to be 4 as of now")

            questionTextView.text = q.text
            for ((i, choice) in q.choices.withIndex()) {
                (chips.getChildAt(i) as Chip).text = choice.text
            }

            // If it is the last question, then the button text changes from "continue" to "complete"
            if (currentQuestion == questions.size) {
                continueButton.text = getString(R.string.quiz_button_complete)
            }
        }
    }
}