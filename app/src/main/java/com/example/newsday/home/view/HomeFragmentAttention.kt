package com.example.newsday.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsday.R
import com.example.newsday.databinding.FragmentHomeAttentionBinding
import com.example.newsday.home.viewmodel.AttentionViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragmentAttention.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragmentAttention : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    lateinit var attentionViewModel: AttentionViewModel
    private lateinit var binding: FragmentHomeAttentionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeAttentionBinding.inflate(inflater, container, false)
        attentionViewModel = ViewModelProvider(this)[AttentionViewModel::class.java]
        attentionViewModel.attention()

        attentionViewModel.attentionLiveData.observe(requireActivity()) {
            if (it == null) {
                Log.d("TAG", "onCreateView: it==null")
                return@observe
            }
            binding.attentionTime.isVisible = true
            val layoutManager = LinearLayoutManager(context)
            binding.attentionRecycler.apply {
                this.layoutManager = layoutManager
                val newsList = it.news
                val adapter = AttentionNewsAdapter(newsList)
               this.adapter = adapter
            }
        }
        binding.model = attentionViewModel
        binding.lifecycleOwner = this


        return binding.root
    }

    class AttentionNewsAdapter(val newsList: List<String>): RecyclerView.Adapter<AttentionNewsAdapter.ViewHolder>(){

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val newsText: TextView = view.findViewById(R.id.news_item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.attention_news_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val news = newsList[position]
            holder.newsText.text = news
        }

        override fun getItemCount() = newsList.size

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragmentAttention.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragmentAttention().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}