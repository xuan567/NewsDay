package com.example.newsday.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsday.persistence.bean.CommendDetailDate

@Dao
interface CommendDetailDao {

    @Insert
    suspend fun insert(vararg detailDate: CommendDetailDate)

    @Delete
    suspend fun delete(detailDate: CommendDetailDate)

    @Query("DELETE FROM detail WHERE title = :title")
    suspend fun deleteByTitle(title: String)

    @Query("SELECT * FROM detail")
    suspend fun getAll(): List<CommendDetailDate>


    @Query("SELECT * FROM detail WHERE title = :title")
    suspend fun findByTitle(title: String): CommendDetailDate

}