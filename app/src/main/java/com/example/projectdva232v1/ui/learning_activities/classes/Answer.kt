package com.example.projectdva232v1.ui.learning_activities.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Answer(private val correctAnswer: String): Parcelable {
    var enteredAnswer: String = ""
    var correct: Boolean = false
    var answered: Boolean = false

    fun answer(answer: String) {
        enteredAnswer = answer
        correct = answer == correctAnswer
        answered = true
    }

    fun clear() {
        // Resets the question to its unanswered state
        enteredAnswer = ""
        correct = false
        answered = false
    }
}