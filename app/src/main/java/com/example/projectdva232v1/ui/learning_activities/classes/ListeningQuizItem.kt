package com.example.projectdva232v1.ui.learning_activities.classes

import com.fasterxml.jackson.annotation.JsonProperty

data class ListeningQuizItem(
    @JsonProperty("text") val text: String,
    @JsonProperty("gap") val gap: String
)