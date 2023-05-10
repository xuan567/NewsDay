package com.example.newsday.home.db

import androidx.databinding.ObservableField

data class AttentionBean(
    val code: Int,
    val `data`: AttentionData,
    val log_id: Long,
    val msg: String,
    val time: Int
)

data class AttentionData(
    val date: String,
    val head_image: String,
    val image: String,
    val news: List<String>,
    val weiyu: String
)