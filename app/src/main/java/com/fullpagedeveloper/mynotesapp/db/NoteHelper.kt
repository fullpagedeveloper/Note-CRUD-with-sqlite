package com.fullpagedeveloper.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.fullpagedeveloper.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.fullpagedeveloper.mynotesapp.db.DatabaseContract.NoteColumns.Companion._ID

class NoteHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper

        //method ini berfungsi untuk menginisiasi database
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }

        private lateinit var database: SQLiteDatabase
    }

    //buat konstructornya
    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    //open connection ke database
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    //close connection ke database
    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    // method untuk proses CRUD, method untuk mengambil data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    //method untuk mengambil data dengan ID tertentu
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    //method untuk menyimpan data
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    //method untuk memperbaruhi data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    //method untuk menghapus data
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}