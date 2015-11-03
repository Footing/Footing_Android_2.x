package team.far.footing.presenter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import team.far.footing.model.IFriendModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.adaper.FriendsRyViewAdapter;
import team.far.footing.ui.vu.IFriendVu;
import team.far.footing.uitl.LogUtils;

/**
 * Created by luoyy on 2015/11/3 0003.
 */
public class FriendsPresenter {

    private IFriendVu mFriendVu;
    private IUserModel mUserModel;
    private IFriendModel mFriendModel;

    private FriendsRyViewAdapter adapter;


    public FriendsPresenter(IFriendVu friendVu) {
        mFriendVu = friendVu;
        mUserModel = UserModel.getInstance();
        mFriendModel = FriendModel.getInstance();
        showFriends();
    }


    private void showFriends() {

        LogUtils.e("我再这里走不了了");
        mFriendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                if (mFriendVu != null) {
                    if (object.size() != 0) {
                        adapter = new FriendsRyViewAdapter(object);
                        mFriendVu.showfriends(adapter);
                    } else {
                        mFriendVu.showEmpty();
                    }
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });

    }

    public void refresh() {
        showFriends();
    }
}
