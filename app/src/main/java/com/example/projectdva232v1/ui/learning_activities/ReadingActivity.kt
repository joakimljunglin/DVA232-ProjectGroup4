package com.example.projectdva232v1.ui.learning_activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.projectdva232v1.MainActivity
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.ResultActivity.ResultActivity
import com.example.projectdva232v1.ui.learning_activities.classes.Answer
import com.example.projectdva232v1.ui.learning_activities.classes.Choice
import com.example.projectdva232v1.ui.learning_activities.classes.Question
import com.example.projectdva232v1.ui.learning_activities.classes.ReadingQuiz
import com.example.projectdva232v1.ui.learning_activities.utilities.controlAnswers
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.example.projectdva232v1.ui.learning_activities.utilities.getProgress
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ReadingActivity : AppCompatActivity() {
    lateinit var continueButton: Button
    lateinit var previousButton: Button
    lateinit var chips: ChipGroup
    lateinit var progressBar: ProgressBar
    lateinit var questionTitleTextView: TextView // The text view displaying the current question number
    lateinit var mainContentTextView: TextView // The text view displaying all the html content
    lateinit var quiz: ReadingQuiz
    lateinit var questions: MutableList<Question> // The questions, including the possible answers
    lateinit var answers: MutableList<Answer> // List of the user's selected answers
    var currentQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        try {
            if (savedInstanceState == null) {
                // Get the required data only when there is no saved instance
                getData()
            } else {
                // Restore values from saved instance
                currentQuestion = savedInstanceState.getInt("CURRENT_QUESTION")
                questions = savedInstanceState.getParcelableArrayList<Question>("QUESTIONS")?.toMutableList()!!
                answers = savedInstanceState.getParcelableArrayList<Answer>("ANSWERS")?.toMutableList()!!
                quiz = savedInstanceState.getParcelable<ReadingQuiz>("QUIZ")!!
            }
            initView()
        } catch (e: UninitializedPropertyAccessException) {
            // Data could not be loaded, return to previous page
            Toast.makeText(this, "Failed to load the reading test", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("CURRENT_QUESTION", currentQuestion)
        outState.putParcelableArrayList("QUESTIONS", ArrayList<Parcelable>(questions))
        outState.putParcelableArrayList("ANSWERS", ArrayList<Parcelable>(answers))
        outState.putParcelable("QUIZ", quiz)
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

            // Initialize & fill answers list
            answers = mutableListOf<Answer>()
            for (question in questions) {
                for (choice in question.choices) {
                    if (choice.correct) {
                        answers.add(Answer(choice.text))
                    }
                }
            }
        }
    }

    private fun initView() {
        continueButton = findViewById(R.id.button)
        previousButton = findViewById(R.id.button_previous)
        chips = findViewById(R.id.answerChips)
        progressBar = findViewById(R.id.progressBar)
        questionTitleTextView = findViewById(R.id.textViewQuestion)
        mainContentTextView = findViewById(R.id.textViewReading)

        // Set progressbar max to number of total questions
        progressBar.max = questions.size

        // Load text from quiz onto the main text view
        loadText()
        loadQuestion(currentQuestion)

        // Next button should not be enabled if no answer is selected
        continueButton.isEnabled = chips.checkedChipId != View.NO_ID

        continueButton.setOnClickListener {
            questionContinue()
        }

        previousButton.setOnClickListener {
            questionPrevious()
        }

        // For when a chip is clicked
        chips.setOnCheckedChangeListener { _, _ ->
            // Enable continue button when a chip is selected
            continueButton.isEnabled = chips.checkedChipId != View.NO_ID
        }
    }

    private fun questionContinue() {
        // When clicking the continue/complete button

        // Add answer to answers list
        val answer = findViewById<Chip>(chips.checkedChipId).text
        for (choice in questions[currentQuestion].choices) {
            if (choice.text == answer) answers[currentQuestion].answer(choice.text)
        }

        // Update progress
        progressBar.progress = getProgress(answers)

        // Check whether quiz has been completed
        if (currentQuestion + 1 == questions.size) {
            // Send to result page
            val intent = Intent(this, ResultActivity::class.java)
            val correctAnswers = controlAnswers(answers)
            intent.putExtra("correctAnswers", correctAnswers)
            intent.putExtra("totalAnswers", questions.size)
            intent.putExtra("activity", "reading")
            startActivity(intent)
        }

        // Continue to next question
        currentQuestion++
        loadQuestion(currentQuestion)

        // Reload text so that the next question can be highlighted and previous answers filled in
        loadText()
    }

    private fun questionPrevious() {
        // When clicking the previous question button

        if (chips.checkedChipId == View.NO_ID) {
            // If no chip is selected, set answer to unanswered
            answers[currentQuestion].clear()
        } else {
            // Set answer to selected chip
            val answer = findViewById<Chip>(chips.checkedChipId).text
            for (choice in questions[currentQuestion].choices) {
                if (choice.text == answer) answers[currentQuestion].answer(choice.text)
            }
        }

        currentQuestion--
        loadQuestion(currentQuestion)

        // Reload text
        loadText()

        // Update progress
        progressBar.progress = getProgress(answers)
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

            htmlText += item.text2
        }
        mainContentTextView.text = HtmlCompat.fromHtml(htmlText, 0)
    }

    private fun loadQuestion(currentQuestion: Int) {
        // Loads the specified question
        // All chips are reset and changed to the new answers
        // As of now it only works for questions with exactly 4 answer options

        // Make sure the input is not a question which does not exist
        if (currentQuestion + 1 <= questions.size) {
            if (currentQuestion > 0) {
                // Show previous button
                previousButton.visibility = View.VISIBLE
            } else {
                // Hide previous button (on the first question)
                previousButton.visibility = View.GONE
            }

            val q = questions[currentQuestion]

            // TODO: consider possibility that the number of answers may not always be 4
            if (q.choices.size > 4) throw IllegalArgumentException("Number of choices has to be 4 as of now")

            // Clear selection from previous question
            chips.clearCheck()

            questionTitleTextView.text = q.text
            for ((i, choice) in q.choices.withIndex()) {
                val chip = (chips.getChildAt(i) as Chip)
                chip.text = choice.text

                // Check if currentQuestion has already been answered
                // If answered, pre-select the chip of the answer
                if (answers[currentQuestion].answered) {
                    if (chip.text == answers[currentQuestion].enteredAnswer) {
                        chips.check(chip.id)
                    }
                }
            }

            // If it is the last question, then the button text changes from "continue" to "complete"
            if (currentQuestion + 1 == questions.size) {
                continueButton.text = getString(R.string.quiz_button_complete)
            } else {
                continueButton.text = getString(R.string.quiz_button_continue)
            }
        }
    }
}