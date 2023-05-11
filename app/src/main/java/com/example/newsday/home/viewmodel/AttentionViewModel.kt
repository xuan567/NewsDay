package com.example.newsday.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsday.home.db.AttentionBean
import com.example.newsday.network.ApiRetrofit
import kotlinx.coroutines.launch
import java.lang.Exception

class AttentionViewModel : ViewModel() {

    private val _attentionLiveData = MutableLiveData<AttentionBean>()
    val attentionLiveData: LiveData<AttentionBean> = _attentionLiveData


    fun attention(){
        viewModelScope.launch {
            try {
                val result = ApiRetrofit.attentionRequest().getCall("5tMzmrhXv4ogqTeA")
                _attentionLiveData.value = result
            } catch (e: Exception) {
                Log.d("attention","连接失败  $e")
            }
        }

    }

}