package com.example.projectdva232v1.ui.homePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R


class RecyclerAdapterActivity(
        private val activityList: List<ActivityItem>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerAdapterActivity.ViewHolder>() {

    // Called for every ViewHolder item that is to be created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Build view from XML file
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_activityitem,
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
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textActivity.text = currentItem.activityName

        //sets the background color of the current item
        when(position){
            0 -> holder.imageView.setBackgroundResource(R.color.listening)
            1 -> holder.imageView.setBackgroundResource(R.color.vocabulary)
            2 -> holder.imageView.setBackgroundResource(R.color.writing)
            3 -> holder.imageView.setBackgroundResource(R.color.speaking)
            4 -> holder.imageView.setBackgroundResource(R.color.reading)
        }

    }

    // Class with the data that is displayed for each ActivityItem
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.activity_icon)
        val textActivity: TextView = itemView.findViewById(R.id.activity_tv)

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
        fun onItemClicked(position:Int)
    }
}