package com.example.newsday.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsday.home.db.HotData

class RecommendDetailViewModel: ViewModel() {
    private var _isLikeLiveDate: MutableLiveData<HotData> = MutableLiveData()
    val isLikeLiveDate: LiveData<HotData> = _isLikeLiveDate

    fun getDetailIsLiked(key: String) {

    }
}