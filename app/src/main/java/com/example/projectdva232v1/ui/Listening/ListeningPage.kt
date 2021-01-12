package com.example.projectdva232v1.ui.Listening

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.HomePage.DifficultyLevelItem
import com.example.projectdva232v1.ui.learning_activities.ListeningActivity

class ListeningPage:AppCompatActivity(), RecyclerAdapterListening.OnItemClickListener {
    var activityList = ArrayList<DifficultyLevelItem>()
    lateinit var difficultySelected: String

    override fun onCreate(
            savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reading_activity)

        difficultySelected = intent.getStringExtra("diff").toString()


        val activities = resources.getStringArray(R.array.difficulty_array)
        activityList.clear()
        for (diff in activities) {
            if (diff == difficultySelected) {
                val item = DifficultyLevelItem(diff)
                activityList.plusAssign(item)
            }
        }
        // Setup for the recycler view
        val rv = findViewById<RecyclerView>(R.id.recycler_view_activity_reading)
        rv.adapter = RecyclerAdapterListening(activityList, this)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.setHasFixedSize(true)

    }

    override fun onItemClick(position: Int) {
        for (index in 0..activityList.size) {
            if (position == index) {

                val intent = Intent(this, ListeningActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}