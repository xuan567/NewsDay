package com.example.newsday.network

import com.example.newsday.home.db.AttentionBean
import com.example.newsday.home.db.RecommendBean
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    /**
     * 关注
     * */
    @POST("zaobao")
    @FormUrlEncoded
    suspend fun getCall(@Field("token")token:String, @Field("format")format:String="json"): AttentionBean

    /**
     * 推荐
     * */
    @POST("news/get")
    @FormUrlEncoded
    suspend  fun getCall(
        @Field("appkey") appkey: String = "07952e732c1fc364",
        @Field("channel") channel: String = "新闻",
        @Field("num") num: Int = 40
    ): RecommendBean
}