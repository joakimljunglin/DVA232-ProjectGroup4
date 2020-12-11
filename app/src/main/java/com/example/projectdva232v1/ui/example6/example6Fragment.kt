package com.example.projectdva232v1.ui.example6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.R

class example6Fragment : Fragment() {

  private lateinit var example6Model: Example6Model

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    example6Model =
            ViewModelProvider(this).get(Example6Model::class.java)
    val root = inflater.inflate(R.layout.fragment_example6, container, false)
    val textView: TextView = root.findViewById(R.id.text_6)
    example6Model.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}