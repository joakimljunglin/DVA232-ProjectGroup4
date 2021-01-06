package com.example.projectdva232v1.ui.RegActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.projectdva232v1.R
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var button: Button
        lateinit var editText: EditText
        lateinit var string: String
        lateinit var textView: TextView
        lateinit var spinner: Spinner

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.roleSpinner)
        button = findViewById(R.id.button1)
        editText = findViewById(R.id.first_name)
        editText = findViewById(R.id.email_adress)
        editText = findViewById(R.id.last_name)
        editText = findViewById(R.id.password)
        textView = findViewById(R.id.textView)


        button.setOnClickListener {
            string = editText.text.toString()
            textView.text = string
            // missing code for spinnner

        }
    }
}