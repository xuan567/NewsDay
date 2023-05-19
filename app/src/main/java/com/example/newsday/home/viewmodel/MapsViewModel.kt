package com.example.newsday.home.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsday.BaseApplication
import com.example.newsday.home.db.MapMarkDate
import com.example.newsday.persistence.dateBaseHelper.MapMarkDateBaseHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsViewModel: ViewModel() {

    private var _markLiveData: MutableLiveData<List<MapMarkDate>> = MutableLiveData()
    val markLiveData: LiveData<List<MapMarkDate>> = _markLiveData

    var addMarkLiveData: MutableLiveData<List<Boolean>> = MutableLiveData()


    private var commendDetailDateBaseHelper: MapMarkDateBaseHelper? = null

    /**
       初始化Room数据库
     */
    fun initCommendDetailDateBaseHelper(context: Context) {
        commendDetailDateBaseHelper =  MapMarkDateBaseHelper.instance(context)
    }

    fun getAllMark() {
        viewModelScope.launch {
            val markList = commendDetailDateBaseHelper?.queryAll()
            if(markList.isNullOrEmpty()) {
                initMapMarkDb()
                _markLiveData.value = DEFAULT_MARK_LIST
            } else {
                _markLiveData.value = markList!!
            }
        }
    }

    fun addMark(date: MapMarkDate) {
        viewModelScope.launch {
            commendDetailDateBaseHelper?.insertMark(date)
            delay(5000) //模拟审核过程
            addMarkLiveData.value = listOf(true)
        }

    }

    private fun initMapMarkDb() {
        //添加模拟数据到数据库
        viewModelScope.launch {
            commendDetailDateBaseHelper?.insertMarkList(DEFAULT_MARK_LIST)
        }
    }

    companion object {
        private val DEFAULT_MARK_LIST = listOf(
            MapMarkDate(title = "西安邮电大学长安校区医务室", snippet = "西安市长安区西长安街西安邮电大学长安校区", lat = 34.152397, lng = 108.898258),
            MapMarkDate(title = "西安市长安区医院", snippet = "西安市长安区文苑中路120号", lng = 109.172557, lat = 34.10802),
            MapMarkDate(title = "陕西省人民医院", snippet = "西安市碑林区友谊西路256号陕西省人民医院内", lng = 108.93015, lat = 34.239675),
            MapMarkDate(title = "西安交通大学第一附属医院总院", snippet = "西安市雁塔区雁塔西路277号",lng = 108.936741, lat = 34.219569),
            MapMarkDate(title = "西北政法大学医务室", snippet = "西安市长安区西长安街南侧558号",lng = 108.926353, lat = 34.165894)
        )
    }

}