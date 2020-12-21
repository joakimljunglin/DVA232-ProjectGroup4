package com.example.projectdva232v1.ui.learning_activities.classes

import com.fasterxml.jackson.annotation.JsonProperty

data class ListeningQuiz(
    @JsonProperty("title" ) val title: String,
    @JsonProperty("instructions") val instructions: String,
    @JsonProperty("audio") val audio: String,
    @JsonProperty("items") val items: List<ReadingQuizItem>
)