package com.example.newsday.search.view

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
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.newsday.R
import com.example.newsday.databinding.FragmentGarbageClassificationBinding
import com.example.newsday.search.db.GarbageRecognitionBean
import com.example.newsday.search.viewmodel.GarbageResultViewModel
import com.example.newsday.util.ButtomDialog
import com.example.newsday.util.CameraUtils
import com.example.newsday.util.ImageUtil

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GarbageClassificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GarbageClassificationFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: GarbageResultViewModel
    private var _binding: FragmentGarbageClassificationBinding? = null
    private val binding get() = _binding!!

    private var dialog: ButtomDialog? = null

    /**
     * startActivityForResult()方法废弃,google更加建议使用Activity Result API来实现在两个Activity之间交换数据的功能。
     * 内置Contract:更简单实现权限申请,拍照，打开文件等
     * 参考：https://blog.csdn.net/guolin_blog/article/details/121063078?spm=1001.2014.3001.5501
     */
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
                Log.d(TAG, "choosePhotoLauncher: ${bitmap?.byteCount ?: 0}")
                bitmap ?: return@registerForActivityResult
                val imageView = binding.testImage
                Glide.with(requireActivity()).load(bitmap).into(imageView)
                viewModel.getGarbageTypeByBase64Image(ImageUtil.base64Encode(bitmap))
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            Log.d(TAG, "takePictureLauncher: ${bitmap?.byteCount ?: 0}")
            bitmap ?: return@registerForActivityResult
            viewModel.getGarbageTypeByBase64Image(ImageUtil.base64Encode(bitmap))
        }


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
        // Inflate the layout for this fragment
        _binding = FragmentGarbageClassificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GarbageResultViewModel::class.java]
        initView()
        initData()
    }

    private fun initView() {
        binding.text1.setOnClickListener {
            setAllText(binding.text1.text.toString().trim())
        }
        binding.text2.setOnClickListener {
            setAllText(binding.text2.text.toString().trim())
        }
        binding.text3.setOnClickListener {
            setAllText(binding.text3.text.toString().trim())
        }
        binding.text4.setOnClickListener {
            setAllText(binding.text4.text.toString().trim())
        }
        binding.text5.setOnClickListener {
            setAllText(binding.text5.text.toString().trim())
        }
        binding.text6.setOnClickListener {
            setAllText(binding.text6.text.toString().trim())
        }
        binding.text7.setOnClickListener {
            setAllText(binding.text7.text.toString().trim())
        }

        //相机按钮设置点击事件
        binding.searchCamera.setOnClickListener {
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

        //搜索按钮设置点击事件
        binding.searchItem.setOnClickListener {
            val word = binding.searchEdit.text.toString().trim()
            if (word.isNotBlank()) {
                viewModel.getGarbageTypeByText(word)
            }
        }
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

    private fun setAllText(text: String?) {
        if (text.isNullOrBlank()) return
        binding.searchEdit.setText(text)
        viewModel.getGarbageTypeByText(text)
    }


    private fun initData() {
        viewModel.garbageText.observe(viewLifecycleOwner) {
            Log.d(TAG, "garbageText: $it")
            val bundle = bundleOf("type" to TYPE_TEXT, "item" to it.result.list)
            dateList  = it
            findNavController().navigate(
                R.id.action_SearchResultFragment_to_garbageResultFragment,
                bundle
            )
        }
        viewModel.garbageImage.observe(viewLifecycleOwner) {
            Log.d(TAG, "garbageImage: $it")
            val bundle = bundleOf("type" to TYPE_IMAGE)
            dateList = it
            findNavController().navigate(
                R.id.action_SearchResultFragment_to_garbageResultFragment,
                bundle
            )
        }
    }


    companion object {
        const val TYPE_TEXT: Int = 0
        const val TYPE_IMAGE: Int = 1
        const val TYPE_VIDEO: Int = 2

        @JvmStatic
        fun newInstance() = GarbageClassificationFragment()
        const val TAG = "IficationFragment"
        var dateList: GarbageRecognitionBean? = null
    }
}