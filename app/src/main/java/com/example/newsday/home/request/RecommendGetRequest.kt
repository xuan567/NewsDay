package com.example.newsday.home.request

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.newsday.home.db.RecommendBean
import com.example.newsday.home.db.RecommendResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecommendGetRequest {

    fun recommendRequest(requestLiveData: MutableLiveData<RecommendResult>) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.jisuapi.com/")
            .build()
        val request: RecommendRequest = retrofit.create(RecommendRequest::class.java)
        request.getCall().enqueue(object : retrofit2.Callback<RecommendBean> {
            override fun onResponse(call: Call<RecommendBean>, response: Response<RecommendBean>) {
                response.body() ?: return
                if (response.body()?.result != null) {

                    requestLiveData.value = response.body()?.result
                    Log.d("Recommend", "Recommend连接成功")
                }
            }

            override fun onFailure(call: Call<RecommendBean>, t: Throwable) {
                Log.d("Recommend", "Recommend连接失败")
            }

        })
    }

}