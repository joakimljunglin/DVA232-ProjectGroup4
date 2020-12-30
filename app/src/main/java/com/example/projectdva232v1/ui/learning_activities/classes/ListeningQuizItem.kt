package com.example.projectdva232v1.ui.learning_activities.classes

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListeningQuizItem(
    @JsonProperty("text") val text: String,
    @JsonProperty("gap") val gap: String
): Parcelable