package com.example.projectdva232v1.ui.learning_activities.utilities

import com.example.projectdva232v1.ui.learning_activities.classes.Answer

fun getProgress(answers: List<Answer>): Int {
    // Returns number of answered questions

    var progress = 0

    for (answer in answers) {
        if (answer.answered) progress++
    }

    return progress
}

fun controlAnswers(answers: List<Answer>): Int {
    // Count and return the number of correct answers

    var correct = 0
    for (item in answers) if (item.correct) correct++
    return correct
}