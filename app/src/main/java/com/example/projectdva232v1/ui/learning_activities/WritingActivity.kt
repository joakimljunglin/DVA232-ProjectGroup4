package com.example.projectdva232v1.ui.learning_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.projectdva232v1.R

class WritingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing)

        // Setup the writing activity
        val textView: TextView = findViewById(R.id.textViewWritingActivity)
        textView.text = getString(R.string.writing_activity_sample_text)

        //TODO: also include source "Adapted from (Jensen..."
        //TODO: Set margin/padding or something on the text, also make the font bigger (look at conventions)
    }
}