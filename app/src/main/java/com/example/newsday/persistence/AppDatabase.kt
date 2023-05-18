package com.example.newsday.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsday.persistence.bean.CommendDetailDate
import com.example.newsday.persistence.dao.CommendDetailDao

@Database(entities = [CommendDetailDate::class], version = 3)
abstract class CommendDetailDatabase : RoomDatabase() {
    abstract fun detailDao(): CommendDetailDao
}