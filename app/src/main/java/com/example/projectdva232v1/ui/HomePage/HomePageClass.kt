package com.example.projectdva232v1.ui.HomePage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.Listening.ListeningPage
import com.example.projectdva232v1.ui.Reading.ReadingPage
import com.example.projectdva232v1.ui.Speaking.SpeakingPage
import com.example.projectdva232v1.ui.Vocabulary.VocabularyPage
import com.example.projectdva232v1.ui.Writing.WritingPage

class HomePageClass:AppCompatActivity(),RecyclerAdapterActivity.OnItemClickListener  {
    private var difficultySelected:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home_page)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false);
            supportActionBar?.setHomeButtonEnabled(false);
        }

        //Create an array with the possible difficulties
        val possibleDifficulties = resources.getStringArray(R.array.difficulty_array)
        // Create a list of all the difficulties to be displayed
        val difficultyList = ArrayList<DifficultyLevelItem>()
        for (diff in possibleDifficulties) {
            val item = DifficultyLevelItem(diff)
            difficultyList += item
        }
        // Setup for the recycler view
        val rv = findViewById<RecyclerView>(R.id.recycler_view_difficulties)
        rv.adapter = RecyclerAdapter(difficultyList, this)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.setHasFixedSize(true)


        //Create an array with the possible activities
        val possibleActivities = resources.getStringArray(R.array.activity_array)
        // Create a list of all the difficulties to be displayed
        val activityList = ArrayList<ActivityItem>()
        for (act in possibleActivities) {
            val icon = this.resources.getIdentifier(
                    act.toLowerCase(),
                    "drawable",
                    this.baseContext?.packageName
            )
            val item = ActivityItem(act, icon)
            activityList += item
        }
        // Setup for the recycler view
        val rva = findViewById<RecyclerView>(R.id.recycler_view_activities)
        rva.adapter = RecyclerAdapterActivity(activityList, this)
        rva.layoutManager = LinearLayoutManager(this)
        rva.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        //var fragment = ListeningFragment()
        if(difficultySelected.isNotEmpty() && difficultySelected.isNotBlank()){
            when (position) {
                0 -> {
                    val intent = Intent(this, ListeningPage::class.java)
                    intent.putExtra("diff", difficultySelected)
                    startActivity(intent)
                }

                1 -> {
                    val intent = Intent(this, VocabularyPage::class.java)
                    intent.putExtra("diff", difficultySelected)
                    startActivity(intent)
                }

                2 -> {
                    val intent = Intent(this, WritingPage::class.java)
                    intent.putExtra("diff", difficultySelected)
                    startActivity(intent)
                }

                3 -> {
                    val intent = Intent(this, SpeakingPage::class.java)
                    intent.putExtra("diff", difficultySelected)
                    startActivity(intent)
                }

                else -> {
                    val intent = Intent(this, ReadingPage::class.java)
                    intent.putExtra("diff", difficultySelected)
                    startActivity(intent)
                }
            }
        }
        else{
            Toast.makeText(this, "Select a difficulty level", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClicked(difficulty: String){
        difficultySelected = difficulty
    }

    override fun onStop() {
        super.onStop()
        difficultySelected = ""
    }
}