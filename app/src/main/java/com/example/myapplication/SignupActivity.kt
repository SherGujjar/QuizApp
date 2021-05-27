package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



import android.widget.Toast
import com.example.myapplication.databinding.ActivitySignupBinding

import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding


    lateinit var firebaseAuth: FirebaseAuth    //created fairbase object for authentication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setContentView(R.layout.activity_signup)
        firebaseAuth = FirebaseAuth.getInstance()   // Initialise firebaseAuth

        //adding button
        binding.stbutton.setOnClickListener{
            signupUser()
        }
    }

    private fun signupUser(){
        val email = binding.stEmailAddress.text.toString()
        val password = binding.stPassword.text.toString()
        val confirmPassword = binding.stConfirmPassword.text.toString()

        //some validations
        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this,"Email and Passord can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        if(password!=confirmPassword){
            Toast.makeText(this,"Password and confirm password do not match",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)   //this wll create a user on firebase
                //since it is a network call therefore we use a listner to tell us that our call is completed
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Login Sucessful",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"Error in creating user.",Toast.LENGTH_SHORT).show()
                }
            }
    }


    

}