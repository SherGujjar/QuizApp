package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.QuizAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupviews()

    }

    private fun populateDummyData() {
        quizList.add(Quiz("13-10-2021","13-10-2021"))
        quizList.add(Quiz("14-10-2021","14-10-2021"))
        quizList.add(Quiz("15-10-2021","15-10-2021"))
        quizList.add(Quiz("16-10-2021","16-10-2021"))
        quizList.add(Quiz("17-10-2021","17-10-2021"))
        quizList.add(Quiz("18-10-2021","18-10-2021"))
        quizList.add(Quiz("19-10-2021","19-10-2021"))
        quizList.add(Quiz("20-10-2021","20-10-2021"))
        quizList.add(Quiz("21-10-2021","21-10-2021"))
        quizList.add(Quiz("22-10-2021","22-10-2021"))
        quizList.add(Quiz("23-10-2021","23-10-2021"))
        quizList.add(Quiz("24-10-2021","24-10-2021"))


    }

    fun setupviews(){
        setupDrawerLayout()
        setUpRecyclerView()
        setupFireStore()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        binding.btnDatePicker.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER",datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener{
                Log.d("DATEPICKER",datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER","DATE PICKER WAS CANCELLED")
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this,quizList)
        binding.quizRecyclerView.layoutManager = GridLayoutManager(this,2)
        binding.quizRecyclerView.adapter = adapter
    }

    fun setupDrawerLayout(){
        setSupportActionBar(binding.appBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.mainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            binding.mainDrawer.closeDrawers()
            true
        }
    }

    private fun setupFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection ("Quizzes")
        // Below line will take snapshot from firestone and whenever we change data in firestone it will reflact
        // at that exact moment
        collectionReference.addSnapshotListener{ value,error->
            if(value==null || error !=null){
                Toast.makeText(this,"Error fetching data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()   //telling to refresh data


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}