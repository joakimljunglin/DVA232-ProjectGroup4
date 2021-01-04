package com.example.projectdva232v1.ui.Vocabulary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.HomePage.DifficultyLevelItem
import com.example.projectdva232v1.ui.learning_activities.VocabularyActivity


class VocabularyFragment : Fragment(), RecyclerAdapterVocabulary.OnItemClickListener {

    private lateinit var vocabularyModel: VocabularyModel
    var activityList = ArrayList<DifficultyLevelItem>()
    lateinit var difficultySelected: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        difficultySelected = VocabularyFragmentArgs.fromBundle(requireArguments()).VocabularyFragmentArgs

        vocabularyModel =
            ViewModelProvider(this).get(VocabularyModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reading_activity, container, false)

        //Since we don't have access to the API
        val activities = resources.getStringArray(R.array.difficulty_array)
        activityList.clear()
        for (diff in activities) {
            if (diff == difficultySelected) {
                val item = DifficultyLevelItem(diff)
                activityList.plusAssign(item)
            }
        }
        // Setup for the recycler view
        val rv = root.findViewById<RecyclerView>(R.id.recycler_view_activity_reading)
        rv.adapter = RecyclerAdapterVocabulary(activityList, this)
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv.setHasFixedSize(true)
        return root
    }

    override fun onItemClick(position: Int) {

        for (index in 0..activityList.size) {
            if (position == index) {

                val intent = Intent(this.context, VocabularyActivity::class.java)
                startActivity(intent)
            }
        }
    }
}