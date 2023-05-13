package com.example.newsday.util

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.net.URLEncoder

object ImageUtil {

    /**
     * 为解决retrofit2.HttpException: HTTP 413  Request Entity Too Large（请求实体太大）
     * 先对图片进行质量压缩  使图片小于1M
     * 再base64编码
     */
    fun base64Encode(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        //读取图片到ByteArrayOutputStream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos) //参数如果为100那么就不压缩
        var option = 100
        while (baos.toByteArray().size / 1024 > 100) {
            baos.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, option, baos)
            option -= 10
        }
        val bytes: ByteArray = baos.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun encodeUTF8(date: String): String {
        return URLEncoder.encode(date, "utf-8");
    }



}