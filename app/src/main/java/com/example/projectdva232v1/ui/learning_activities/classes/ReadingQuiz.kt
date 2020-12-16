package com.example.projectdva232v1.ui.learning_activities.classes

import com.fasterxml.jackson.annotation.JsonProperty

data class ReadingQuiz(
    @JsonProperty("title" ) val title: String,
    @JsonProperty("instructions") val instructions: String,
    @JsonProperty("random") val random: Boolean,
    @JsonProperty("randomiseChoices") val randomiseChoices: Boolean,
    @JsonProperty("showQuestionNumbers") val showQuestionNumbers: Boolean,
    @JsonProperty("sectionsOnSamePage") val sectionsOnSamePage: Boolean,
    @JsonProperty("items") val items: List<ReadingQuizItem>
)