package com.example.newsday.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsday.home.db.HotData
import com.example.newsday.network.ApiRetrofit
import kotlinx.coroutines.launch

class HotListViewModel: ViewModel() {
    private var _hotListLiveData: MutableLiveData<HotData> = MutableLiveData()
    val hotListLiveData: LiveData<HotData> = _hotListLiveData

    fun getHotList(type: String){
        viewModelScope.launch {
            try {
                val result = ApiRetrofit.hotListRequest().apiHotList("5tMzmrhXv4ogqTeA",type)
                _hotListLiveData.value = result.data
            }catch (e: Exception){
                Log.d("HotListViewModel", "HotListViewModel: $e")
            }
        }
    }
}