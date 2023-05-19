package com.example.newsday.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsday.home.db.MapMarkDate

@Dao
interface MapMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg markDate: MapMarkDate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(markList: List<MapMarkDate>)

    @Delete
    suspend fun delete(markDate: MapMarkDate)

    @Query("DELETE FROM mapMark WHERE title = :title")
    suspend fun deleteByTitle(title: String)

    @Query("SELECT * FROM mapMark")
    suspend fun queryAll(): List<MapMarkDate>


    @Query("SELECT * FROM mapMark WHERE title = :title")
    suspend fun queryByTitle(title: String): MapMarkDate
}