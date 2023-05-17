package com.example.newsday.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiRetrofit {

    private fun attentionRequest(baseUrl: String): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    /**
     * 关注
     * */
    fun attentionRequest(): ApiService {
        val baseUrl = "https://v2.alapi.cn/api/"
        return attentionRequest(baseUrl)
    }


    /**
     * 推荐
     * */
    fun recommendRequest(): ApiService {
        val baseUrl = "https://api.jisuapi.com/"
        return attentionRequest(baseUrl)
    }


    /**
     * 热榜
     * */
    fun hotListRequest(): ApiService {
        val baseurl = "https://v2.alapi.cn/api/"
        return attentionRequest(baseurl)
    }


    /**
     * 垃圾分类
     * */
    const val GARBAGE_API_KEY = "ef3f0c87a61c043a23642a443f02c541"
    private const val GARBAGE_API_BASEURL = "https://apis.tianapi.com/"

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    fun getGarbageRequest(): ApiService {
        return Retrofit.Builder()
            .baseUrl(GARBAGE_API_BASEURL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}