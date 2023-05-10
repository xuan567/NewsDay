package com.example.newsday.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsday.home.db.AttentionData
import com.example.newsday.home.request.HomeAttentionGetRequest

class AttentionViewModel : ViewModel() {

    private val _attentionLiveData = MutableLiveData<AttentionData>()
    val attentionLiveData: LiveData<AttentionData> = _attentionLiveData
    val homeAttentionGetRequest = HomeAttentionGetRequest()


    fun attention(){
        homeAttentionGetRequest.attentionRequest(_attentionLiveData)
    }

}