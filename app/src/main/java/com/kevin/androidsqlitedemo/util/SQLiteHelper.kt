package com.kevin.androidsqlitedemo.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.kevin.androidsqlitedemo.pojo.Person
import com.kevin.androidsqlitedemo.util.Constants.DATABASE_NAME
import com.kevin.androidsqlitedemo.util.Constants.DATABASE_VERSION
import com.kevin.androidsqlitedemo.util.Constants.TABLE_NAME

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private const val TAG = "SQLiteHelper"
    }


    override fun onCreate(db: SQLiteDatabase?) {

        Log.e(TAG, "onCreate: ")
        // Create table
        val createTable = """
            CREATE TABLE ${Constants.TABLE_NAME} (
            ${Constants.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Constants.COLUMN_NAME} TEXT,
            ${Constants.COLUMN_AGE} INTEGER,
            ${Constants.COLUMN_PROFESSION} TEXT
            )
            """.trimIndent()

        // Execute the SQL statement
        db?.execSQL(createTable)

        Log.e(TAG, "onCreate: Table created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        // Drop the table if it exists
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert data into the database
    fun insertPerson(person: Person): Long {
        // Get the database in write mode
        val db = this.writableDatabase

        // Create a map of values
        val values = ContentValues().apply {
            put(Constants.COLUMN_NAME, person.name)
            put(Constants.COLUMN_AGE, person.age)
            put(Constants.COLUMN_PROFESSION, person.profession)
        }

        // Insert the row
        val id = db.insert(TABLE_NAME, null, values)

        // Close the database connection
        db.close()

        // Return the id of the inserted row
        return id
    }

    // delete data from the database
    fun deletePerson(id: Int): Boolean {
        // Get the database in write mode
        val db = this.writableDatabase

        // Delete the row
        val rows = db.delete(TABLE_NAME, "${Constants.COLUMN_ID} = ?", arrayOf(id.toString()))

        // Close the database connection
        db.close()

        return rows > 0

    }


    // Read data from the database
    @SuppressLint("Range")
    fun readAll(): List<Person> {
        // Get the database in read mode
        val db = this.readableDatabase

        // Create a list to store the data
        val personList = mutableListOf<Person>()

        // Query the database
        val query = "SELECT * FROM $TABLE_NAME"

        // Get the cursor object from the database query
        // store it in a variable if it is null return the empty list
        val cursor = db?.rawQuery(query, null)
            ?: // Return the empty list
            return personList

        // Loop through the cursor object and store the data in the list
        while (cursor.moveToNext()) {
            val person = Person(
                cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_AGE)),
                cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PROFESSION))
            )

            // Add the person object to the list
            personList.add(person)
        }
        return personList
    }


    @SuppressLint("Range")
    fun readOne(id: Int): Person? {
        // Get the database in read mode
        val db = this.readableDatabase


        // Query the database
        val query = "SELECT * FROM $TABLE_NAME WHERE ${Constants.COLUMN_ID} = ?"

        // Get the cursor object from the database query
        // store it in a variable if it is null return null
        val cursor = db?.rawQuery(query, arrayOf(id.toString()))
            ?: // Return null
            return null

        // Loop through the cursor object and return the data
        while (cursor.moveToNext()) {
            return Person(
                cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_AGE)),
                cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PROFESSION))
            )
        }
        return null
    }

}