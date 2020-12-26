package com.example.projectdva232v1.ui.learning_activities.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Choice(val text: String, val correct: Boolean): Parcelable