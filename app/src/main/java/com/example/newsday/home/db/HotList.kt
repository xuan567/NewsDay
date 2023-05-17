package com.example.newsday.home.db

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

data class HotItem(
    val link: String,
    val other: String,
    val title: String
)
