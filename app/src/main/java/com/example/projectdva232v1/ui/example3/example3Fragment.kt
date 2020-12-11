package com.example.projectdva232v1.ui.example3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.R

class example3Fragment : Fragment() {

  private lateinit var example3Model: Example3Model

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    example3Model =
            ViewModelProvider(this).get(Example3Model::class.java)
    val root = inflater.inflate(R.layout.fragment_example3, container, false)
    val textView: TextView = root.findViewById(R.id.text_3)
    example3Model.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}