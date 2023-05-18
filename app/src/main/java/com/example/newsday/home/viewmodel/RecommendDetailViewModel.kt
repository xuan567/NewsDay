package com.example.newsday.home.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsday.persistence.bean.CommendDetailDate
import com.example.newsday.persistence.dateBaseHelper.CommendDetailDateBaseHelper
import kotlinx.coroutines.launch

class RecommendDetailViewModel: ViewModel() {
    var isLikeLiveDate: MutableLiveData<CommendDetailDate> = MutableLiveData()


    private var commendDetailDateBaseHelper: CommendDetailDateBaseHelper? = null
    fun initCommendDetailDateBaseHelper(context: Context) {
        commendDetailDateBaseHelper =  CommendDetailDateBaseHelper.instance(context)
    }


    fun getDetailIsLiked(title: String) {
        viewModelScope.launch {
            isLikeLiveDate.value = commendDetailDateBaseHelper?.isLikedByTitle(title)
        }
    }

    fun insertLike(date: CommendDetailDate) {
        viewModelScope.launch {
            commendDetailDateBaseHelper?.insertLike(date)
        }

    }

    fun deleteLike(title: String) {
        viewModelScope.launch {
            commendDetailDateBaseHelper?.deleteLike(title)
        }
    }

}