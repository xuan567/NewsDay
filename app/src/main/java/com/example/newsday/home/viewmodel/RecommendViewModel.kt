package com.example.newsday.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsday.home.db.RecommendResult
import com.example.newsday.home.request.RecommendGetRequest

class RecommendViewModel : ViewModel() {
    private val _recommendLiveData = MutableLiveData<RecommendResult>()
    val recommendLiveData: LiveData<RecommendResult> = _recommendLiveData
    val recommendRequest = RecommendGetRequest()
    fun recommend() {
        recommendRequest.recommendRequest(_recommendLiveData)
    }
}