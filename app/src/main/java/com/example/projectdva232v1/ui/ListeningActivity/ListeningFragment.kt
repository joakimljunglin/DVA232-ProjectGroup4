package com.example.projectdva232v1.ui.ListeningActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.homePage.DifficultyLevelItem


class ListeningFragment : Fragment(), RecyclerAdapterListening.OnItemClickListener {

    private lateinit var listeningModel: ListeningModel
   //lateinit var levels: MutableList<DifficultyLevelItem>
    var activityList = ArrayList<DifficultyLevelItem>()
    lateinit var difficultySelected: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        difficultySelected = ListeningFragmentArgs.fromBundle(requireArguments()).ListeningFragmentArgs

        listeningModel =
            ViewModelProvider(this).get(ListeningModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading_activity, container, false)

        //Since we don't have access to the API
        val activities = resources.getStringArray(R.array.difficulty_array)
        for (diff in activities) {
            if (diff == difficultySelected) {
                val item = DifficultyLevelItem(diff)
                activityList.plusAssign(item)
            }
        }
        // Setup for the recycler view
        val rv = root.findViewById<RecyclerView>(R.id.recycler_view_activity_reading)
        rv.adapter = RecyclerAdapterListening(activityList, this)
        //rv.adapter = RecyclerAdapterReading(rightReadingActivities,this)
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv.setHasFixedSize(true)
        return root
    }

    override fun onItemClick(position: Int) {
        for (index in 0..activityList.size) {
            if (position == index) {
                view?.findNavController()?.navigate(R.id.action_nav_listening_to_nav_example5)
            }
        }
    }
}