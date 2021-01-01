package com.example.projectdva232v1.ui.ReadingActivity

import android.content.Intent
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
import com.example.projectdva232v1.ui.example5.example5Fragment
import com.example.projectdva232v1.ui.homePage.DifficultyLevelItem
import com.example.projectdva232v1.ui.learning_activities.ReadingActivity


class ReadingFragment : Fragment(), RecyclerAdapterReading.OnItemClickListener {

    private lateinit var readingModel: ReadingModel
    var activityList = ArrayList<DifficultyLevelItem>()
    lateinit var difficultySelected: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        difficultySelected = ReadingFragmentArgs.fromBundle(requireArguments()).ReadingFragmentArgs

        readingModel =
            ViewModelProvider(this).get(ReadingModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading_activity, container, false)

        //TODO: get a list of all reading activities proposed from API and create a new list with
        // only the ones with the right difficulty level
        /*var rightReadingActivities = ArrayList<ReadingActivity>()
        for(a in allReadingActivities){
            if(a.difficulty == difficultySelected){
                rightReadingActivities.add(a)
            }
        }*/

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
        rv.adapter = RecyclerAdapterReading(activityList, this)
        //rv.adapter = RecyclerAdapterReading(rightReadingActivities,this)
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv.setHasFixedSize(true)
        return root
    }

    override fun onItemClick(position: Int) {

        var fragment: Fragment = ReadingFragment()
        //TODO: replace with the right reading activity fragment
        /*for (index in 0..rightReadingActivities.size) {
            if (position == index) {
                fragment = readingActivity()
            }
        }*/

        for (index in 0..activityList.size) {
            if (position == index) {
                fragment = example5Fragment()
            }
        }

        // view?.findNavController()?.navigate(R.id.action_nav_reading_to_nav_example5)
        //Opens new fragment
        /*val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()*/

        val intent = Intent(this.activity, ReadingActivity::class.java)
        startActivity(intent)
    }
}