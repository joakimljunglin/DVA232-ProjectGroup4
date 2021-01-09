package com.example.projectdva232v1.ui.Listening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListeningModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is reading Fragment"
    }
    val text: LiveData<String> = _text
}