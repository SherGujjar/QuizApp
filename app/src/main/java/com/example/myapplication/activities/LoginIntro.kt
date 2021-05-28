package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginIntroBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class LoginIntro : AppCompatActivity() {
    private lateinit var binding: ActivityLoginIntroBinding


    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_intro)
        val auth:FirebaseAuth = FirebaseAuth.getInstance()
        // to check if user is alreadu loged in we redirect him to Main Activity
        if(auth.currentUser!=null){
            Toast.makeText(this,"User is already logged in",Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        binding.btnGetStarted.setOnClickListener{
            redirect("LOGIN")
        }

    }

    private fun redirect(name:String){
        val intent:Intent = when(name){
            "LOGIN"-> Intent(this, LoginActivity::class.java)
            "MAIN"-> Intent(this, MainActivity::class.java)
            else ->throw Exception("no path exist")
        }
        startActivity(intent)
        finish()
    }


}