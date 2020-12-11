package com.example.projectdva232v1.ui.example4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.R

class example4Fragment : Fragment() {

  private lateinit var example4Model: Example4Model

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    example4Model =
            ViewModelProvider(this).get(Example4Model::class.java)
    val root = inflater.inflate(R.layout.fragment_example4, container, false)
    val textView: TextView = root.findViewById(R.id.text_4)
    example4Model.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}