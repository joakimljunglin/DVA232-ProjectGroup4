package com.example.projectdva232v1.ui.learning_activities.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(val text: String, val choices: List<Choice>): Parcelable