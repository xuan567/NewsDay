package com.example.newsday.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsday.home.db.HotItem

@Dao
interface HotDateDao {
    @Insert
    suspend fun insertAll(hotList: List<HotItem>)


    @Query("SELECT * FROM hot")
    suspend fun queryAll(): List<HotItem>

    @Query("DELETE FROM hot")
    suspend fun deleteAll()

}