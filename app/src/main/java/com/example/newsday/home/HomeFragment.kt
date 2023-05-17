package com.example.newsday.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.newsday.R
import com.example.newsday.home.view.HomeFragmentAttention
import com.example.newsday.home.view.HomeFragmentEpidemic
import com.example.newsday.home.view.HomeFragmentHot
import com.example.newsday.home.view.HomeFragmentRecommend
import com.example.newsday.home.view.IndicatorLineUtil
import com.example.newsday.home.view.MyPagerAdapter
import com.example.newsday.home.view.ZoomOutPageTransFormer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val viewpager: ViewPager2 = root.findViewById(R.id.viewpager)!!
        val tablayout: TabLayout = root.findViewById(R.id.tab_layout)!!
        val titles = arrayListOf<String>("关注", "推荐", "热榜", "疫情")

        val attentionFragment: Fragment = HomeFragmentAttention()
        val recommendFragment: Fragment = HomeFragmentRecommend()
        val hotFragment: Fragment = HomeFragmentHot()
        val epidemicFragment: Fragment = HomeFragmentEpidemic()

        val fragmentList: List<Fragment> = listOf(attentionFragment,recommendFragment,hotFragment,epidemicFragment)
        val fragmentManager: FragmentManager = activity?.supportFragmentManager!!
        val adapter = MyPagerAdapter(lifecycle, fragmentManager, fragmentList)
        viewpager.adapter = adapter
        viewpager.setPageTransformer(ZoomOutPageTransFormer())
        TabLayoutMediator(tablayout, viewpager, true){
                tab, position ->
            tab.text = titles[position]
        }.attach()
        tablayout.post(Runnable {
            IndicatorLineUtil.setIndicator(tablayout,20,20)
        })

        // Inflate the layout for this fragment
        return root;
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}