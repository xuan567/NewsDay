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
import com.example.newsday.databinding.FragmentTouTiaoBinding
import com.example.newsday.home.db.HotItem
import com.example.newsday.home.viewmodel.HotListViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.s
 * Use the [TouTiaoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TouTiaoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentTouTiaoBinding

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
        binding = FragmentTouTiaoBinding.inflate(inflater,container,false)
        val toutiaoViewModel = ViewModelProvider(this)[HotListViewModel::class.java]
        toutiaoViewModel.getHotList("toutiao")
        toutiaoViewModel.hotListLiveData.observe(requireActivity()) {
            if (it == null) {
                Log.d("TouTiao", "it==null")
                return@observe
            }

            val layoutManager = LinearLayoutManager(context)
            val adapter = TouTiaoAdapter(it)
            binding.toutiaoRecycler.layoutManager = layoutManager
            binding.toutiaoRecycler.adapter = adapter
            binding.toutiaoRecycler.setItemViewCacheSize(50)

            adapter.setOnItemClickListener(object : TouTiaoAdapter.OnItemClickListener {
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

    class TouTiaoAdapter(val toutiaoList : List<HotItem>) : RecyclerView.Adapter<TouTiaoAdapter.ViewHolder>() {

        private var mOnItemClickListener: OnItemClickListener? = null

        interface OnItemClickListener {
            fun onItemClick(view: View?, position: Int)
        }
        fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
            mOnItemClickListener = onItemClickListener
        }



        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val toutiaoNum : TextView = view.findViewById(R.id.toutiao_num)
            val toutiaoText : TextView = view.findViewById(R.id.toutiao_text)
            val toutiaoOther : TextView = view.findViewById(R.id.toutiao_other)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.toutiao_item,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val toutiao = toutiaoList[position]
            holder.toutiaoNum.text = (position+1).toString()
            holder.toutiaoText.text = toutiao.title
            holder.toutiaoOther.text = toutiao.other

            if(position==0){
                holder.toutiaoNum.setTextColor(Color.RED)
                holder.toutiaoNum.setTypeface(Typeface.DEFAULT_BOLD)
            }
            if(position==1){
                holder.toutiaoNum.setTextColor(Color.parseColor("#FF5722"))
                holder.toutiaoNum.setTypeface(Typeface.DEFAULT_BOLD)
            }
            if(position==2){
                holder.toutiaoNum.setTextColor(Color.parseColor("#FF9800"))
                holder.toutiaoNum.setTypeface(Typeface.DEFAULT_BOLD)
            }

            holder.itemView.setOnClickListener {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener?.onItemClick(it,position)
                }
            }
        }

        override fun getItemCount(): Int {
            return toutiaoList.size
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TouTiaoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TouTiaoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}