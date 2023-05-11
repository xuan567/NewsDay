package com.example.newsday.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRetrofit {

    private fun attentionRequest(baseUrl: String): ApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun attentionRequest(): ApiService {
        val baseUrl = "https://v2.alapi.cn/api/"
        return attentionRequest(baseUrl)
    }

    fun recommendRequest(): ApiService {
        val baseUrl = "https://api.jisuapi.com/"
        return attentionRequest(baseUrl)
    }
}