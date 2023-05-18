package com.example.newsday.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsday.persistence.bean.CommendDetailDate

@Dao
interface CommendDetailDao {

    @Insert
    fun insert(vararg detailDate: CommendDetailDate)

    @Delete
    fun delete(detailDate: CommendDetailDate)


    @Query("SELECT * FROM detail")
    fun getAll(): List<CommendDetailDate>


    @Query("SELECT * FROM detail WHERE id = :key")
    fun findByKey(key: String): CommendDetailDate

}