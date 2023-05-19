package com.example.newsday.home.view

import android.Manifest
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.MapsInitializer
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.amap.api.services.route.BusRouteResult
import com.amap.api.services.route.DriveRouteResult
import com.amap.api.services.route.RideRouteResult
import com.amap.api.services.route.RouteSearch
import com.amap.api.services.route.RouteSearch.FromAndTo
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener
import com.amap.api.services.route.RouteSearch.WalkRouteQuery
import com.amap.api.services.route.WalkPath
import com.amap.api.services.route.WalkRouteResult
import com.example.newsday.R
import com.example.newsday.databinding.FragmentMapsBinding
import com.example.newsday.home.db.MapMarkDate
import com.example.newsday.home.viewmodel.MapsViewModel
import com.example.newsday.util.AMapUtil
import com.example.newsday.util.ButtomDialog
import com.example.newsday.util.WalkRouteOverlay


class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var mapView: MapView
    private lateinit var topReturnView: TextView
    private lateinit var topPositionView: TextView
    private lateinit var topAddView: TextView


    private lateinit var viewModel: MapsViewModel

    private lateinit var aMap: AMap
    private var mLastLocation: Location? = null
    private var mLocationLatLng: LatLng? = null
    private var nearestMark: MapMarkDate? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        //显示地图
        aMap = mapView.map

        topReturnView = binding.topReturn
        topAddView = binding.topAddMark
        topPositionView = binding.topPosition

        initDate()
        initListener()
    }

    private fun initListener() {
        topReturnView.setOnClickListener {
            findNavController().popBackStack()
        }
        topPositionView.setOnClickListener {
            Toast.makeText(requireContext(), "已为您导航至最近的核算点！您还可以在地图上查看更多核算点", Toast.LENGTH_LONG).show()
            nearestMark?.let {
                initRouteSearch(it)
            }
        }
        topAddView.setOnClickListener {
            aMap.clear()
            val locationStyle = MyLocationStyle().apply {
                myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
                interval(2000)
                strokeWidth(0f)
                showMyLocation(true)
            }
            aMap.apply {
                myLocationStyle = locationStyle            //设置定位蓝点的Style
                isMyLocationEnabled = true                 // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
            }
            if(mLocationLatLng == null) {
                Toast.makeText(requireContext(), "定位失败", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val geocoderSearch = GeocodeSearch(requireContext())
            geocoderSearch.setOnGeocodeSearchListener(object : OnGeocodeSearchListener {
                override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {
                    //1000为成功
                    if(p1 == 1000) {
                        binding.addMarResultAddress.text = p0?.regeocodeAddress?.formatAddress
                    }
                }
                override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {}
            })
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            val query = RegeocodeQuery(LatLonPoint(mLocationLatLng!!.latitude,
                mLocationLatLng!!.longitude), 20f, GeocodeSearch.AMAP)
            geocoderSearch.getFromLocationAsyn(query)


            binding.addMarResult.visibility = View.VISIBLE
            binding.addMarResultSure.setOnClickListener {
                val name = binding.addMarResultEdit.text.trim().toString()
                if(name.isBlank()) {
                    Toast.makeText(requireContext(), "核算点名称不能为空", Toast.LENGTH_LONG).show()
                } else if (binding.addMarResultAddress.text.toString().isBlank()) {
                    Toast.makeText(requireContext(), "获取位置信息失败", Toast.LENGTH_LONG).show()
                } else {
                    val mapMarkDate = MapMarkDate(title = name, snippet = binding.addMarResultAddress.text.toString(),
                    lat = mLocationLatLng!!.latitude, lng = mLocationLatLng!!.longitude)
                    viewModel.addMark(mapMarkDate)
                    Toast.makeText(requireContext(), "添加成功，等待管理员审核", Toast.LENGTH_LONG).show()
                }
                defaultLocationSitting()
                binding.addMarResult.visibility = View.GONE
            }
            binding.addMarResultQuit.setOnClickListener {
                binding.addMarResult.visibility = View.GONE
                aMap.clear()
                viewModel.getAllMark()
                defaultLocationSitting()
            }
        }

        aMap.addOnMyLocationChangeListener {
            it ?: return@addOnMyLocationChangeListener
            if(mLastLocation == null) {
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(it.latitude, it.longitude)))
                aMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
            }
            mLastLocation = it
            mLocationLatLng = LatLng(it.latitude, it.longitude)
            Log.d(TAG, "addOnMyLocationChangeListener: ${it.latitude}    ${it.longitude}")
        }

        aMap.setOnMarkerClickListener { marker ->
            marker?: return@setOnMarkerClickListener false
            marker.showInfoWindow()

            var dialog: ButtomDialog? = null
            val builder: ButtomDialog.Builder = ButtomDialog.Builder(context)
            builder.addMenu("去这里") {
                initRouteSearch(MapMarkDate(title = marker.title, snippet = marker.snippet,
                    lat = marker.position.latitude, lng = marker.position.longitude))
                dialog?.cancel()
            }
            builder.setTitle("导航") //添加标题*/
            builder.setCanCancel(true) //点击阴影时是否取消dialog，true为取消
            builder.setShadow(true) //是否设置阴影背景，true为有阴影
            builder.setCancelText("取消") //设置最下面取消的文本内容

            builder.setCancelListener {
                dialog!!.cancel()
                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show()
            }
            dialog = builder.create()
            dialog?.show()
            return@setOnMarkerClickListener true
        }
    }

    private fun initDate() {
        needPermission()

        viewModel = ViewModelProvider(this)[MapsViewModel::class.java]
        viewModel.initCommendDetailDateBaseHelper(requireContext())
        viewModel.getAllMark()

        viewModel.markLiveData.observe(viewLifecycleOwner) {
            it?: return@observe
            var nearestDistance: Float = Float.MAX_VALUE
            it.forEach { markDate ->
                val distance = AMapUtils.calculateLineDistance(mLocationLatLng, LatLng(markDate.lat, markDate.lng))
                if(AMapUtils.calculateLineDistance(mLocationLatLng, LatLng(markDate.lat, markDate.lng))
                    < nearestDistance) {
                    nearestDistance = distance
                    nearestMark =  markDate
                }
                addMarker(markDate)
            }
        }
        viewModel.addMarkLiveData.observe(viewLifecycleOwner) {
            it?: return@observe
            Toast.makeText(requireContext(),"你添加的核算点信息审核通过了，快去看看把！", Toast.LENGTH_LONG).show()
            aMap.clear()
            viewModel.getAllMark()
        }

    }

    private fun needPermission() {
        val permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
                if (!granted.values.all { it }) {
                    Toast.makeText(requireContext(), "位置信息获取失败，请打开相关权限", Toast.LENGTH_LONG)
                        .show()
                    findNavController().popBackStack()
                } else {
                    initLocation()
                }
            }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun initLocation() {
        // 隐私合规
        MapsInitializer.updatePrivacyShow(activity, true, true)
        MapsInitializer.updatePrivacyAgree(activity, true)
        defaultLocationSitting()
    }

    private fun defaultLocationSitting() {
        //定位设置
        val locationStyle = MyLocationStyle().apply {
            myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
            interval(2000)           //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            strokeWidth(0f)
            showMyLocation(true)
        }
        //显示定位蓝点
        aMap.apply {
            myLocationStyle = locationStyle            //设置定位蓝点的Style
            uiSettings.isMyLocationButtonEnabled = true //设置默认定位按钮是否显示，非必需设置
            isMyLocationEnabled = true                  // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        }
    }

    private fun addMarker(mapMarkDate: MapMarkDate) {
        val markerOption = MarkerOptions()
            .position(LatLng(mapMarkDate.lat, mapMarkDate.lng))
            .title(mapMarkDate.title)
            .snippet(mapMarkDate.snippet)

        markerOption.isFlat = true
        aMap.addMarker(markerOption)
        //marker.showInfoWindow()
    }

    private fun initRouteSearch(endLocation: MapMarkDate) {
        val routeSearch = RouteSearch(requireContext())
        if(mLocationLatLng?.latitude != null && mLocationLatLng?.longitude != null) {
            val fromAndTo = FromAndTo(
                LatLonPoint(mLocationLatLng!!.latitude, mLocationLatLng!!.longitude),
                LatLonPoint(endLocation.lat, endLocation.lng))
            val query = WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault)
            routeSearch.calculateWalkRouteAsyn(query)
        } else {
            Toast.makeText(requireContext(), "获取位置信息失败", Toast.LENGTH_LONG).show()
        }


        routeSearch.setRouteSearchListener(object : OnRouteSearchListener {
            override fun onBusRouteSearched(p0: BusRouteResult?, p1: Int) {}

            override fun onDriveRouteSearched(p0: DriveRouteResult?, p1: Int) {}

            override fun onWalkRouteSearched(result: WalkRouteResult?, errorCode: Int) {
                aMap.clear() // 清理地图上的所有覆盖物
                if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result?.paths != null) {
                        if (result.paths.size > 0) {
                            val walkPath: WalkPath = result.paths[0] ?: return
                            val walkRouteOverlay = WalkRouteOverlay(
                                requireContext(), aMap, walkPath,
                                result.startPos,
                                result.targetPos
                            )
                            walkRouteOverlay.removeFromMap()
                            walkRouteOverlay.addToMap()
                            walkRouteOverlay.zoomToSpan()

                            val dis = walkPath.distance.toInt()
                            val dur = walkPath.duration.toInt()
                            val des: String =
                                (AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(
                                    dis
                                )) + ")"

                            binding.routeResult.visibility = View.VISIBLE
                            binding.routeResultTitle.text = endLocation.title
                            binding.routeResultLocation.text = des
                            binding.routeResultQuit.setOnClickListener {
                                binding.routeResult.visibility = View.GONE
                                aMap.clear()
                                viewModel.getAllMark()
                            }

                        }
                    }
                }
            }
            override fun onRideRouteSearched(p0: RideRouteResult?, p1: Int) {}
        })

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    companion object {
        private const val TAG = "MapsFragment"
    }


}