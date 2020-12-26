package com.example.projectdva232v1.ui.learning_activities.classes

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListeningQuiz(
    @JsonProperty("title" ) val title: String,
    @JsonProperty("instructions") val instructions: String,
    @JsonProperty("audio") val audio: String,
    @JsonProperty("items") val items: List<ListeningQuizItem>
): Parcelable