package com.example.myapp.Dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nasaapp.Response

@Database(entities = [Response::class],version = 1)
abstract class AstronomyDb :RoomDatabase() {

  abstract fun getResturantDao():AstronomyDao
}