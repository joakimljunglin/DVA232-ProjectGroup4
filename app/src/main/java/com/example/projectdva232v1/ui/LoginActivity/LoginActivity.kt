package com.example.projectdva232v1.ui.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.example.projectdva232v1.MainActivity
import com.example.projectdva232v1.R
import com.example.projectdva232v1.ui.RegActivity.RegistrationActivity
import com.example.projectdva232v1.ui.homePage.homePageFragment
import com.example.projectdva232v1.ui.homePage.homePageFragmentDirections


class LoginActivity : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View?{


        val root = inflater.inflate(R.layout.activity_login,container,false)

        var et_user_name = root.findViewById(R.id.editTextTextEmailAddress) as EditText
        var et_password = root.findViewById(R.id.editTextTextPassword) as EditText
        var btn_login = root.findViewById(R.id.button) as Button
        var btn_signup = root.findViewById(R.id.button2) as Button


        btn_signup.setOnClickListener {
            // taking them into the registration page
            val intent = Intent(this.activity,RegistrationActivity::class.java)
            startActivity(intent)
        }
        // set on-click listener
        btn_login.setOnClickListener {
            //val intent = Intent(this.activity,homePageFragment::class.java)
            //this.activity?.startActivity(intent)

            var action = LoginActivityDirections.actionNavLoginActivityToNavHome()
            view?.findNavController()?.navigate(action)
            val manager: FragmentManager? = parentFragmentManager
            val transaction: FragmentTransaction? = manager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, homePageFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()



        }
     return root
    }
} //  val user_name = et_user_name.text;
// val password = et_password.text;
//  Toast.makeText(this@LoginActivity, user_name, Toast.LENGTH_LONG).show()

// your code to validate the user_name and password combination
// and verify the same
// make them actually login