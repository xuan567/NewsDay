package com.example.newsday.persistence.dateBaseHelper

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsday.persistence.CommendDetailDatabase
import com.example.newsday.persistence.bean.CommendDetailDate
import com.example.newsday.persistence.dao.CommendDetailDao

class CommendDetailDateBaseHelper private constructor(context: Context){

    private val detailDao: CommendDetailDao
    init {
        val db = Room.databaseBuilder(
            context,
            CommendDetailDatabase::class.java, "detail"
        ).fallbackToDestructiveMigration().build()
        detailDao = db.detailDao()
    }

    suspend fun isLikedByTitle(title: String): CommendDetailDate {
        return detailDao.findByTitle(title)
    }

    suspend fun getAll(): List<CommendDetailDate> {
        return detailDao.getAll()
    }

    suspend fun insertLike(date: CommendDetailDate) {
        detailDao.insert(date)
    }

    suspend fun deleteLike(title: String) {
        detailDao.deleteByTitle(title)
    }

    companion object {
        private var instance: CommendDetailDateBaseHelper? = null
        fun instance(context: Context): CommendDetailDateBaseHelper {
            if(instance != null) {
                return instance!!
            }
            instance = CommendDetailDateBaseHelper(context)
            return instance!!
        }
    }
}