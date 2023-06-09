package com.example.newsday.persistence.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "detail")
data class CommendDetailDate(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                             @ColumnInfo(name = "title") val title: String?,
                             @ColumnInfo(name = "pic") val pic: String?,
                             @ColumnInfo(name = "content") val content: String?,
                             @ColumnInfo(name = "src") val src: String?,
                             @ColumnInfo(name = "time") val time: String?,
)