package team.far.footing.ui.adaper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.ui.activity.FriendsActivity;
import team.far.footing.ui.activity.LoginActivity;
import team.far.footing.uitl.BmobUtils;

/**
 * 主界面recyclerView的Adapter
 *
 * @author stormouble
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    /**
     * 未登陆时,显示的login区域
     */
    public static final int TYPE_LOGIN = 0;

    /**
     * 功能区域
     */
    public static final int TYPE_FUNCTION = 1;

    /**
     * 最近两个字
     */
    public static final int TYPE_RECENTLY_TEXT = 2;

    /**
     * 最近列表
     */
    public static final int TYPE_RECENTLY = 3;
    @Bind(R.id.home_iv_broadcast)
    ImageView homeIvBroadcast;
    @Bind(R.id.home_view_circle)
    View homeViewCircle;
    @Bind(R.id.home_iv_friends)
    ImageView homeIvFriends;
    @Bind(R.id.home_iv_rooms)
    ImageView homeIvRooms;
    @Bind(R.id.home_layout_function)
    LinearLayout homeLayoutFunction;

    private Context mContext;
    private HomeFunctionViewHolder holder;

    /**
     * 用户是否登陆
     */
    private boolean isLogin = false;

    private View mRecentlyView;

    public HomeAdapter(Context context) {
        mContext = context;

        if (BmobUtils.getCurrentUser() != null) {
            isLogin = true;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!isLogin) {
            if (position == 0) {
                return TYPE_LOGIN;
            } else if (position == 1) {
                return TYPE_FUNCTION;
            } else if (position == 2) {
                return TYPE_RECENTLY_TEXT;
            } else {
                return TYPE_RECENTLY;
            }
        } else {
            if (position == 0) {
                return TYPE_FUNCTION;
            } else if (position == 1) {
                return TYPE_RECENTLY_TEXT;
            } else {
                return TYPE_RECENTLY;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_LOGIN) {
            final View view = inflater.inflate(R.layout.layout_home_header_login, parent, false);
            HomeLoginViewHolder holder = new HomeLoginViewHolder(view);
            holder.view.setOnClickListener(this);
            return holder;
        } else if (viewType == TYPE_FUNCTION) {
            final View view = inflater.inflate(R.layout.layout_home_header_function, parent, false);
            holder = new HomeFunctionViewHolder(view);
            holder.mIvBroadcast.setOnClickListener(this);
            holder.mIvFriends.setOnClickListener(this);
            holder.miVRooms.setOnClickListener(this);
            return holder;
        } else if (viewType == TYPE_RECENTLY_TEXT) {
            final View view = inflater.inflate(R.layout.layout_home_recently_text, parent, false);
            return new HomeRecentlyTextViewHolder(view);
        } else {
            mRecentlyView = inflater.inflate(R.layout.item_recently, parent, false);
            mRecentlyView.setOnClickListener(this);
            return new HomeRecentlyViewHolder(mRecentlyView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemType = holder.getItemViewType();
        if (itemType == TYPE_RECENTLY) {
            bindRecentlyHolder((HomeRecentlyViewHolder) holder, position - 2);
        }
    }


    private void bindRecentlyHolder(HomeRecentlyViewHolder holder, int position) {
        // TODO 在这写死数据的给我出来，我不打死你
        if (position % 2 == 0) {
            holder.mIvRecently.setImageResource(R.mipmap.ic_home_list_draw);
            holder.mTvText.setText("重邮新校门到二教");
        } else if (position % 3 == 0) {
            holder.mIvRecently.setImageResource(R.mipmap.ic_home_list_position);
            holder.mTvText.setText("我在老操场");
        } else {
            holder.mIvRecently.setImageResource(R.mipmap.ic_home_list_walk);
            holder.mTvText.setText("重邮之行");
        }
    }

    /**
     * 3是TYPE_LOGIN + TYPE_FUNCTION + TYPE_RECENTLY_TEXT
     */
    @Override
    public int getItemCount() {
        // TODO 在这写死数据的给我出来，我不打死你
        if (!isLogin) {
            return 3 + 9;
        } else {
            return 2 + 9;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (v == mRecentlyView) {
            Logger.d("mRecently has clicked");
        }

        switch (id) {
            case R.id.home_lin_header:
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.home_iv_friends:
                mContext.startActivity(new Intent(mContext, FriendsActivity.class));
                break;
            case R.id.home_iv_broadcast:
                holder.mCircleView.setVisibility(View.GONE);
                //点击查看新消息

                break;
            case R.id.home_iv_rooms:
                break;
        }

    }


    public void showNewMsg() {
        holder.mCircleView.setVisibility(View.VISIBLE);
    }

    class HomeLoginViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.home_iv_login)
        ImageView mIvLogin;
        @Bind(R.id.home_lin_header)
        LinearLayout view;

        public HomeLoginViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class HomeFunctionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.home_iv_broadcast)
        ImageView mIvBroadcast;
        @Bind(R.id.home_iv_friends)
        ImageView mIvFriends;
        @Bind(R.id.home_iv_rooms)
        ImageView miVRooms;
        @Bind(R.id.home_view_circle)
        View mCircleView;

        public HomeFunctionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeRecentlyTextViewHolder extends RecyclerView.ViewHolder {

        public HomeRecentlyTextViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HomeRecentlyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_home_iv_recently)
        ImageView mIvRecently;
        @Bind(R.id.item_home_tv_text)
        TextView mTvText;

        public HomeRecentlyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
