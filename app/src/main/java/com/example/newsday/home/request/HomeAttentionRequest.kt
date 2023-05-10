package com.example.newsday.home.request

import com.example.newsday.home.db.AttentionBean
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HomeAttentionRequest {
    @POST("zaobao")
    @FormUrlEncoded
    fun getCall(@Field("token")token:String, @Field("format")format:String="json"): Call<AttentionBean>
}