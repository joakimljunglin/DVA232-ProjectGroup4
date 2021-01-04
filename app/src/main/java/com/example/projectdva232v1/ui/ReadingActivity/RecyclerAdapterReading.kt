package com.example.projectdva232v1.ui.ReadingActivity

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.homePage.DifficultyLevelItem


class RecyclerAdapterReading(
        private val activityList: List<DifficultyLevelItem>,
        private val listener: OnItemClickListener,
        var score: Int = 0

) : RecyclerView.Adapter<RecyclerAdapterReading.ViewHolder>() {

    // Called for every ViewHolder item that is to be created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Build view from XML file
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_readingitem,
                parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    // Called each time a new ViewHolder is to be displayed
    // Takes ViewHolder and fills it with the new data to be displayed
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = activityList[position]
        //TODO: get best score and difficulty from previous attempts & page
        val s = "Test ${position+1}\nBest score: $score\nLevel: ${currentItem.difficultyName}"
        val span = SpannableString(s)
        span.setSpan(RelativeSizeSpan(1.5f),0,span.indexOf("\n"),0)
        span.setSpan(ForegroundColorSpan(Color.GRAY),span.indexOf("\n"),span.length,0)
        holder.textActivity.text = span
        holder.textImageView.text = (position + 1).toString()
    }

    // Class with the data that is displayed for each ActivityItem
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        val textImageView: TextView = itemView.findViewById(R.id.reading_circle_tv)
        val textActivity: TextView = itemView.findViewById(R.id.reading_tv)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}