package com.example.newsday.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsday.R
import com.example.newsday.databinding.GarbageResultRecyclerItemBinding
import com.example.newsday.search.db.GarbageRecognitionBean
import com.example.newsday.search.view.SearchResultFragment

class GarbageResultRecyclerAdapter(private val values: List<GarbageRecognitionBean.TextItem>) :
    RecyclerView.Adapter<GarbageResultRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            GarbageResultRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val type = SearchResultFragment.typeMap[item.type ?: item.lajitype]
        holder.sort.text = type
        holder.name.text = item.name
        if (type.equals("厨余垃圾")) {
            holder.imageTwo.setImageResource(R.drawable.kitchen)
        }
        if (type.equals("其他垃圾")) {
            holder.imageTwo.setImageResource(R.drawable.other)
        }
        if (type.equals("有害垃圾")) {
            holder.imageTwo.setImageResource(R.drawable.nonrecyclable)
        }
        if (type.equals("可回收垃圾")) {
            holder.imageTwo.setImageResource(R.drawable.recyclable)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: GarbageResultRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.wasteName
        val sort: TextView = binding.wasteSort
        val imageTwo: ImageView = binding.imageTwo
    }

}