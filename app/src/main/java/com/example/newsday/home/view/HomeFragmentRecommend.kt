package com.example.newsday.home.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsday.R
import com.example.newsday.databinding.FragmentHomeRecommendBinding
import com.example.newsday.home.db.ListItem
import com.example.newsday.home.viewmodel.RecommendViewModel

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragmentRecommend.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragmentRecommend : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var fragmentHomeRecommendBinding: FragmentHomeRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeRecommendBinding = FragmentHomeRecommendBinding.inflate(inflater, container, false)
        val recommendViewModel = ViewModelProvider(this)[RecommendViewModel::class.java]
        recommendViewModel.recommend()
        recommendViewModel.recommendLiveData.observe(requireActivity()) {
            if (it == null) {
                Log.d("Recommend", "it==null")
            }
            val layoutManager = LinearLayoutManager(context)
            val adapter = context?.let {
                    it1 -> RecommendAdapter(it1, it.list)
            }
            fragmentHomeRecommendBinding.recommendRecycler.layoutManager = layoutManager
            fragmentHomeRecommendBinding.recommendRecycler.adapter = adapter

            adapter?.setOnItemClickListener(object : RecommendAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
//                    val arr = it.list[position]
//                    val recommendText = RecommendText().newInstance(
//                        arr.pic,
//                        arr.title,
//                        arr.content,
//                        arr.src,
//                        arr.time
//                    )
//                    replaceFragment(recommendText)
                }

            })
        }
        // Inflate the layout for this fragment
        return fragmentHomeRecommendBinding.root

    }

    class RecommendAdapter(val context: Context, var recommendList: List<ListItem>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val TYPE_ITEM_ONE = 1
        private val TYPE_ITEM_TWO = 2
        private val TYPE_ITEM_THREE = 3

        private var mOnItemClickListener: OnItemClickListener? = null

        interface OnItemClickListener {
            fun onItemClick(view: View?, position: Int)
        }
        fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
            mOnItemClickListener = onItemClickListener
        }

        //创建三种ViewHolder
        private inner class OneViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val item1Text : TextView = view.findViewById(R.id.item1_text)
            val item1Image : ImageView = view.findViewById(R.id.item1_pc)
            val item1Time : TextView = view.findViewById(R.id.item1_time)
        }
        private inner class TwoViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val item2Text : TextView = view.findViewById(R.id.item2_text)
            val item2Image : ImageView = view.findViewById(R.id.item2_pc)
            val item2Time : TextView = view.findViewById(R.id.item2_time)
        }
        private inner class ThreeViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val item3Text : TextView = view.findViewById(R.id.item3_text)
            val item3Image1 : ImageView = view.findViewById(R.id.item3_pc1)
            val item3Image2 : ImageView = view.findViewById(R.id.item3_pc2)
            val item3Image3 : ImageView = view.findViewById(R.id.item3_pc3)
            val item3Time : TextView = view.findViewById(R.id.item3_time)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                TYPE_ITEM_ONE -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_one, parent, false)
                    OneViewHolder(view)
                }
                TYPE_ITEM_TWO -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_two, parent, false)
                    TwoViewHolder(view)
                }
                else -> {
                    val view =
                        LayoutInflater.from(parent.context).inflate(R.layout.item_three, parent, false)
                    ThreeViewHolder(view)
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val recommend = recommendList[position]
            val text = "${recommend.src} + ${recommend.time}"
            when (holder) {
                is OneViewHolder -> {
                    holder.item1Text.text = recommend.title
                    holder.item1Time.text = text
                    Glide.with(context).load(recommend.pic).into(holder.item1Image)
                }

                is TwoViewHolder -> {
                    holder.item2Text.text = recommend.title
                    Glide.with(context).load(recommend.pic).into(holder.item2Image)
                    holder.item2Time.text = text
                }

                is ThreeViewHolder -> {
                    holder.item3Text.text = recommend.title
                    Glide.with(context).load(recommend.pic).into(holder.item3Image1)
                    Glide.with(context).load(recommend.pic).into(holder.item3Image2)
                    Glide.with(context).load(recommend.pic).into(holder.item3Image3)
                    holder.item3Time.text = text
                }
            }

            holder.itemView.setOnClickListener {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener?.onItemClick(it,position)
                }
            }
        }

        override fun getItemCount(): Int {
            return recommendList.size
        }

        override fun getItemViewType(position: Int): Int {
            return if (position % 3 == 1) {
                TYPE_ITEM_ONE
            } else if (position % 3 == 2) {
                TYPE_ITEM_TWO
            } else {
                TYPE_ITEM_THREE
            }
        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragmentRecommend.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragmentRecommend().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}