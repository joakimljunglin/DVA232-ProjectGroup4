package com.example.projectdva232v1.ui.LoginActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projectdva232v1.R
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.view.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var et_user_name = findViewById(R.id.editTextTextEmailAddress) as EditText
        var et_password = findViewById(R.id.editTextTextPassword) as EditText
        var btn_login = findViewById(R.id.button) as Button
        var btn_signup = findViewById(R.id.button2) as Button

        btn_signup.setOnClickListener {
            // taking them into the registration page
        }

        // set on-click listener
        btn_login.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;
            Toast.makeText(this@LoginActivity, user_name, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same
            // make them actually login

        }
    }
}