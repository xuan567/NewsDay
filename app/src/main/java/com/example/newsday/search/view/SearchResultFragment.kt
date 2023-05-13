package com.example.newsday.search.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsday.R
import com.example.newsday.databinding.FragmentGarbageResultRecyclerItemBinding
import com.example.newsday.databinding.FragmentSearchResultBinding
import com.example.newsday.search.db.GarbageRecognitionBean
import com.example.newsday.search.viewmodel.GarbageResultViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchResultFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GarbageResultViewModel

    private var type: Int? = 0

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
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GarbageResultViewModel::class.java]
        initDate()
        initView()
        initListener()
    }

    private fun initDate() {
        val bundle = arguments
        if (bundle != null) {
            type = bundle.getInt("type")
        }
    }

    private fun initView() {
        binding.recyclerItem.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            val date = GarbageClassificationFragment.dateList?.result?.list ?: emptyList()
            this.adapter = MyGarbageResultRecyclerViewAdapter(date)
        }
    }

    private fun initListener() {
        binding.know.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    class MyGarbageResultRecyclerViewAdapter(private val values: List<GarbageRecognitionBean.TextItem>) :
        RecyclerView.Adapter<MyGarbageResultRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                FragmentGarbageResultRecyclerItemBinding.inflate(
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

        inner class ViewHolder(binding: FragmentGarbageResultRecyclerItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val name: TextView = binding.wasteName
            val sort: TextView = binding.wasteSort
            val imageTwo: ImageView = binding.imageTwo
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchResultFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


        val typeMap: HashMap<Int, String> = hashMapOf()
        init {
            typeMap[0] = "可回收垃圾"
            typeMap[1] = "有害垃圾"
            typeMap[2] = "厨余垃圾"
            typeMap[3] = "其他垃圾"
        }
    }
}