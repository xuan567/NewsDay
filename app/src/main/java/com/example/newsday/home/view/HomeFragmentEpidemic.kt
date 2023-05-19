package com.example.newsday.home.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsday.R
import com.example.newsday.databinding.FragmentHomeEpidemicBinding
import com.github.testpress.mikephil.charting.charts.PieChart
import com.github.testpress.mikephil.charting.data.PieData
import com.github.testpress.mikephil.charting.data.PieDataSet
import com.github.testpress.mikephil.charting.data.PieEntry
import com.github.testpress.mikephil.charting.formatter.PercentFormatter
import com.github.testpress.mikephil.charting.utils.ColorTemplate

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragmentEpidemic.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragmentEpidemic : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var pieChart: PieChart? = null
    private lateinit var binding: FragmentHomeEpidemicBinding

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
        binding = FragmentHomeEpidemicBinding.inflate(inflater, container, false)
        binding.internalText.setTextColor(Color.WHITE)
        binding.abroadText.setTextColor(Color.DKGRAY)
        initCharView()
        initCharData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hesuanImg.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_mapsFragment)
        }

        binding.internalText.setOnClickListener {
            binding.internalText.setTextColor(Color.WHITE)
            binding.abroadText.setTextColor(Color.DKGRAY)
            binding.internalImg.isVisible = true
            binding.abroadImg.isVisible = false
        }
        binding.abroadText.setOnClickListener {
            binding.internalText.setTextColor(Color.DKGRAY)
            binding.abroadText.setTextColor(Color.WHITE)
            binding.internalImg.isVisible = false
            binding.abroadImg.isVisible = true
        }
    }

    private fun initCharView() {
        pieChart = PieChart(context)
        pieChart?.apply {
            val lp = FrameLayout.LayoutParams(850, 850)
            layoutParams = lp
        }
        binding.chartContainer.addView(pieChart)
    }

    private fun initCharData() {
        val values = arrayListOf<PieEntry>()
        values.add(PieEntry(40f, "累计治愈"))
        values.add(PieEntry(10f, "累计死亡"))
        values.add(PieEntry(40f, "累计确诊"))
        values.add(PieEntry(10f, "现存确诊"))

        //数据和颜色
        val dataColors = arrayListOf<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) dataColors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) dataColors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) dataColors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) dataColors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) dataColors.add(c)
        dataColors.add(ColorTemplate.getHoloBlue())
        val mPieDataSet = PieDataSet(values, "Label")
        mPieDataSet.apply {
            valueFormatter = PercentFormatter()
            colors = dataColors
            valueTextColor = Color.DKGRAY// 设置百分比字体颜色
            valueTextSize = 15f // 设置百分比字体大小
        }
        val mPieData = PieData(mPieDataSet)
        pieChart?.setEntryLabelColor(Color.DKGRAY) // 设置图表扇形文字颜色
        pieChart?.data = mPieData
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragmentEpidemic().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}