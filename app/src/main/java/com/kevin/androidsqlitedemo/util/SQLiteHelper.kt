package com.kevin.androidsqlitedemo.util

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


}