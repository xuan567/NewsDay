package com.example.newsday

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Toast：显示错误Toast
     */
    fun showErrorToast(context: Context, msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, msg, duration).show()
    }

    /**
     * Toast：显示成功Toast
     */
    fun showSuccessToast(context: Context, msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, msg, duration).show()
    }
}