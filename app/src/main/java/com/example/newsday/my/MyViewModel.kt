package com.example.newsday.my


import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsday.home.db.ListItem
import com.example.newsday.home.viewmodel.RecommendDetailViewModel
import com.example.newsday.login.SpHelp
import com.example.newsday.util.ImageUtil
import kotlinx.coroutines.launch

class MyViewModel: RecommendDetailViewModel() {

    var likedDateLiveDate: MutableLiveData<List<ListItem>> = MutableLiveData()

    var myImageLiveDate: MutableLiveData<Bitmap> = MutableLiveData()

    fun getAllLike() {
        viewModelScope.launch {
            likedDateLiveDate.value = commendDetailDateBaseHelper?.getAll()?.map {
                ListItem("", it.content ?: "", it.pic ?: "", it.src ?: "", it.time ?: "", it.title ?: "", "", "")
            }
        }

    }

    fun upDateMyImage(bitmap: Bitmap) {
        val base64Encode = ImageUtil.base64Encode(bitmap)
        SpHelp.setStringValue(MyFragment.SP_MY_IMAGE, base64Encode)
    }

    fun getMyImage() {
        val base64Image = SpHelp.getStringValue(MyFragment.SP_MY_IMAGE)
        base64Image?.let {
            myImageLiveDate.value = ImageUtil.base64Decode(it)
        }
    }
}