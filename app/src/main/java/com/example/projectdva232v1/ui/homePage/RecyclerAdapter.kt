package com.example.projectdva232v1.ui.homePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R

var lastClickedPosition = -1
class RecyclerAdapter(
    private val difficultyList: List<DifficultyLevelItem>,
    private val listener: RecyclerAdapterActivity.OnItemClickListener
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    // Called for every ViewHolder item that is to be created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Build view from XML file
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_difficultyitem,
            parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return difficultyList.size
    }

    // Called each time a new ViewHolder is to be displayed
    // Takes ViewHolder and fills it with the new data to be displayed
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = difficultyList[position]
        holder.textDifficulty.text = currentItem.difficultyName

        holder.diffLayout.setOnClickListener {
            lastClickedPosition = position
            notifyDataSetChanged()
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClicked(holder.textDifficulty.text.toString())
            }
        }
        if (lastClickedPosition == position) {
            holder.textDifficulty.setBackgroundResource(R.color.pink)
        }
    }

    // Class with the data that is displayed for each Difficulty Item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textDifficulty: TextView = itemView.findViewById(R.id.difficulty_tv)
        val diffLayout: RelativeLayout = itemView.findViewById(R.id.diff_layout)
    }
}