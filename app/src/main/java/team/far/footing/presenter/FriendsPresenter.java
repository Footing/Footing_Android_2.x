package team.far.footing.presenter;

import java.util.List;

import team.far.footing.model.IFriendModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.adaper.FriendsRyViewAdapter;
import team.far.footing.ui.vu.IFriendVu;

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

    public FriendsPresenter(IFriendVu friendVu, int a) {
        mFriendVu = friendVu;
        mUserModel = UserModel.getInstance();
        mFriendModel = FriendModel.getInstance();
    }

    private void showFriends() {

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

    public void query(final String s) {

        mFriendModel.queryUserById(s, new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                if (object.size() == 0) {

                    mFriendModel.queryUserByName(s, new OnQueryFriendListener() {
                        @Override
                        public void onSuccess(List<UserInfo> object) {
                            adapter = new FriendsRyViewAdapter(object);
                            if (mFriendVu != null) mFriendVu.showfriends(adapter);
                        }

                        @Override
                        public void onError(int code, String msg) {

                        }
                    });

                } else {
                    adapter = new FriendsRyViewAdapter(object);
                    if (mFriendVu != null) mFriendVu.showfriends(adapter);
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });

    }

    private void showAllUser() {
        mFriendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                adapter = new FriendsRyViewAdapter(object);
                if (mFriendVu != null) mFriendVu.showfriends(adapter);
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
