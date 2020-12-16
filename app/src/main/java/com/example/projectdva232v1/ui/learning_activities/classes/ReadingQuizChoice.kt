package com.example.projectdva232v1.ui.learning_activities.classes

import com.fasterxml.jackson.annotation.JsonProperty

data class ReadingQuizChoice(
    @JsonProperty("text") val text: String,
    @JsonProperty("correct") val correct: Boolean,
    @JsonProperty("feedback") val feedback: String
)