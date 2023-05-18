package com.example.newsday.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun loadImage(imageView: ImageView?, imgUrl: String?){
    if(imageView == null) {
        return
    }
    if (imgUrl != null){
        Glide.with(imageView.context)
            .load(imgUrl)
            .into(imageView)
    }
}