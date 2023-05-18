package com.example.newsday.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.newsday.R
import com.example.newsday.databinding.FragmentRecommendDetailBinding
import com.example.newsday.home.db.ListItem
import com.example.newsday.util.HtmlUtil
class RecommendDetailFragment : Fragment() {
    private var pic: String? = null
    private var title: String? = null
    private var content: String? = null
    private var src: String? = null
    private var time: String? = null


    private lateinit var binding: FragmentRecommendDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pic = it.getString(ARG_PIC)
            title = it.getString(ARG_TITLE)
            content = it.getString(ARG_CONTENT)
            src = it.getString(ARG_SRC)
            time = it.getString(ARG_TIME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.pic.load(pic)
        binding.title.text = title
        content?.let {
            val standard = HtmlUtil.getStandard(it)
            binding.content.loadDataWithBaseURL(null, standard, "text/html", "utf-8", null)
        }

    }

    companion object {
        const val ARG_PIC = "pic"
        const val ARG_TITLE = "title"
        const val ARG_CONTENT = "content"
        const val ARG_SRC = "src"
        const val ARG_TIME = "time"
    }
}