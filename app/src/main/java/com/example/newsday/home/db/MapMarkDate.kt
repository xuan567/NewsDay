package com.example.newsday.home.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mapMark")
data class MapMarkDate(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                       @ColumnInfo(name = "title") val title: String?,
                       @ColumnInfo(name = "snippet") val snippet: String?,
                       @ColumnInfo(name = "lat") val lat: Double,
                       @ColumnInfo(name = "lng") val lng: Double)
