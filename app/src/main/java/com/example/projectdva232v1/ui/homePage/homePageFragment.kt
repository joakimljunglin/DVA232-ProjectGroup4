package com.example.projectdva232v1.ui.HomePage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R


class homePageFragment : Fragment(), RecyclerAdapterActivity.OnItemClickListener {

    private lateinit var homePageModel: HomePageModel
    private var difficultySelected:String =""
    private lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homePageModel =
            ViewModelProvider(this).get(HomePageModel::class.java)

        root = inflater.inflate(R.layout.fragment_home_page, container, false)

        Log.d("difficulty",difficultySelected)
        //Create an array with the possible difficulties
        val possibleDifficulties = resources.getStringArray(R.array.difficulty_array)
        // Create a list of all the difficulties to be displayed
        val difficultyList = ArrayList<DifficultyLevelItem>()
        for (diff in possibleDifficulties) {
            val item = DifficultyLevelItem(diff)
            difficultyList += item
        }
        // Setup for the recycler view
        val rv = root.findViewById<RecyclerView>(R.id.recycler_view_difficulties)
        rv.adapter = RecyclerAdapter(difficultyList, this)
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rv.setHasFixedSize(true)


        //Create an array with the possible activities
        val possibleActivities = resources.getStringArray(R.array.activity_array)
        // Create a list of all the difficulties to be displayed
        val activityList = ArrayList<ActivityItem>()
        for (act in possibleActivities) {
            val icon = this.resources.getIdentifier(
                act.toLowerCase(),
                "drawable",
                activity?.baseContext?.packageName
            )
            val item = ActivityItem(act, icon)
            activityList += item
        }
        // Setup for the recycler view
        val rva = root.findViewById<RecyclerView>(R.id.recycler_view_activities)
        rva.adapter = RecyclerAdapterActivity(activityList, this)
        rva.layoutManager = LinearLayoutManager(activity)
        rva.setHasFixedSize(true)

        return root
    }

    override fun onItemClick(position: Int) {

        if(difficultySelected.isNotEmpty() && difficultySelected.isNotBlank()){
            val action = when (position) {
                0 -> homePageFragmentDirections.actionNavHomeToNavListening(difficultySelected)

                1 -> homePageFragmentDirections.actionNavHomeToNavVocabulary(difficultySelected)

                2 -> homePageFragmentDirections.actionNavHomeToNavWriting(difficultySelected)

                3 -> homePageFragmentDirections.actionNavHomeToNavSpeaking(difficultySelected)

                else -> homePageFragmentDirections.actionNavHomeToNavReading(difficultySelected)
            }
            view?.findNavController()?.navigate(action)
        }
        else{
            Toast.makeText(activity, "Select a difficulty level", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClicked(difficulty: String){
        //TODO: When a difficulty is chosen, load the right exercises or create list with right exercises from json file
        difficultySelected = difficulty
    }

    override fun onStop() {
        super.onStop()
        difficultySelected = ""
    }

}
