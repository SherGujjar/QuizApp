package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.OptionAdapter
import com.example.myapplication.databinding.ActivityQuestionBinding
import com.example.myapplication.models.Question
import com.example.myapplication.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {


    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question)
        setupFireStore()
        setUpEventListner()
    }

    private fun setUpEventListner() {
        binding.btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        binding.btnNext.setOnClickListener {
            index++
            bindViews()
        }
        binding.btnSubmit.setOnClickListener {
            Log.d("FINALQUIZ", questions.toString())

            val intent = Intent(this,ResultActivity::class.java)
            val json:String = Gson().toJson(quizzes!![0])   //serial objects with the help of gson
            intent.putExtra("QUIZ",json)
            startActivity(intent)

        }
    }


    private fun setupFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("Quizzes").whereEqualTo("title", date).get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }

                }
        }


    }

    private fun bindViews() {
        binding.btnPrevious.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE
        binding.btnNext.visibility = View.GONE

        if (index == 1) {
            binding.btnNext.visibility = View.VISIBLE
        } else if (index == questions!!.size) {
            binding.btnSubmit.visibility = View.VISIBLE
            binding.btnPrevious.visibility = View.VISIBLE
        } else {
            binding.btnPrevious.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]
        question?.let {
            binding.description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        }

    }
}