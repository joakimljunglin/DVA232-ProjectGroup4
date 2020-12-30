package com.example.projectdva232v1.ui.learning_activities

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.widget.addTextChangedListener
import com.example.projectdva232v1.MainActivity
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.learning_activities.classes.Answer
import com.example.projectdva232v1.ui.learning_activities.classes.ListeningQuiz
import com.example.projectdva232v1.ui.learning_activities.utilities.controlAnswers
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.example.projectdva232v1.ui.learning_activities.utilities.getProgress
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File


class ListeningActivity : AppCompatActivity() {
    lateinit var continueButton: Button
    lateinit var previousButton: Button
    lateinit var audioButton: Button
    lateinit var audioTimeTextView: TextView
    lateinit var progressBar: ProgressBar
    lateinit var questionTextView: TextView
    lateinit var contentTextView: TextView
    lateinit var answerField: EditText
    lateinit var quiz: ListeningQuiz
    lateinit var answers: MutableList<Answer> // List of the user's selected answers
    private var mediaPlayer: MediaPlayer? = null
    private var audioTimer: CountDownTimer? = null
    var currentQuestion = 0
    var audioCurrentPosition = 0 // For tracking where the audio is when having to restore the activity
    var audioFinishedPlaying = false // Since the audio should not be replayable

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
                answers =
                    savedInstanceState.getParcelableArrayList<Answer>("ANSWERS")?.toMutableList()!!
                quiz = savedInstanceState.getParcelable<ListeningQuiz>("QUIZ")!!
                audioFinishedPlaying = savedInstanceState.getBoolean("FINISHED_PLAYING")
                audioCurrentPosition = savedInstanceState.getInt("AUDIO_POSITION")
            }
            initView()
        } catch (e: UninitializedPropertyAccessException) {
            // Data could not be loaded, return to previous page
            Toast.makeText(this, "Failed to load the listening test", Toast.LENGTH_LONG).show()

            // TODO: Set to select activity page once implemented
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("CURRENT_QUESTION", currentQuestion)
        outState.putParcelableArrayList("ANSWERS", ArrayList<Parcelable>(answers))
        outState.putParcelable("QUIZ", quiz)

        if (mediaPlayer != null && !audioFinishedPlaying) {
            outState.putInt("AUDIO_POSITION", mediaPlayer!!.currentPosition)
        } else {
            outState.putInt("AUDIO_POSITION", 0)
        }
        outState.putBoolean("FINISHED_PLAYING", audioFinishedPlaying)
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
        audioButton = findViewById(R.id.audio_button)
        audioTimeTextView = findViewById(R.id.textViewAudioTimeRemaining)
        progressBar = findViewById(R.id.progressBar_listening)
        questionTextView = findViewById(R.id.textViewQuestion_listening)
        contentTextView = findViewById(R.id.textViewReading_listening)
        answerField = findViewById(R.id.editTextListeningAnswer)

        // Set time to remaining time when restoring the activity
        if (audioCurrentPosition != 0 && !audioFinishedPlaying) {
            restorePlayer()
        } else if (audioFinishedPlaying) {
            audioTimeTextView.text = timeFormat(0)
        }

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

        audioButton.setOnClickListener {
            toggleAudio()
        }

        answerField.addTextChangedListener {
            // Enable continue when there is some sort of input text
            continueButton.isEnabled = answerField.text.isNotEmpty()
        }

        answerField.setOnFocusChangeListener { _: View, _: Boolean ->
            if (!answerField.hasFocus()) {
                // Hide keyboard when not focused
                answerField.hideKeyboard()
            }
        }
    }

    private fun toggleAudio() {
        // Pauses or plays the listening test audio

        if (audioFinishedPlaying) return // Do not allow playing the audio once it has finished

        // Create MediaPlayer if not already created
        if (mediaPlayer == null) {
            // Right now the JSON sample only provides a string containing the name of the audio file
            // Change later depending on where the audio files are actually stored

            // Get the filename without the ".mp3" extension
            val fileName = File(quiz.audio).nameWithoutExtension
            val audioUri =
                Uri.parse("android.resource://" + this.packageName + "/raw/" + fileName)

            mediaPlayer = MediaPlayer.create(this, audioUri)

            mediaPlayer?.setOnCompletionListener {
                // For when the audio has finished playing
                // It should not be replayable after this point
                stopPlayer()
                audioFinishedPlaying = true
                audioButton.visibility = View.GONE
            }

            // Forward to specified position (for when the activity is restored)
            mediaPlayer?.seekTo(audioCurrentPosition)
        }

        if (mediaPlayer?.isPlaying == true) {
            pausePlayer()
        } else {
            startPlayer()
        }

        // Also change text string from play to pause etc
        // OR have 2 different buttons and hide/show them if easier
    }

    private fun restorePlayer() {
        if (audioFinishedPlaying) return // Do not allow playing the audio once it has finished

        // Create MediaPlayer if not already created
        if (mediaPlayer == null) {
            // Right now the JSON sample only provides a string containing the name of the audio file
            // Change later depending on where the audio files are actually stored

            // Get the filename without the ".mp3" extension
            val fileName = File(quiz.audio).nameWithoutExtension
            val audioUri =
                Uri.parse("android.resource://" + this.packageName + "/raw/" + fileName)

            mediaPlayer = MediaPlayer.create(this, audioUri)

            mediaPlayer?.setOnCompletionListener {
                // For when the audio has finished playing
                // It should not be replayable after this point
                stopPlayer()
                audioFinishedPlaying = true
                audioButton.visibility = View.GONE
            }

            // Forward to specified position (for when the activity is restored)
            mediaPlayer?.seekTo(audioCurrentPosition)
            val timeLeft = mediaPlayer?.duration?.minus(audioCurrentPosition)?.toLong()
            audioTimeTextView.text = timeLeft?.let { timeFormat(it) }
        }
    }

    private fun startPlayer() {
        // Starting or resuming the audio

        mediaPlayer?.start()

        // Create a countdown timer to display the time left of the audio
        val timeLeft = mediaPlayer?.duration?.minus(mediaPlayer?.currentPosition!!)?.toLong()
        audioTimer = object: CountDownTimer(timeLeft!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                audioTimeTextView.text = timeFormat(millisUntilFinished)
            }

            override fun onFinish() {
                audioTimeTextView.text = timeFormat(0)
                audioFinishedPlaying = true
            }

        }.start()

        // Update text of button to pause audio
        audioButton.text = this.resources.getString(R.string.pause_audio)
    }

    private fun pausePlayer() {
        // Pausing the audio
        mediaPlayer?.pause()

        // Since countdown timer cannot be paused and resumed it has to be cancelled and re-created
        audioTimer?.cancel()

        // Update text of button to play audio
        audioButton.text = this.resources.getString(R.string.play_audio)
    }

    private fun stopPlayer() {
        // For releasing the MediaPlayer object

        mediaPlayer?.release()
        mediaPlayer = null
    }

    // When leaving the app
    override fun onStop() {
        super.onStop()
        pausePlayer()
    }

    // When the activity gets destroyed
    override fun onDestroy() {
        super.onDestroy()
        stopPlayer()
    }

    // For formatting the text used to display time left of audio
    private fun timeFormat(milliseconds: Long): String {
        // Input milliseconds
        // Convert to 3:40 format (M:SS)
        // Return as string

        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60

        // Add a 0 before the number if less than 10 seconds
        if (seconds < 10) {
            return "-$minutes:0$seconds"
        }
        return "-$minutes:$seconds"
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
            val intent = Intent(this, MainActivity::class.java)
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

            val text =
                getString(R.string.complete_sentence) + " " + (currentQuestion + 1).toString()
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

    // Hide the soft keyboard
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}