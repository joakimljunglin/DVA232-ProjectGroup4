package com.example.projectdva232v1.ui.learning_activities.classes

data class Quiz(
    val title: String, val instructions: String, val random: Boolean,
    val randomiseChoices: Boolean, val showQuestionNumbers: Boolean,
    val sectionsOnSamePage: Boolean, val item: List<Question>
)