package com.example.newsday.home.db

data class RecommendBean(
    val msg: String,
    val result: RecommendResult,
    val status: Int
)

data class RecommendResult(
    val channel: String,
    val list: List<ListItem>,
    val num: Int
)

data class ListItem(
    val category: String,
    val content: String,
    val pic: String,
    val src: String,
    val time: String,
    val title: String,
    val url: String,
    val weburl: String
)
