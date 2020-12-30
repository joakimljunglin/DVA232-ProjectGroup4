package com.example.projectdva232v1.ui.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomePageModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Page Fragment"
    }
    val text: LiveData<String> = _text

}