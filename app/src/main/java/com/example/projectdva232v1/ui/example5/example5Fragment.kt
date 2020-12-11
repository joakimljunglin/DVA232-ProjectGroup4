package com.example.projectdva232v1.ui.example5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.R

class example5Fragment : Fragment() {

  private lateinit var example5Model: Example5Model

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    example5Model =
            ViewModelProvider(this).get(Example5Model::class.java)
    val root = inflater.inflate(R.layout.fragment_example5, container, false)
    val textView: TextView = root.findViewById(R.id.text_5)
    example5Model.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}