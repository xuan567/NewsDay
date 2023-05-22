package com.example.newsday.persistence.dateBaseHelper

import android.content.Context
import androidx.room.Room
import com.example.newsday.home.db.HotItem
import com.example.newsday.persistence.HotDatabase
import com.example.newsday.persistence.dao.HotDateDao

class HotDateBaseHelper private constructor(context: Context) {

    private val hotDateDao: HotDateDao
    init {
        val db = Room.databaseBuilder(
            context,
            HotDatabase::class.java, "hot"
        ).fallbackToDestructiveMigration().build()
        hotDateDao = db.hotDao()
    }

    suspend fun queryAll(): List<HotItem> {
        return hotDateDao.queryAll()
    }

    suspend fun insertAll(hotList: List<HotItem>) {
        hotDateDao.insertAll(hotList)
    }


    suspend fun deleteAll() {
        hotDateDao.deleteAll()
    }

    companion object {
        private var instance: HotDateBaseHelper? = null
        fun instance(context: Context): HotDateBaseHelper {
            if(instance != null) {
                return instance!!
            }
            instance = HotDateBaseHelper(context)
            return instance!!
        }
    }
}