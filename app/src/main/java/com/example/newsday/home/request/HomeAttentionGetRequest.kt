package com.example.newsday.home.request

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.newsday.home.db.AttentionBean
import com.example.newsday.home.db.AttentionData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeAttentionGetRequest {
    fun attentionRequest(requestLiveData: MutableLiveData<AttentionData>){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://v2.alapi.cn/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val request: HomeAttentionRequest = retrofit.create(HomeAttentionRequest::class.java)

        request.getCall("5tMzmrhXv4ogqTeA").enqueue(object : retrofit2.Callback<AttentionBean?>{
            override fun onResponse(
                call: Call<AttentionBean?>,
                response: Response<AttentionBean?>
            ) {
                requestLiveData.value = response.body()?.data
                Log.d("attention","连接成功")
            }

            override fun onFailure(call: Call<AttentionBean?>, t: Throwable) {
                Log.d("attention","连接失败  $t")
            }
        })
    }
}