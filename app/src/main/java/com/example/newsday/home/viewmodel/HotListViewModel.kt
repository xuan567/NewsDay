package com.example.newsday.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsday.home.db.HotItem
import com.example.newsday.network.ApiRetrofit
import com.example.newsday.persistence.dateBaseHelper.HotDateBaseHelper
import kotlinx.coroutines.launch

class HotListViewModel: ViewModel() {
    private var _hotListLiveData: MutableLiveData<List<HotItem>> = MutableLiveData()
    val hotListLiveData: LiveData<List<HotItem>> = _hotListLiveData

    var hotDateBaseHelper: HotDateBaseHelper? = null
    fun initCommendDetailDateBaseHelper(context: Context) {
        hotDateBaseHelper =  HotDateBaseHelper.instance(context)
    }




    fun getHotList(type: String){
        viewModelScope.launch {
            try {
                val result = ApiRetrofit.hotListRequest().apiHotList("5tMzmrhXv4ogqTeA",type)
                if(type == TYPE_ZHIHU ) {
                    val hotData: List<HotItem>?
                    if(result.data.list.isNotEmpty()) {
                        hotData = result.data.list
                        hotDateBaseHelper?.deleteAll()
                        hotDateBaseHelper?.insertAll(result.data.list)
                    } else {
                        hotData = hotDateBaseHelper?.queryAll()
                    }
                    hotData?.let {
                        _hotListLiveData.value = it
                    }
                } else {
                    _hotListLiveData.value = result.data.list
                }
            }catch (e: Exception){
                Log.d("HotListViewModel", "HotListViewModel: $e")
            }
        }
    }
     companion object {
         private const val TYPE_ZHIHU = "zhihu"
     }
}