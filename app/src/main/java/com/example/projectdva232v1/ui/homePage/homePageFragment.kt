package com.example.projectdva232v1.ui.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.MainActivity
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.example1.example1Fragment
import com.example.projectdva232v1.ui.example2.example2Fragment
import com.example.projectdva232v1.ui.example3.example3Fragment
import com.example.projectdva232v1.ui.example4.example4Fragment
import com.example.projectdva232v1.ui.example5.example5Fragment
import java.lang.reflect.Array.newInstance


class homePageFragment : Fragment(), RecyclerAdapterActivity.OnItemClickListener {

    private lateinit var homePageModel: HomePageModel

    //val possibleActivities = resources.getStringArray(R.array.activity_array)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homePageModel =
                ViewModelProvider(this).get(HomePageModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home_page, container, false)

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
        val activityIcon = resources.getStringArray(R.array.activities_images)
        // Create a list of all the difficulties to be displayed
        val activityList = ArrayList<ActivityItem>()
        for (act in possibleActivities) {
            var icon = this.resources.getIdentifier(
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

        var fragment: Fragment
        //TODO: replace with the right activity fragment
        when (position) {
            0 -> fragment = example1Fragment()

            1 -> fragment = example2Fragment()

            2 -> fragment = example3Fragment()

            3 -> fragment = example4Fragment()

            else -> fragment = example5Fragment()
        }

        //Opens new fragment
        val manager = activity?.supportFragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    override fun onItemClicked(position:Int){
    }
}
