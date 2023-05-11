package com.example.newsday.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsday.home.db.RecommendResult
import com.example.newsday.network.ApiRetrofit
import kotlinx.coroutines.launch
import java.lang.Exception

class RecommendViewModel : ViewModel() {
    private val _recommendLiveData = MutableLiveData<RecommendResult>()
    val recommendLiveData: LiveData<RecommendResult> = _recommendLiveData
    fun recommend() {
        viewModelScope.launch {
            try {
                val result = ApiRetrofit.recommendRequest().getCall()
                _recommendLiveData.value = result.result
                Log.d("Recommend",result.result.num.toString())
            }catch (e: Exception) {
                Log.d("Recommend","连接失败  $e")
            }
        }
    }
}