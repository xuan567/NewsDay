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
import com.example.newsday.databinding.FragmentZhiHuBinding
import com.example.newsday.home.db.HotItem
import com.example.newsday.home.viewmodel.HotListViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ZhiHuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ZhiHuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentZhiHuBinding
    private val zhihuList = ArrayList<HotItem>()

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
        binding = FragmentZhiHuBinding.inflate(inflater, container, false)
        val hotListViewModel = ViewModelProvider(this)[HotListViewModel::class.java]
        hotListViewModel.getHotList("zhihu")
        hotListViewModel.initCommendDetailDateBaseHelper(requireContext())
//        hotListViewModel.hotListLiveData.observe(requireActivity()) {
//            if (it == null) {
//                Log.d("Hot", "zhihu: it==null")
//                return@observe
//            }
//            val layoutManager = LinearLayoutManager(context)
//            val adapter = ZhiHuAdapter(it)
//            binding.zhihuRecycler.layoutManager = layoutManager
//            binding.zhihuRecycler.adapter = adapter
//            binding.zhihuRecycler.setItemViewCacheSize(50)
//            adapter.setOnItemClickListener(object : ZhiHuAdapter.OnItemClickListener {
//                override fun onItemClick(view: View?, position: Int) {
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    val string = it[position].link
//                    intent.data = Uri.parse(string)
//                    startActivity(intent)
//                }
//            })
//        }
        initListData()
        val layoutManager = LinearLayoutManager(context)
        val adapter = ZhiHuAdapter(zhihuList)
        binding.zhihuRecycler.layoutManager = layoutManager
        binding.zhihuRecycler.adapter = adapter
        binding.zhihuRecycler.setItemViewCacheSize(50)
        adapter.setOnItemClickListener(object : ZhiHuAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val intent = Intent(Intent.ACTION_VIEW)
                val string = zhihuList[position].link
                intent.data = Uri.parse(string)
                startActivity(intent)
            }
        })

        return binding.root

    }

    private fun initListData() {
        zhihuList.add(
            HotItem(
                1,
                "https://www.zhihu.com/question/602362308",
                "653 万热度",
                "2023 年退休人员基本养老金上调 3.8% ，将带来哪些影响？"
            )
        )
        zhihuList.add(
            HotItem(
                2,
                "https://www.zhihu.com/question/602439506",
                "1229 万热度",
                "俄方称一支乌军部队攻入俄罗斯境内，具体情况如何？将对俄乌局势产生哪些影响？"
            )
        )
        zhihuList.add(
            HotItem(
                3,
                "https://www.zhihu.com/question/547031865",
                "429 万热度",
                "为什么做梦时大脑生成的梦境世界如此宏大且真实，而清醒时大脑内存却这么低？"
            )
        )
        zhihuList.add(
            HotItem(
                4,
                "https://www.zhihu.com/question/601942509",
                "294 万热度",
                "刘若英演唱会试点强制实名入场，开启临时退票通道，这一措施会得到推广吗？「强实名」购票入场实施为什么难"
            )
        )
        zhihuList.add(
            HotItem(
                5,
                "https://www.zhihu.com/question/599884016",
                "287 万热度",
                "男朋友给正在同居的我们列了一个预算表，所有东西AA，我看完很不舒服，是我太自私吗？"
            )
        )
        zhihuList.add(
            HotItem(
                6,
                "https://www.zhihu.com/question/601857583",
                "116 万热度",
                "新文章提出取消作者姓名排序，人人都是一作，你赞同这种署名方案吗？"
            )
        )
        zhihuList.add(
            HotItem(
                7,
                "https://www.zhihu.com/question/602087055",
                "111 万热度",
                "贫铀弹，是为了其硬度还是仅仅为了辐射杀伤？"
            )
        )
        zhihuList.add(
            HotItem(
                8,
                "https://www.zhihu.com/question/602229772",
                "106 万热度",
                "孕妇遭陌生男子猥亵，报警时被打致耳聋，嫌疑人已被刑拘，如何看待此事？"
            )
        )
        zhihuList.add(
            HotItem(
                9,
                "https://www.zhihu.com/question/602504870",
                "97 万热度",
                "尤文图斯案重审结果官宣，本赛季被扣除 10 分，尤文降至意甲第七，如何看待这个处罚结果？"
            )
        )
        zhihuList.add(
            HotItem(
                10,
                "https://www.zhihu.com/question/602332945",
                "89 万热度",
                "钟南山称 6 月底或是今年第二波疫情高峰，上班族如何做好防护工作？"
            )
        )
        zhihuList.add(
            HotItem(
                11,
                "https://www.zhihu.com/question/602330182",
                "82 万热度",
                "东部决赛第三场，热火 128 比 102 战胜凯尔特人，三比零领先，如何评价这场比赛？"
            )
        )
        zhihuList.add(
            HotItem(
                12,
                "https://www.zhihu.com/question/601843353",
                "80 万热度",
                "网易子公司正在开发《战锤》新作，你对此游戏有哪些期待?"
            )
        )
        zhihuList.add(
            HotItem(
                13,
                "https://www.zhihu.com/question/601725007",
                "76 万热度",
                "神舟十六号船箭组合体转运至发射区，计划近日择机实施发射，有何期待和祝福？"
            )
        )
        zhihuList.add(
            HotItem(
                14,
                "https://www.zhihu.com/question/600353143",
                "57 万热度",
                "同事不带饭，天天蹭我的，社恐人应该怎么拒绝？"
            )
        )
    }

    class ZhiHuAdapter(val zhihuList: List<HotItem>) :
        RecyclerView.Adapter<ZhiHuAdapter.ViewHolder>() {

        private var mOnItemClickListener: OnItemClickListener? = null

        interface OnItemClickListener {
            fun onItemClick(view: View?, position: Int)
        }

        fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
            mOnItemClickListener = onItemClickListener
        }


        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val zhihuNum: TextView = view.findViewById(R.id.zhihu_num)
            val zhihuText: TextView = view.findViewById(R.id.zhihu_text)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZhiHuAdapter.ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.zhihu_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ZhiHuAdapter.ViewHolder, position: Int) {
            val zhihu = zhihuList.get(position)
            holder.zhihuNum.text = (position + 1).toString()
            holder.zhihuText.text = zhihu.title

            if (position == 0) {
                holder.zhihuNum.setTextColor(Color.RED)
                holder.zhihuNum.setTypeface(Typeface.DEFAULT_BOLD)
            }
            if (position == 1) {
                holder.zhihuNum.setTextColor(Color.parseColor("#FF5722"))
                holder.zhihuNum.setTypeface(Typeface.DEFAULT_BOLD)
            }
            if (position == 2) {
                holder.zhihuNum.setTextColor(Color.parseColor("#FF9800"))
                holder.zhihuNum.setTypeface(Typeface.DEFAULT_BOLD)
            }

            holder.itemView.setOnClickListener {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener?.onItemClick(it, position)
                }
            }
        }

        override fun getItemCount(): Int {
            return zhihuList.size
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ZhiHuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ZhiHuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}