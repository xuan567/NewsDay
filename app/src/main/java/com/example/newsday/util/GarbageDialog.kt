package com.example.newsday.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newsday.R
import com.example.newsday.databinding.GarbageDialogBinding
import com.example.newsday.search.db.GarbageRecognitionBean

class GarbageDialog @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var binding: GarbageDialogBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.garbage_dialog,null)
        binding = GarbageDialogBinding.bind(view)
    }

    inner class Builder{

        fun setGarbageResultList(list: List<GarbageRecognitionBean.TextItem>){
            binding.garbageRecycler.apply {
                this.layoutManager = LinearLayoutManager(context)
                this.adapter = GarbageResultRecyclerAdapter(list)
            }
        }

        fun setGarbageHeadImage(imgUrl: String){
            Glide.with(binding.garbageImage.context)
                .load(imgUrl)
                .into(binding.garbageImage)
        }
        fun setButtonClick(){
            binding.knowButton.setOnClickListener {

            }
            binding.closeIcon.setOnClickListener {

            }
        }
    }





}

