package com.kevin.androidsqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kevin.androidsqlitedemo.databinding.ActivityMainBinding
import com.kevin.androidsqlitedemo.pojo.Person
import com.kevin.androidsqlitedemo.util.SQLiteHelper

class MainActivity : AppCompatActivity() {

    // View binding
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // SQLiteHelper instance
    private val sqliteHelper: SQLiteHelper by lazy {
        SQLiteHelper(this)
    }

    // onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {

        binding.btnInsert.setOnClickListener {
            // Insert data into the database

            // Create a Person object with the data
            val id = sqliteHelper.insertPerson(Person(0, "Kevin", 25, "Developer"))

            // Check if the data was inserted successfully
            if (id > 0) {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}