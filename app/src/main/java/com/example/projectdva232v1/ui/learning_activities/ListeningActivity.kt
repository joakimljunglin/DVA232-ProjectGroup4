package com.example.projectdva232v1.ui.learning_activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.widget.addTextChangedListener
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.learning_activities.classes.Answer
import com.example.projectdva232v1.ui.learning_activities.classes.ListeningQuiz
import com.example.projectdva232v1.ui.learning_activities.classes.Question
import com.example.projectdva232v1.ui.learning_activities.classes.ReadingQuiz
import com.example.projectdva232v1.ui.learning_activities.utilities.controlAnswers
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.example.projectdva232v1.ui.learning_activities.utilities.getProgress
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.chip.Chip


class ListeningActivity : AppCompatActivity() {
    lateinit var continueButton: Button
    lateinit var previousButton: Button
    lateinit var progressBar: ProgressBar
    lateinit var questionTextView: TextView
    lateinit var contentTextView: TextView
    lateinit var answerField: EditText
    lateinit var quiz: ListeningQuiz
    lateinit var answers: MutableList<Answer> // List of the user's selected answers
    var currentQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening)

        try {
            if (savedInstanceState == null) {
                // Get the required data only when there is no saved instance
                getData()
            } else {
                // Restore values from saved instance
                currentQuestion = savedInstanceState.getInt("CURRENT_QUESTION")
                answers = savedInstanceState.getParcelableArrayList<Answer>("ANSWERS")?.toMutableList()!!
                quiz = savedInstanceState.getParcelable<ListeningQuiz>("QUIZ")!!
            }
            initView()
        } catch (e: UninitializedPropertyAccessException) {
            // Data could not be loaded, return to previous page
            Toast.makeText(this, "Failed to load the listening test", Toast.LENGTH_LONG).show()

            // TODO: Set to select activity page once implemented
            val intent = Intent(this, TestSelector::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("CURRENT_QUESTION", currentQuestion)
        outState.putParcelableArrayList("ANSWERS", ArrayList<Parcelable>(answers))
        outState.putParcelable("QUIZ", quiz)
    }

    private fun getData() {
        // Get data from the JSON file and save it in local variables

        val jsonFileString = getJsonDataFromAsset(applicationContext, "listening_sample.json")

        if (jsonFileString != null) {
            val mapper = jacksonObjectMapper()
            quiz = mapper.readValue<ListeningQuiz>(jsonFileString)

            // Initialize & fill answers list
            answers = mutableListOf<Answer>()
            for (item in quiz.items) {
                answers.add(Answer(item.gap))
            }
        }
    }

    private fun initView() {
        continueButton = findViewById(R.id.continue_button_listening)
        previousButton = findViewById(R.id.previous_button_listening)
        progressBar = findViewById(R.id.progressBar_listening)
        questionTextView = findViewById(R.id.textViewQuestion_listening)
        contentTextView = findViewById(R.id.textViewReading_listening)
        answerField = findViewById(R.id.editTextListeningAnswer)

        // Set progressbar max to number of total questions
        progressBar.max = answers.size

        // Load text from quiz onto the main text view
        loadText(currentQuestion)
        loadQuestion(currentQuestion)

        // Next button should not be enabled if no text is entered
        continueButton.isEnabled = answerField.text.isNotEmpty()

        continueButton.setOnClickListener {
            questionContinue()
        }

        previousButton.setOnClickListener {
            questionPrevious()
        }

        answerField.addTextChangedListener {
            // Enable continue when there is some sort of input text
            continueButton.isEnabled = answerField.text.isNotEmpty()
        }
    }

    private fun questionContinue() {
        // When clicking the continue/complete button

        // Add answer to answers list
        answers[currentQuestion].answer(answerField.text.toString())

        // Update progress
        progressBar.progress = getProgress(answers)

        // Check whether quiz has been completed
        if (currentQuestion + 1 == answers.size) {
            // Send to result page
            // TODO: Change to results page
            val intent = Intent(this, TestSelector::class.java)
            val correctAnswers = controlAnswers(answers)
            intent.putExtra("correctAnswers", correctAnswers)
            intent.putExtra("totalAnswers", answers.size)
            intent.putExtra("activity", "listening")
            startActivity(intent)
        }

        // Clear input
        answerField.text.clear()

        // Continue to next question
        currentQuestion++
        loadQuestion(currentQuestion)

        // Reload text so that the next question can be highlighted and previous answers filled in
        loadText(currentQuestion)
    }

    private fun questionPrevious() {
        // When clicking the previous question button

        if (answerField.text.isEmpty()) {
            // If no text is entered, set answer to unanswered
            answers[currentQuestion].clear()
        } else {
            // Set answer to entered text
            answers[currentQuestion].answer(answerField.text.toString())
        }

        // Clear input
        answerField.text.clear()

        currentQuestion--
        loadQuestion(currentQuestion)

        // Reload text
        loadText(currentQuestion)

        // Update progress
        progressBar.progress = getProgress(answers)
    }

    private fun loadText(currentQuestion: Int) {
        // Displays the text for the current question
        // Answered gaps are filled with the chosen answer

        var htmlText = ""

        // The space displayed on both sides of the gap number
        val tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"

        for ((i, item) in quiz.items.withIndex()) {
            htmlText += item.text

            if (i == currentQuestion) {
                // Highlight current question with different background-color
                htmlText += "<span style=\"background-color: #f8ff00\">"
            } else {
                htmlText += "<span style=\"background-color: #DCDCDC\">"
            }

            if (answers[i].answered) {
                // Display answer if answered
                htmlText += answers[i].enteredAnswer
            } else {
                // Display question number surrounded by tabs if not
                htmlText += "<u>" + tab + (i + 1).toString() + tab + "</u>"
            }

            htmlText += "</span>"
        }
        contentTextView.text = HtmlCompat.fromHtml(htmlText, 0)
    }

    private fun loadQuestion(currentQuestion: Int) {
        // Loads the specified question

        // Make sure the input is not a question which does not exist
        if (currentQuestion + 1 <= answers.size) {
            if (currentQuestion > 0) {
                // Show previous button
                previousButton.visibility = View.VISIBLE
            } else {
                // Hide previous button (on the first question)
                previousButton.visibility = View.GONE
            }

            val text = getString(R.string.complete_sentence) + " " + (currentQuestion + 1).toString()
            questionTextView.text = text


            // Check if currentQuestion has already been answered
            // If answered, pre-set text to answer
            if (answers[currentQuestion].answered) {
                answerField.setText(answers[currentQuestion].enteredAnswer)
            }

            // If it is the last question, then the button text changes from "continue" to "complete"
            if (currentQuestion + 1 == answers.size) {
                continueButton.text = getString(R.string.quiz_button_complete)
            } else {
                continueButton.text = getString(R.string.quiz_button_continue)
            }
        }
    }
}