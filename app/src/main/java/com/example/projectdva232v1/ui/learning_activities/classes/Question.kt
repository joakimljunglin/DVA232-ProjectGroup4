package com.example.projectdva232v1.ui.learning_activities.classes

import com.fasterxml.jackson.annotation.JsonProperty

data class Question(
    @JsonProperty("text1") val text1: String,
    @JsonProperty("choices") val choices: List<Choice>,
    @JsonProperty("text2") val text2: String
)