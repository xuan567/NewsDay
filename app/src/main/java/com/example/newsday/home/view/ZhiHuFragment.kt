package com.example.newsday.home.view

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsday.R
import com.example.newsday.databinding.FragmentZhiHuBinding
import com.example.newsday.home.db.HotItem
import com.example.newsday.home.viewmodel.HotListViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ZhiHuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ZhiHuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentZhiHuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentZhiHuBinding.inflate(inflater, container, false)
        val hotListViewModel = ViewModelProvider(this)[HotListViewModel::class.java]
        hotListViewModel.getHotList("zhihu")
        hotListViewModel.hotListLiveData.observe(requireActivity()){
            if (it == null) {
                Log.d("Hot", "zhihu: it==null")
                return@observe
            }
            val layoutManager = LinearLayoutManager(context)
            val adapter = ZhiHuAdapter(it.list)
            binding.zhihuRecycler.layoutManager = layoutManager
            binding.zhihuRecycler.adapter = adapter
            binding.zhihuRecycler.setItemViewCacheSize(50)
            adapter.setOnItemClickListener(object : ZhiHuAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    val string = it.list[position].link
                    intent.data = Uri.parse(string)
                    startActivity(intent)
                }
            })
        }
        return binding.root

    }

    class ZhiHuAdapter(val zhihuList: List<HotItem>) :
        RecyclerView.Adapter<ZhiHuAdapter.ViewHolder>() {

        private var mOnItemClickListener: OnItemClickListener? = null

        interface OnItemClickListener {
            fun onItemClick(view: View?, position: Int)
        }

        fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
            mOnItemClickListener = onItemClickListener
        }


        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val zhihuNum: TextView = view.findViewById(R.id.zhihu_num)
            val zhihuText: TextView = view.findViewById(R.id.zhihu_text)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZhiHuAdapter.ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.zhihu_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ZhiHuAdapter.ViewHolder, position: Int) {
            val zhihu = zhihuList.get(position)
            holder.zhihuNum.text = (position + 1).toString()
            holder.zhihuText.text = zhihu.title

            if (position == 0) {
                holder.zhihuNum.setTextColor(Color.RED)
                holder.zhihuNum.setTypeface(Typeface.DEFAULT_BOLD)
            }
            if (position == 1) {
                holder.zhihuNum.setTextColor(Color.parseColor("#FF5722"))
                holder.zhihuNum.setTypeface(Typeface.DEFAULT_BOLD)
            }
            if (position == 2) {
                holder.zhihuNum.setTextColor(Color.parseColor("#FF9800"))
                holder.zhihuNum.setTypeface(Typeface.DEFAULT_BOLD)
            }

            holder.itemView.setOnClickListener {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener?.onItemClick(it, position)
                }
            }
        }

        override fun getItemCount(): Int {
            return zhihuList.size
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ZhiHuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ZhiHuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}