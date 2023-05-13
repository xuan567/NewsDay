package com.example.newsday.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsday.network.ApiRetrofit
import com.example.newsday.search.db.GarbageRecognitionBean
import kotlinx.coroutines.launch

class GarbageResultViewModel : ViewModel() {
    private val _garbageText: MutableLiveData<GarbageRecognitionBean> = MutableLiveData()
    val garbageText: LiveData<GarbageRecognitionBean> = _garbageText

    private val _garbageImage: MutableLiveData<GarbageRecognitionBean> = MutableLiveData()
    val garbageImage: LiveData<GarbageRecognitionBean> = _garbageImage

    private val _garbageVideo: MutableLiveData<GarbageRecognitionBean> = MutableLiveData()
    val garbageVideo: LiveData<GarbageRecognitionBean> = _garbageVideo

    fun getGarbageTypeByText(word: String) {
        viewModelScope.launch {
            try {
                val result = ApiRetrofit.getGarbageRequest()
                    .apiTextGarbageRecognition(ApiRetrofit.GARBAGE_API_KEY, word)
                _garbageText.value = result
            } catch (e: Exception) {
                Log.d("GarbageResultViewModel", "getGarbageTypeText: $e")
            }
        }
    }

    fun getGarbageTypeByBase64Image(base64Image: String) {
        viewModelScope.launch {
            try {
                val result = ApiRetrofit.getGarbageRequest()
                    .apiImageGarbageRecognition(ApiRetrofit.GARBAGE_API_KEY, base64Image)
                _garbageImage.value = result
            } catch (e: Exception) {
                Log.d("GarbageResultViewModel", "getGarbageTypeByBase64Image: $e")
            }
        }
    }

    fun getGarbageTypeByVideo(say: String, format: String) {
        viewModelScope.launch {
            try {
                val result = ApiRetrofit.getGarbageRequest()
                    .apiVoiceGarbageRecognition(ApiRetrofit.GARBAGE_API_KEY, say, format)
                _garbageVideo.value = result
            } catch (e: Exception) {
                Log.d("GarbageResultViewModel", "getGarbageTypeByVideo: $e")
            }
        }
    }

}