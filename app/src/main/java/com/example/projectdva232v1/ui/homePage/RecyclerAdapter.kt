package com.example.projectdva232v1.ui.homePage

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.example1.example1Fragment

class RecyclerAdapter(
        private val difficultyList: List<DifficultyLevelItem>,
        private val listener: RecyclerAdapterActivity.OnItemClickListener
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    lateinit var lastText:TextView
    var c:Int= 0
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
    }

    // Class with the data that is displayed for each CurrencyItem
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        val textDifficulty: TextView = itemView.findViewById(R.id.difficulty_tv)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if(c > 0){
                lastText.setBackgroundResource(R.color.blue_diff)
            }
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                textDifficulty.setBackgroundResource(R.color.pink)
                listener.onItemClicked(position)
            }

            lastText = textDifficulty
            c+=1
        }
    }
}