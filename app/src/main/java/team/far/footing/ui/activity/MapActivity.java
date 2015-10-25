package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.ui.widget.RevealBackgroundView;

public class MapActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.map_reveal_background_view)
    RevealBackgroundView mRevealBackground;
    @Bind(R.id.map_view)
    MapView mMapView;

    private AMap mAmap;

    public static void startMapActivityFromLocation(int[] startLocation, Activity homeActivity) {
        Intent intent = new Intent(homeActivity, MapActivity.class);
        intent.putExtra(HomeActivity.ARG_REVEAL_START_LOCATION, startLocation);
        homeActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setupToolbar();
        setupRevealBackground(savedInstanceState);
        setupMap(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


    private void setupToolbar() {
        if(mToolbar != null){
            mToolbar.setTitle(getResources().getString(R.string.app_name));
            setSupportActionBar(mToolbar);
        }
    }

    private void setupMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }

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

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            mMapView.setVisibility(View.VISIBLE);
            mAmap = mMapView.getMap();
        } else {
            mMapView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


}
