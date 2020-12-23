package com.example.projectdva232v1.ui.learning_activities.classes

class Answer(correctAnswer: String) {
    var enteredAnswer: String = ""
    val correctAnswer: String = correctAnswer
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