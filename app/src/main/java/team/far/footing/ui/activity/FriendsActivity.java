package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.FriendsPresenter;
import team.far.footing.ui.vu.IFriendVu;
import team.far.footing.ui.widget.DividerItemDecoration;

public class FriendsActivity extends BaseActivity implements IFriendVu, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.firends_recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.firends_recycler_view_empty)
    TextView mTextView;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private FriendsPresenter friendsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);
        init();

        friendsPresenter = new FriendsPresenter(this);

    }

    private void init() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(APP.getContext()));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(APP.getContext(), LinearLayoutManager.VERTICAL));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        setSupportActionBar(mToolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void showfriends(RecyclerView.Adapter adapter) {
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        // 必须去掉、否则下拉刷新卡顿
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mRecyclerview.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        friendsPresenter.refresh();
    }
}
