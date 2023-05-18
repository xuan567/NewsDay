package com.example.newsday.my

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.newsday.R
import com.example.newsday.databinding.FragmentMyBinding
import com.example.newsday.home.db.ListItem
import com.example.newsday.home.view.HomeFragmentRecommend
import com.example.newsday.home.view.RecommendDetailFragment
import com.example.newsday.search.view.GarbageClassificationFragment
import com.example.newsday.util.ButtomDialog
import com.example.newsday.util.CameraUtils
import com.example.newsday.util.ImageUtil
import de.hdodenhof.circleimageview.CircleImageView

class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding

    private lateinit var viewModel: MyViewModel
    private lateinit var circleImageView: CircleImageView
    private lateinit var myAdapter: HomeFragmentRecommend.RecommendAdapter

    private var date: MutableList<ListItem> = mutableListOf()
    private var dialog: ButtomDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initDate()
    }
    private fun initView() {
        circleImageView = binding.circleImageView
        val layoutManager = LinearLayoutManager(context)
        myAdapter = HomeFragmentRecommend.RecommendAdapter(requireContext(), date)
        binding.myRecycler.apply {
            this.layoutManager = layoutManager
            adapter = myAdapter
        }
    }

    private fun initListener() {
        circleImageView.setOnClickListener {
            val builder: ButtomDialog.Builder = ButtomDialog.Builder(context)
            //添加条目，可多个
            builder.addMenu("相机") {
                dialog?.cancel()
                takePhotos()
            }.addMenu("相册") {
                dialog?.cancel()
                choosePhoto()
            }
            builder.setTitle("请选择方式") //添加标题
            builder.setCanCancel(true) //点击阴影时是否取消dialog，true为取消
            builder.setShadow(true) //是否设置阴影背景，true为有阴影
            builder.setCancelText("取消") //设置最下面取消的文本内容

            builder.setCancelListener {
                dialog!!.cancel()
                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show()
            }
            dialog = builder.create()
            dialog?.show()
        }

        myAdapter.setOnItemClickListener(object : HomeFragmentRecommend.RecommendAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val detail = date[position]
                findNavController().navigate(R.id.action_navigation_my_to_recommendDetailFragment,  Bundle().apply {
                    putString(RecommendDetailFragment.ARG_PIC,detail.pic)
                    putString(RecommendDetailFragment.ARG_TITLE,detail.title)
                    putString(RecommendDetailFragment.ARG_CONTENT,detail.content)
                    putString(RecommendDetailFragment.ARG_SRC,detail.src)
                    putString(RecommendDetailFragment.ARG_TIME,detail.time)
                })
            }

        })


    }

    private val choosePhotoRequestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                openAlbum()
            } else {
                Toast.makeText(requireContext(), "获取权限失败，请打开相关权限", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private val choosePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap =
                    result.data?.let { CameraUtils.getImageBitMapApi29Down(it, requireContext()) }
                Log.d(GarbageClassificationFragment.TAG, "choosePhotoLauncher: ${bitmap?.byteCount ?: 0}")
                bitmap ?: return@registerForActivityResult
                circleImageView.load(bitmap)
                viewModel.upDateMyImage(bitmap)
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            Log.d(GarbageClassificationFragment.TAG, "takePictureLauncher: ${bitmap?.byteCount ?: 0}")
            bitmap ?: return@registerForActivityResult
            circleImageView.load(bitmap)
            viewModel.upDateMyImage(bitmap)
        }

    private fun takePhotos() {
        takePictureLauncher.launch(null)
    }

    private fun choosePhoto() {
        choosePhotoRequestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun openAlbum() {
        choosePhotoLauncher.launch(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        )
    }

    private fun initDate() {
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.initCommendDetailDateBaseHelper(requireContext())
        viewModel.getAllLike()
        viewModel.getMyImage()


        viewModel.likedDateLiveDate.observe(viewLifecycleOwner) {
            it ?: return@observe
            date = it.toMutableList()
            myAdapter.recommendList.clear()
            myAdapter.recommendList.addAll(date)
            myAdapter.notifyDataSetChanged()
        }

        viewModel.myImageLiveDate.observe(viewLifecycleOwner) {
            it ?: return@observe
            circleImageView.load(it)
        }
    }
    companion object {
        const val SP_MY_IMAGE = "myImage"
    }
}