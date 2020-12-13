package com.example.projectdva232v1.ui.learning_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projectdva232v1.R

// TODO: Remove from final version
// Used for easily navigating the learning test activities

class TestSelector : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_selector)
    }

    fun loadListeningPage(view: View) {
        val intent = Intent(this, ListeningActivity::class.java)
        startActivity(intent)
    }
    fun loadReadingPage(view: View) {
        val intent = Intent(this, ReadingActivity::class.java)
        startActivity(intent)
    }
    fun loadSpeakingPage(view: View) {
        val intent = Intent(this, SpeakingActivity::class.java)
        startActivity(intent)
    }
    fun loadWritingPage(view: View) {
        val intent = Intent(this, WritingActivity::class.java)
        startActivity(intent)
    }
    fun loadVocabularyPage(view: View) {
        val intent = Intent(this, VocabularyActivity::class.java)
        startActivity(intent)
    }
}