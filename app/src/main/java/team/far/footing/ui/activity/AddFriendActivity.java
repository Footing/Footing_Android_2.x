package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.a.a.This;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.FriendsPresenter;
import team.far.footing.ui.vu.IFriendVu;
import team.far.footing.ui.widget.DividerItemDecoration;

public class AddFriendActivity extends BaseActivity implements IFriendVu, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.edit_addfriend)
    EditText mEditText;
    @Bind(R.id.bt_query)
    ImageView btQuery;
    @Bind(R.id.ll_add_search_layout)
    LinearLayout llAddSearchLayout;
    @Bind(R.id.firends_recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;

    private FriendsPresenter mFriendsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        init();
        mFriendsPresenter = new FriendsPresenter(this, 0);

    }

    private void init() {
        setSupportActionBar(mToolbar);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(APP.getContext()));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(APP.getContext(), LinearLayoutManager.VERTICAL));
        swipeRefreshWidget.setOnRefreshListener(this);
        swipeRefreshWidget.setColorSchemeResources(R.color.cardview_shadow_end_color, R.color.primary_color);

        btQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFriendsPresenter.query(mEditText.getText().toString());
            }
        });
    }

    @Override
    public void showfriends(RecyclerView.Adapter adapter) {
        swipeRefreshWidget.setVisibility(View.VISIBLE);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void stopRefresh() {
        swipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        swipeRefreshWidget.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        mFriendsPresenter.refresh();
    }
}
