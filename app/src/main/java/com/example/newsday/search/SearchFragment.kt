package com.example.newsday.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.newsday.databinding.FragmentSearchBinding
import com.example.newsday.home.view.IndicatorLineUtil
import com.example.newsday.home.view.MyPagerAdapter
import com.example.newsday.search.view.GarbageClassificationFragment
import com.example.newsday.search.view.GarbageGuideFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val viewPager2: ViewPager2 = binding.viewpager
        val tabLayout: TabLayout = binding.tabLayout
        val titles = arrayListOf("识别", "指南")
        val fragmentLists: List<Fragment> =
            listOf(GarbageClassificationFragment.newInstance(), GarbageGuideFragment.newInstance())
        val fragmentManager: FragmentManager = activity?.supportFragmentManager ?: return
        val adapter = MyPagerAdapter(lifecycle, fragmentManager, fragmentLists)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2, true){
                tab, position ->
            tab.text = titles[position]
        }.attach()
        tabLayout.post {
            IndicatorLineUtil.setIndicator(tabLayout, 20, 20)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}