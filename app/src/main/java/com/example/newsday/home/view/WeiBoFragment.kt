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
import com.example.newsday.databinding.FragmentWeiBoBinding
import com.example.newsday.home.db.HotItem
import com.example.newsday.home.viewmodel.HotListViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WeiBoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentWeiBoBinding

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
    ): View {
        binding = FragmentWeiBoBinding.inflate(inflater,container,false)
        val hotListViewModel = ViewModelProvider(this)[HotListViewModel::class.java]
        hotListViewModel.getHotList("weibo")
        hotListViewModel.hotListLiveData.observe(requireActivity()){
            if (it == null) {
                Log.d("Hot", "weibo: it==null")
                return@observe
            }
            val layoutManager = LinearLayoutManager(context)

            val adapter = WeiBoAdapter(it)
            binding.weiboRecycler.layoutManager = layoutManager
            binding.weiboRecycler.adapter = adapter
            binding.weiboRecycler.setItemViewCacheSize(50)
            adapter.setOnItemClickListener(object : WeiBoAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    val string = it[position].link
                    intent.data = Uri.parse(string)
                    startActivity(intent)
                }
            })
        }
        return binding.root
    }

    class WeiBoAdapter(val weiboList: List<HotItem>): RecyclerView.Adapter<WeiBoAdapter.ViewHolder>() {

        private var mOnItemClickListener: OnItemClickListener? = null

        interface OnItemClickListener {
            fun onItemClick(view: View?, position: Int)
        }
        fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
            mOnItemClickListener = onItemClickListener
        }

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val weiboText : TextView = view.findViewById(R.id.weibo_text)
            val weiboNum : TextView = view.findViewById(R.id.weibo_num)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.weibo_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val weibo = weiboList.get(position)
            holder.weiboText.text = weibo.title
            holder.weiboNum.text = (position+1).toString()
            if(position==0){
                holder.weiboNum.setTextColor(Color.RED)
                holder.weiboNum.setTypeface(Typeface.DEFAULT_BOLD)
            }
            if(position==1){
                holder.weiboNum.setTextColor(Color.parseColor("#FF5722"))
                holder.weiboNum.setTypeface(Typeface.DEFAULT_BOLD)
            }
            if(position==2){
                holder.weiboNum.setTextColor(Color.parseColor("#FF9800"))
                holder.weiboNum.setTypeface(Typeface.DEFAULT_BOLD)
            }

            holder.itemView.setOnClickListener{
                if(mOnItemClickListener!=null){
                    mOnItemClickListener?.onItemClick(it,position)
                }
            }
        }

        override fun getItemCount(): Int {
            return weiboList.size
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeiBoFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeiBoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}