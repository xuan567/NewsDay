package com.example.newsday.home.view

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Field

object IndicatorLineUtil {
    fun setIndicator(tabs: TabLayout, leftDip:Int, rightDip:Int){
        val tablayout:Class<*> = tabs.javaClass
        var tabStrip : Field? = null
        try{
            tabStrip = tablayout.getDeclaredField("slidingTabIndicator")
        }catch (e:NoSuchFieldError){
            e.printStackTrace()
        }
        tabStrip?.isAccessible = true
        var llTab: LinearLayout? = null
        try {
            llTab = tabStrip?.get(tabs) as LinearLayout
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        val left =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                leftDip.toFloat(),
                Resources.getSystem().getDisplayMetrics()
            )
                .toInt()
        val right =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                rightDip.toFloat(),
                Resources.getSystem().getDisplayMetrics()
            )
                .toInt()
        for (i in 0 until llTab!!.childCount) {
            val child: View = llTab.getChildAt(i)
            child.setPadding(0, 0, 0, 0)
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1F)
            params.leftMargin = left
            params.rightMargin = right
            child.setLayoutParams(params)
            child.invalidate()
        }

    }

    fun app(){

    }
}