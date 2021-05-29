package com.example.myapplication.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityResultBinding
import com.example.myapplication.models.Quiz
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    private lateinit var quiz: Quiz
    private lateinit var binding : ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_result)

        setUpViews()

    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")

        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)   //Deserial with the help of gson
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for(entry in quiz.questions.entries){
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color'#009688'>Answer: ${question.answer}</font><br/><br/>")
        }

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.N){
            binding.txtAnswer.text = Html.fromHtml(builder.toString(),Html.FROM_HTML_MODE_COMPACT);
        }
        else{
            binding.txtAnswer.text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        var score = 0
        for(entry in quiz.questions.entries){
            val question = entry.value
            if(question.answer == question.userAnswer){
                score+=10
            }
        }
        Log.d("Score","$score")
        binding.txtScore.text = "Your Score: $score"
    }
}