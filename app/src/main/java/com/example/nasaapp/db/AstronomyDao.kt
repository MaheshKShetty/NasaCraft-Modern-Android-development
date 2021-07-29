package com.example.nasaapp.db

import androidx.room.*
import androidx.room.Dao
import com.example.nasaapp.model.Response
import kotlinx.coroutines.flow.Flow

@Dao
interface AstronomyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(response: Response)

    @Delete
    suspend fun deleteAll(response: Response)

     @Query("SELECT * FROM Response")
     fun getAstronomy(): Flow<Response>
}