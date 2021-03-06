package com.example.projectdva232v1.ui.learning_activities.classes

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReadingQuizItem(
    @JsonProperty("text1") val text1: String,
    @JsonProperty("choices") val choices: List<ReadingQuizChoice>,
    @JsonProperty("text2") val text2: String
): Parcelable