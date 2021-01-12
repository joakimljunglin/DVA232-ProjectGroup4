package com.example.projectdva232v1.ui.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.example.projectdva232v1.MainActivity
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.HomePage.HomePageClass
import com.example.projectdva232v1.ui.RegActivity.RegistrationActivity
//import com.example.projectdva232v1.ui.HomePage.homePageFragment
//import com.example.projectdva232v1.ui.HomePage.homePageFragmentDirections


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var et_user_name = findViewById(R.id.editTextTextEmailAddress) as EditText
        var strUserName = et_user_name.text.toString()
        var et_password = findViewById(R.id.editTextTextPassword) as EditText
        var strPassword=et_password.text.toString()
        var btn_login = findViewById(R.id.button) as Button
        var btn_signup = findViewById(R.id.button2) as Button

        et_user_name.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if (TextUtils.isEmpty(et_user_name.text.toString())) {
                    et_user_name.error = "Required field"
                    strUserName=""
                }else{
                    strUserName = et_user_name.text.toString()}
            }

        })
        et_password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if (TextUtils.isEmpty(et_password.text.toString())) {
                    et_password.error = "Required field"
                    strPassword = ""
                } else {
                    strPassword = et_password.text.toString()
                }
            }
        })


        btn_signup.setOnClickListener {
            // taking them into the registration page
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }


        btn_login.setOnClickListener {
            if (!TextUtils.isEmpty(strUserName) && !TextUtils.isEmpty(strPassword)){

                val intent = Intent(this,HomePageClass::class.java)
                this.startActivity(intent)
            }else{
                if (TextUtils.isEmpty(strUserName)) {
                    et_user_name.error = "Required field"
                }
                if (TextUtils.isEmpty(strPassword)) {
                    et_password.error = "Required field"
                }

            }

          /*  var action = LoginActivityDirections.actionNavLoginActivityToNavHome()
            view?.findNavController()?.navigate(action)
            val manager: FragmentManager? = parentFragmentManager
            val transaction: FragmentTransaction? = manager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, homePageFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
*/
        }
    }
} //  val user_name = et_user_name.text;
// val password = et_password.text;
//  Toast.makeText(this@LoginActivity, user_name, Toast.LENGTH_LONG).show()

// your code to validate the user_name and password combination
// and verify the same
// make them actually login