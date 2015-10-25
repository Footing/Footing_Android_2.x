package team.far.footing.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.ui.activity.HomeActivity;
import team.far.footing.ui.activity.MapActivity;
import team.far.footing.ui.adaper.HomeAdapter;
import team.far.footing.uitl.DensityUtils;
import team.far.footing.uitl.LogUtils;

/**
 * 主界面的内容区域
 *
 * @author stormouble
 */
public class HomeFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    private static final int ANIM_DURATION_FAB = 400;
    private static final int ANIM_DURATION_TOOLBAR = 300;

    @Bind(R.id.home_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.home_toolbar_tv_title)
    TextView mTvToolbarTitle;
    @Bind(R.id.home_rfab_layout)
    RapidFloatingActionLayout mRfabLayout;
    @Bind(R.id.home_rfab)
    RapidFloatingActionButton mRfabButton;
    @Bind(R.id.home_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.home_recycler_view)
    RecyclerView mRecyclerView;

    private RapidFloatingActionHelper mRfabHelper;
    private HomeAdapter mHomeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        setupRfab();
        setupRefresh();
        setupRecyclerView();
    }

    private void setupToolbar() {
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            mTvToolbarTitle.setText(getResources().getString(R.string.app_name));
        }
    }

    private void setupRfab() {
        RapidFloatingActionContentLabelList rfabContent = new RapidFloatingActionContentLabelList(getActivity());
        rfabContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel(getResources().getString(R.string.home_fab_search))
                .setResId(R.mipmap.ic_rfab_search)
                .setIconNormalColor(getResources().getColor(R.color.home_fab_item_search))
                .setIconPressedColor(getResources().getColor(R.color.home_fab_item_dark_search))
                .setLabelColor(getResources().getColor(R.color.secondary_text))
                .setWrapper(3));
        items.add(new RFACLabelItem<Integer>()
                .setLabel(getResources().getString(R.string.home_fab_draw))
                .setResId(R.mipmap.ic_rfab_draw)
                .setIconNormalColor(getResources().getColor(R.color.home_fab_item_draw))
                .setIconPressedColor(getResources().getColor(R.color.home_fab_item_dark_draw))
                .setLabelColor(getResources().getColor(R.color.secondary_text))
                .setWrapper(3));
        items.add(new RFACLabelItem<Integer>()
                .setLabel(getResources().getString(R.string.home_fab_walk))
                .setResId(R.mipmap.ic_rfab_walk)
                .setIconNormalColor(getResources().getColor(R.color.home_fab_item_walk))
                .setIconPressedColor(getResources().getColor(R.color.home_fab_item_dark_walk))
                .setLabelColor(getResources().getColor(R.color.secondary_text))
                .setWrapper(3));
        rfabContent
                .setItems(items)
                .setIconShadowRadius(DensityUtils.dp2px(getActivity(), 4))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(DensityUtils.dp2px(getActivity(), 2));

        mRfabHelper = new RapidFloatingActionHelper(
                getActivity(),
                mRfabLayout,
                mRfabButton,
                rfabContent
        ).build();
    }

    private void setupRefresh() {
        //TODO 刷新逻辑
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //简单让刷新停止
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1800);
            }
        });
    }

    private void onRfabClicked(int position) {
        final Activity homeActivity = getActivity();
        final int[] startingLocation = new int[2];
        mRfabButton.getLocationOnScreen(startingLocation);
        startingLocation[0] += mRfabButton.getWidth() / 2;
        if (position == 0) {

        } else if (position == 1) {
            MapActivity.startMapActivityFromLocation(HomeActivity.MAP_DRAW,startingLocation, homeActivity);
            homeActivity.overridePendingTransition(0, 0);
        } else {
            MapActivity.startMapActivityFromLocation(HomeActivity.MAP_WALK,startingLocation, homeActivity);
            homeActivity.overridePendingTransition(0, 0);
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mHomeAdapter = new HomeAdapter(getActivity());
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    public void startIntroAnimation(View menuUserView) {
        int actionbarSize = DensityUtils.dp2px(getActivity(),56);

        mRfabButton.setTranslationY(2 * DensityUtils.dp2px(getActivity(),56));

        mToolbar.setTranslationY(-actionbarSize);
        mTvToolbarTitle.setTranslationY(-actionbarSize);
        menuUserView.setTranslationY(-actionbarSize);

        mToolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        mTvToolbarTitle.animate()
                .translationX(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        menuUserView.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startRfabAnimation();
                    }
                })
                .start();
    }

    public void startRfabAnimation() {
        mRfabButton.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem rfacLabelItem) {
        onRfabClicked(position);
        mRfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem rfacLabelItem) {
        onRfabClicked(position);
        mRfabHelper.toggleContent();
    }
}
