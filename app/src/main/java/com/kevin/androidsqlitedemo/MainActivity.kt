package com.kevin.androidsqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kevin.androidsqlitedemo.databinding.ActivityMainBinding
import com.kevin.androidsqlitedemo.pojo.Person
import com.kevin.androidsqlitedemo.sqlite.SQLiteHelper

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

        // Set the click listener on the insert button
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

        // Set the click listener on the delete button
        binding.btnDelete.setOnClickListener {
            // Delete data from the database

            // Delete the data
            // Change the id to the id of the row you want to delete
            val isDeleted = sqliteHelper.deletePerson(1)

            // Check if the data was deleted successfully
            if (isDeleted) {
                Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show()
            }
        }


        // Set the click listener on the read button
        binding.btnRead.setOnClickListener {
            // Read data from the database

            // Read the data
            val personList = sqliteHelper.readAll()

            // Check if the data was read successfully
            if (personList.isNotEmpty()) {
                Toast.makeText(
                    this,
                    "Data read successfully\n ${personList.joinToString()}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "No data in table", Toast.LENGTH_SHORT).show()
            }
        }

        // Set the click listener on the read single button
        binding.btnReadSingle.setOnClickListener {
            // Read a single row from the database

            // Read the data
            val person = sqliteHelper.readOne(2)

            // Check if the data was read successfully
            if (person != null) {
                Toast.makeText(this, "Data read successfully\n $person", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No data in table", Toast.LENGTH_SHORT).show()
            }
        }

        // Set the click listener on the update button
        binding.btnUpdateSingle.setOnClickListener {

            // Read a single row from the database

            // Read the data
            val person = sqliteHelper.readOne(2)

            // Check if the data was read successfully
            if (person != null) {

                // Update the data
                person.age += 5

                // Update the data in the database
                val isUpdated = sqliteHelper.update(person)

                // Check if the data was updated successfully
                if (isUpdated) {
                    Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
                }


            }

        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}