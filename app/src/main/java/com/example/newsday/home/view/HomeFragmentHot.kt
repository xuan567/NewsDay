package com.example.newsday.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.newsday.R
import com.example.newsday.databinding.FragmentHomeHotBinding
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragmentHot.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragmentHot : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeHotBinding

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
        binding = FragmentHomeHotBinding.inflate(inflater,container,false)
        val titles = arrayListOf("微博", "头条", "知乎")
        val fragmentList: List<Fragment> = listOf(WeiBoFragment(),TouTiaoFragment(),ZhiHuFragment())
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        if(fragmentManager != null){
            val adapter = MyPagerAdapter(lifecycle, fragmentManager, fragmentList)
            binding.viewpager.apply {
                this.adapter = adapter
                this.setPageTransformer(ZoomOutPageTransFormer())
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewpager, true){
                tab, position ->
            tab.text = titles[position]
        }.attach()
        binding.tabLayout.post(Runnable {
            IndicatorLineUtil.setIndicator(binding.tabLayout,20,20)
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragmentHot.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragmentHot().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}