package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding

import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        firebaseAuth = FirebaseAuth.getInstance()   // Initialise firebaseAuth

        //adding button
        binding.btnLogin.setOnClickListener{
            login()
        }

        binding.etSignUp.setOnClickListener{
            val intenet  = Intent(this, SignupActivity::class.java)
            startActivity(intenet)
            finish()
        }

    }

    private fun login(){
        val email = binding.etEmailAddress.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email/Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                val intenet  = Intent(this, MainActivity::class.java)
                startActivity(intenet)
                finish()
            }
            else{
                Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}