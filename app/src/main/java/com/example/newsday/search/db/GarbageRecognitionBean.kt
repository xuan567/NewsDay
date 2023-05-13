package com.example.newsday.search.db

import com.google.gson.annotations.SerializedName


data class GarbageRecognitionBean(
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("list") val list: List<TextItem>
    )
    data class TextItem(
        @SerializedName("aipre") val aipre: Int?,
        @SerializedName("contain") val contain: String?,
        @SerializedName("explain") val explain: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("tip") val tip: String?,
        @SerializedName("type") val type: Int?,
        @SerializedName("lajitip") val lajitip: String?,
        @SerializedName("lajitype") val lajitype: Int?,
        @SerializedName("trust") val trust: Double?
    )
}


