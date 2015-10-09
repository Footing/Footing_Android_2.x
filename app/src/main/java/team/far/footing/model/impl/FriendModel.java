package team.far.footing.model.impl;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import team.far.footing.app.APP;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.bean.Friends;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.callback.OnIsMyFriendListener;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.uitl.LogUtils;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public class FriendModel implements IFriendModel {

    public static final FriendModel instance = new FriendModel();

    final public static FriendModel getInstance() {
        return instance;
    }

    private FriendModel() {


    }


    @Override
    public void addFriend(UserInfo userInfo, final OnUserListener onUserListener) {


    }
    @Override
    public void deleteFriend(UserInfo userInfo, final OnUserListener onUserListener) {

    }

    @Override
    public void getAllFriends(final OnQueryFriendListener onQueryFriendListener) {


    }





    @Override
    public void queryUserByName(String nickname, OnQueryFriendListener onQueryFriendListener) {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("NickName", nickname);
        query_users(query, onQueryFriendListener);
    }

    @Override
    public void queryUserById(String id, OnQueryFriendListener onQueryFriendListener) {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("username", id);
        query_users(query, onQueryFriendListener);
    }

    @Override
    public void queryUserByDistance(OnQueryFriendListener onQueryFriendListener) {

    }

    @Override
    public void queryAlluser(OnQueryFriendListener onQueryFriendListener) {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereNotEqualTo("username", "00");
        query_users(query, onQueryFriendListener);
    }

    @Override
    public void isMyFriendByNickname(final String nickname, final OnIsMyFriendListener onIsMyFriendListener) {
        getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                for (UserInfo userbean : object) {
                    if (nickname.equals(userbean.getUsername())) {
                        onIsMyFriendListener.onSuccess(true);
                        return;
                    }
                }
                onIsMyFriendListener.onSuccess(false);
            }

            @Override
            public void onError(int code, String msg) {
                onIsMyFriendListener.onError(code, msg);
            }
        });
    }

    @Override
    public void isMyFriendByUsername(final String username, final OnIsMyFriendListener onIsMyFriendListener) {
        getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                for (UserInfo userbean : object) {
                    if (username.equals(userbean.getUsername())) {
                        onIsMyFriendListener.onSuccess(true);
                        return;
                    }
                }
                onIsMyFriendListener.onSuccess(false);

            }

            @Override
            public void onError(int code, String msg) {
                onIsMyFriendListener.onError(code, msg);
            }
        });
    }


    private void update_friend(Friends friend_1, final Friends friends_2, final OnUserListener onUserListener) {

        friend_1.update(APP.getContext(), new UpdateListener() {
            @Override
            public void onSuccess() {

                if (friends_2 != null) {
                    friends_2.update(APP.getContext(), new UpdateListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
                if (onUserListener != null)
                    onUserListener.Success();

            }

            @Override
            public void onFailure(int i, String s) {
                if (onUserListener != null)
                    onUserListener.Failed(i, s);
            }
        });
    }

    //设置完查询条件后，进行用户查询
    private void query_users(BmobQuery<UserInfo> query, final OnQueryFriendListener onQueryFriendListener) {
        query.findObjects(APP.getContext(), new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                // TODO Auto-generated method stub
                LogUtils.i("查询用户成功");
                onQueryFriendListener.onSuccess(object);
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                LogUtils.e("查询用户失败", msg);
                onQueryFriendListener.onError(code, msg);

            }
        });


    }

}
