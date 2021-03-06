package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.app.BaseActivity;
import team.far.footing.ui.fragment.HomeFragment;
import team.far.footing.ui.widget.RevealBackgroundView;
import team.far.footing.uitl.LogUtils;
import team.far.footing.uitl.ScreenUtils;

public class MapActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener,
        LocationSource, AMapLocationListener, SensorEventListener {
    private static final int TIME_SENSOR = 100;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.map_reveal_background_view)
    RevealBackgroundView mRevealBackground;
    @Bind(R.id.map_map_view)
    MapView mMapView;

    private AMap mAMap;

    private OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
    private Marker mMarker;

    private SensorManager mSensorManager;
    private Sensor mSensor;


    /**上次方向改变时的时间*/
    private long mLastTime = 0;

    /**旋转角*/
    private float mAngle;

    /**操作的方式*/
    private String mActionType;

    /**是否为行走*/
    private boolean mIsFollowed;

    /**上次记录的点*/
    private Point mPoint;

    /**重新计算的中心LatLng*/
    private LatLng mLatLng;

    /**绘画PolylineOptions*/
    private PolylineOptions mPolylineOptions;

    /**绘图所需要的List*/
    private List<LatLng> mLaLngList;

    /**头LatLng*/
    private LatLng headerLaLng;

    /**
     * 打开MapActivity.
     *
     * @param startLocation 点击位置
     * @param homeActivity  homeActivity
     */
    public static void startMapActivityFromLocation(String actionType, int[] startLocation, Activity homeActivity) {
        Intent intent = new Intent(homeActivity, MapActivity.class);
        intent.putExtra(HomeActivity.ARG_REVEAL_START_LOCATION, startLocation);
        intent.putExtra(HomeActivity.MAP_ACTION_TYPE, actionType);
        homeActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mActionType = getIntent().getStringExtra(HomeActivity.MAP_ACTION_TYPE);
        setupToolbar();
        setupRevealBackground(savedInstanceState);
        setupMap(savedInstanceState);
    }

    private void setupToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle(getResources().getString(R.string.app_name));
            setSupportActionBar(mToolbar);
        }
    }

    /**
     * 设置跳转背景view.
     */
    private void setupRevealBackground(Bundle savedInstanceState) {
        mRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(HomeActivity.ARG_REVEAL_START_LOCATION);
            mRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    mRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            mRevealBackground.setToFinishedFrame();
        }
    }

    /**
     * 根据跳转背景view是否可见,显示地图.
     *
     * @param state 背景view的状态
     */
    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            mMapView.setVisibility(View.VISIBLE);
        } else {
            mMapView.setVisibility(View.GONE);
        }
    }

    private void setupMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();

        //小蓝点
        ArrayList<BitmapDescriptor> gifList = new ArrayList<>();
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point1));
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point2));
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point3));
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point4));
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point5));
        gifList.add(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point6));
        mMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .icons(gifList).period(50));
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_location_marker));// 设置小蓝点的图标
        // 设置圆形的边框颜色
        myLocationStyle.strokeColor(getResources().getColor(R.color.map_circle_border));
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.map_circle_fill));
        myLocationStyle.strokeWidth(0.1f); // 设置圆形的边框粗细
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.getUiSettings().setScaleControlsEnabled(true);//设置默认缩放比例是否显示
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.setMyLocationEnabled(true);

        //设置方向传感器
        setupSensor();

        //设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        //根据主界面点击的按钮不同进行不同的地图操作:绘制或者行走
        if (mActionType.equals(HomeActivity.MAP_DRAW)) {
            setupMapDraw();
            mIsFollowed = false;
        } else if(mActionType.equals(HomeActivity.MAP_WALK)){
            setupMapFollow();
            mIsFollowed = true;
        }

    }

    /**
     * 地图绘制设置
     */
    private void setupMapDraw() {
        mAMap.setMapType(AMap.LOCATION_TYPE_LOCATE);
        //禁止手势移动
        mAMap.getUiSettings().setScrollGesturesEnabled(false);

        mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        mAMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                //只有一个手指
                if (motionEvent.getPointerCount() == 1) {
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                           /* LatLng Li =
                                    mAMap.getProjection().fromScreenLocation(new Point((int) motionEvent.getX(), (int) motionEvent.getY()));
                            draw(Li);
                            */
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            LogUtils.e("正在移动");
                            LatLng moveLa =
                                    mAMap.getProjection().fromScreenLocation(new Point((int) motionEvent.getX(), (int) motionEvent.getY()));
                            draw(moveLa);
                            break;
                    }
                }
                //有两个手指
                else if (motionEvent.getPointerCount() == 2) {
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_POINTER_DOWN:
                            mPoint = new Point((int) motionEvent.getX(1), (int) motionEvent.getY(1));
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Point move_point = new Point((int) motionEvent.getX(1), (int) motionEvent.getY(1));
                            CameraPosition cameraPosition = mAMap.getCameraPosition();
                            /*
                            double x = cameraPosition.target.longitude;
                            double y = cameraPosition.target.latitude;
                            */
                            Point centerPoint = mAMap.getProjection().toScreenLocation(cameraPosition.target);

                            int x = centerPoint.x;
                            int y = centerPoint.y;

                            mLatLng = mAMap.getProjection().fromScreenLocation(
                                    new Point(x + (mPoint.x - move_point.x), y + (mPoint.y - move_point.y)));
                            LogUtils.e("移动到了---->>>  经度：" + mLatLng.longitude + "维度：" + mLatLng.latitude);
                            float zoom = cameraPosition.zoom;
                            float bearing = cameraPosition.bearing;
                            mAMap.animateCamera(CameraUpdateFactory.newCameraPosition
                                    (CameraPosition.builder(new CameraPosition(mLatLng, zoom, 0, bearing))
                                            .build()));
                            break;
                    }
                }
                //不晓得有好多手指
                else {
                    LogUtils.e("你是在搞啥子哟");
                }
            }
        });

    }

    /**
     * 地图行走设置
     */
    private void setupMapFollow() {
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);//跟随
    }

    /**
     * 根据LatLng进行地图绘制
     */
    private void draw(LatLng latLng) {
        if (mPolylineOptions == null) {
            mPolylineOptions = new PolylineOptions();
            mLaLngList = new ArrayList<>();
            mLaLngList.add(latLng);
            mPolylineOptions.geodesic(true);
            mPolylineOptions.width(12);
            return;
        }
        mLaLngList.add(latLng);
        mPolylineOptions.add(latLng);
        mAMap.addPolyline(mPolylineOptions);
    }



    public List<LatLng> getArrayLocations() {
        if (mLaLngList == null || mLaLngList.size() == 0) return null;
        return mLaLngList;
    }

    /**
     * 方向传感器
     */
    private void setupSensor() {
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    /**
     * 加载地图必须重写的方法
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 加载地图必须重写的方法
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        registerSensorListener();
    }

    /**
     * 加载地图必须重写的方法
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }

    /**
     * 加载地图必须重写的方法
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this);
            /*
             * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location;
			 * API定位采用GPS和网络混合定位方式;
			 * 第一个参数是定位provider，第二个参数时间最短是2000毫秒，如果是-1则只定位一次,
			 * 第三个参数距离间隔单位是米，第四个参数是定位监听者.
			 */
            int requestInterval = -1;
            if (mIsFollowed) {
                requestInterval = 2000;
            }
            mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, requestInterval, 10, this);
        }
    }

    /**
     * 关闭定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destroy();
        }
        mAMapLocationManager = null;
        unRegisterSensorListener();
    }

    /**
     * 定位成功后回调函数.
     */
    @Override
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null && aLocation != null) {
            mListener.onLocationChanged(aLocation);// 显示系统小蓝点
            mMarker.setPosition(new LatLng(aLocation.getLatitude(), aLocation
                    .getLongitude()));// 定位雷达小图标
            float bearing = mAMap.getCameraPosition().bearing;
            mAMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
        }
    }

    /**
     * 回调函数处理逻辑.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (System.currentTimeMillis() - mLastTime < TIME_SENSOR) {
            return;
        }
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION: {
                float x = event.values[0];

                x += getScreenRotationOnPhone(this);
                x %= 360.0F;
                if (x > 180.0F)
                    x -= 360.0F;
                else if (x < -180.0F)
                    x += 360.0F;
                if (Math.abs(mAngle - x) < 5.0f) {
                    break;
                }
                mAngle = x;

                if (mAMap != null) {
                    mAMap.setMyLocationRotateAngle(-mAngle);
                }
                mLastTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void registerSensorListener() {
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unRegisterSensorListener() {
        mSensorManager.unregisterListener(this, mSensor);
    }

    /**
     * 方法已被废弃
     */
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    /**
     * 获取当前屏幕旋转角度.
     *
     * @param context 上下文
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    public static int getScreenRotationOnPhone(Context context) {
        final Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;

            case Surface.ROTATION_90:
                return 90;

            case Surface.ROTATION_180:
                return 180;

            case Surface.ROTATION_270:
                return -90;
        }
        return 0;
    }
}
