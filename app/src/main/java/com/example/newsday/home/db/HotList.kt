package com.example.newsday.home.db

import androidx.room.Entity
import androidx.room.PrimaryKey

data class HotListBean(
    val code: Int,
    val `data`: HotData,
    val log_id: Long,
    val msg: String,
    val time: Int
)

data class HotData(
    val last_update: String,
    val list: List<HotItem>,
    val name: String
)


@Entity(tableName = "hot")
data class HotItem(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val link: String,
    val other: String,
    val title: String
)
