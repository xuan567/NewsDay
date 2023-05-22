package com.example.newsday.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsday.home.db.HotItem
import com.example.newsday.home.db.MapMarkDate
import com.example.newsday.persistence.bean.CommendDetailDate
import com.example.newsday.persistence.dao.CommendDetailDao
import com.example.newsday.persistence.dao.HotDateDao
import com.example.newsday.persistence.dao.MapMarkDao

@Database(entities = [CommendDetailDate::class], version = 3)
abstract class CommendDetailDatabase : RoomDatabase() {
    abstract fun detailDao(): CommendDetailDao
}


@Database(entities = [MapMarkDate::class], version = 3)
abstract class MapMarkDatabase : RoomDatabase() {
    abstract fun mapMarkDao(): MapMarkDao
}

@Database(entities = [HotItem::class], version = 1)
abstract class HotDatabase : RoomDatabase() {
    abstract fun hotDao(): HotDateDao
}

