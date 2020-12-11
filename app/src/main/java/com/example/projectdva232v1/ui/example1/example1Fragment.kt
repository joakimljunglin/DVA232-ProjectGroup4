package com.example.projectdva232v1.ui.example1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.R

class example1Fragment : Fragment() {

  private lateinit var example1Model: Example1Model

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    example1Model =
            ViewModelProvider(this).get(Example1Model::class.java)
    val root = inflater.inflate(R.layout.fragment_example1, container, false)
    val textView: TextView = root.findViewById(R.id.text_1)
    example1Model.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}