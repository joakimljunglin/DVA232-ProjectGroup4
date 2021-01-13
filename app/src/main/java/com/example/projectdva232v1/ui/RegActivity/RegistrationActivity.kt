package com.example.projectdva232v1.ui.RegActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.LoginActivity.LoginActivity
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.view.*


class RegistrationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        lateinit var button: Button

        button = findViewById(R.id.button1)

        val spinner: Spinner = findViewById(R.id.roleSpinner)

        ArrayAdapter.createFromResource(
                this,
                R.array.role_spinner,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        //lateinit var spinner: Spinner
        //spinner = findViewById(R.id.roleSpinner)

        var etUserName = findViewById<View>(R.id.first_name) as EditText
        var strUserName = etUserName.text.toString()
        var etEmail = findViewById<View>(R.id.email_adress) as EditText
        var strEmail = etUserName.text.toString()
        var etlastname = findViewById<View>(R.id.last_name) as EditText
        var strlastname = etlastname.text.toString()
        var etPassword = findViewById<View>(R.id.password) as EditText
        var strPassword = etPassword.text.toString()

        etUserName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if (TextUtils.isEmpty(etUserName.text.toString())) {
                    etUserName.error = "Required field"
                    strUserName=""
                }else{
                    strUserName = etUserName.text.toString()}
            }

        })

        etEmail.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if (TextUtils.isEmpty(etEmail.text.toString())) {
                    etEmail.error = "Required field"
                    strEmail=""
                }
                else{
                    strEmail = etEmail.text.toString()
                }
            }

        })

        etPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if (TextUtils.isEmpty(etPassword.text.toString())) {
                    etPassword.error = "Required field"
                    strPassword = ""
                }else{
                    strPassword = etPassword.text.toString()}
                }
            }

        )
        etlastname.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if (TextUtils.isEmpty(etlastname.text.toString())) {
                    etlastname.error = "Required field"
                    strlastname = ""
                }else{
                strlastname = etlastname.text.toString()}
            }
        })


        button1.setOnClickListener {
            if (!TextUtils.isEmpty(strUserName) && !TextUtils.isEmpty(strUserName) && !TextUtils.isEmpty(strlastname) && !TextUtils.isEmpty(strEmail)){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                if (TextUtils.isEmpty(strUserName)){
                    etUserName.error = "Required field"
                }
                if (TextUtils.isEmpty(strlastname)){
                    etlastname.error = "Required field"
                }
                if (TextUtils.isEmpty(strEmail)){
                    etEmail.error = "Required field"
                }
                if (TextUtils.isEmpty(strPassword)){
                    etPassword.error = "Required field"
                }
            }
        }
 }
}