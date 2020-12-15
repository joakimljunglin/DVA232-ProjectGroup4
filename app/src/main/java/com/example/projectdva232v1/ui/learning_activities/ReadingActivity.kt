package com.example.projectdva232v1.ui.learning_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.learning_activities.utilities.getJsonDataFromAsset
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import org.json.JSONObject

class ReadingActivity : AppCompatActivity() {
    lateinit var btn: Button
    lateinit var chips: ChipGroup
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        getData()
        initView()
    }

    private fun getData() {
        val jsonFileString = getJsonDataFromAsset(applicationContext,
                "reading_sample.json")

        val gson = Gson()
    }

    private fun initView() {
        btn = findViewById(R.id.button)
        chips = findViewById(R.id.chips)
        progressBar = findViewById(R.id.progressBar)

        // Next button should not be enabled until an answer has been selected
        btn.isEnabled = false
        btn.setOnClickListener {
            // Clear all selections
            chips.clearCheck()

            // TODO: Remove below code
            progressBar.progress++
        }

        // For when a chip is clicked
        chips.setOnCheckedChangeListener { _, _ ->
            // Enable continue button

            // Make sure the button does not get enabled when a chip is deselected
            btn.isEnabled = chips.checkedChipId != View.NO_ID
        }
    }
}