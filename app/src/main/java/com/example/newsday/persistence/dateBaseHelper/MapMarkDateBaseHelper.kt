package com.example.newsday.persistence.dateBaseHelper

import android.content.Context
import androidx.room.Room
import com.example.newsday.home.db.MapMarkDate
import com.example.newsday.persistence.MapMarkDatabase
import com.example.newsday.persistence.dao.MapMarkDao

class MapMarkDateBaseHelper private constructor(context: Context){

    private val mapMarkDao: MapMarkDao
    init {
        val db = Room.databaseBuilder(
            context,
            MapMarkDatabase::class.java, "mapMark"
        ).fallbackToDestructiveMigration().build()
        mapMarkDao = db.mapMarkDao()
    }

    suspend fun queryByTitle(title: String): MapMarkDate {
        return mapMarkDao.queryByTitle(title)
    }

    suspend fun queryAll(): List<MapMarkDate> {
        return mapMarkDao.queryAll()
    }

    suspend fun insertMark(date: MapMarkDate) {
        mapMarkDao.insert(date)
    }

    suspend fun insertMarkList(markList: List<MapMarkDate>) {
        mapMarkDao.insertList(markList)
    }

    suspend fun deleteByTitle(title: String) {
        mapMarkDao.deleteByTitle(title)
    }

    companion object {
        private var instance: MapMarkDateBaseHelper? = null
        fun instance(context: Context): MapMarkDateBaseHelper {
            if(instance != null) {
                return instance!!
            }
            instance = MapMarkDateBaseHelper(context)
            return instance!!
        }
    }
}