package com.example.projectdva232v1.ui.example2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectdva232v1.R

class example2Fragment : Fragment() {

  private lateinit var example2Model: Example2Model

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    example2Model =
            ViewModelProvider(this).get(Example2Model::class.java)
    val root = inflater.inflate(R.layout.fragment_example2, container, false)
    val textView: TextView = root.findViewById(R.id.text_2)
    example2Model.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}