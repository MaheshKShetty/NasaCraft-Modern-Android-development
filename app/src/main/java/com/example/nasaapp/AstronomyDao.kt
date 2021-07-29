package com.example.myapp.Dao

import androidx.room.*
import androidx.room.Dao
import com.example.nasaapp.Response
import kotlinx.coroutines.flow.Flow

@Dao
interface AstronomyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(response: Response)

    @Delete
    suspend fun delete(response: Response)

     @Query("SELECT * FROM Response")
     fun getAstronomy(): Flow<Response>
}