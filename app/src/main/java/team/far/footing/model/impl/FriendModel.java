package team.far.footing.model.impl;

import android.accounts.NetworkErrorException;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import team.far.footing.app.APP;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.IMessageModel;
import team.far.footing.model.bean.Friends;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.callback.OnIsMyFriendListener;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.callback.OnUserInfoListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.uitl.BmobUtils;
import team.far.footing.uitl.LogUtils;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public class FriendModel implements IFriendModel {

    public static final FriendModel instance = new FriendModel();

    final public static FriendModel getInstance() {
        return instance;
    }

    private MessageModel messageModel;


    private FriendModel() {
        messageModel = MessageModel.getInstance();
    }

    @Override
    public void addFriend(final UserInfo userInfo, final OnUserListener onUserListener) {

        IMessageModel messageModel = MessageModel.getInstance();

        messageModel.sendTxtMssageToUser(userInfo, "==addfriend==", new OnUserListener() {
            @Override
            public void Success() {
                onUserListener.Success();
            }

            @Override
            public void Failed(int i, String reason) {
                onUserListener.Failed(i, reason);
            }

            @Override
            public void onProgress(int i, String s) {
                onUserListener.onProgress(i, s);
            }
        });


    }

    @Override
    public void ensureAddFriend(final UserInfo userInfo, final OnUserListener onUserListener) {
        BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
            @Override
            public void Success(UserInfo userInfoo) {
                final Friends friends = new Friends();
                friends.setObjectId(userInfoo.getFriendId());
                friends.setUserbean(userInfoo);
                BmobRelation bmobRelation = new BmobRelation();
                bmobRelation.add(userInfo);
                friends.setFriends(bmobRelation);

                //其他人把你加为好友
                final Friends friends1 = new Friends();
                friends1.setObjectId(userInfo.getFriendId());
                friends1.setUserbean(userInfo);
                BmobRelation bmobRelation1 = new BmobRelation();
                bmobRelation1.add(userInfoo);
                friends1.setFriends(bmobRelation1);

                IMessageModel messageModel = MessageModel.getInstance();

                messageModel.sendTxtMssageToUser(userInfo, "加好友成功", null);

                update_friend(friends, friends1, onUserListener);
            }

            @Override
            public void Failed(int i, String reason) throws NetworkErrorException {

            }
        });
    }

    @Override
    public void refuseAddFriend() {

    }

    @Override
    public void deleteFriend(final UserInfo userInfo, final OnUserListener onUserListener) {
        BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
            @Override
            public void Success(UserInfo userInfoo) {
                Friends friends = new Friends();
                friends.setObjectId(userInfoo.getFriendId());
                friends.setUserbean(userInfoo);
                BmobRelation bmobRelation = new BmobRelation();
                bmobRelation.remove(userInfo);
                friends.setFriends(bmobRelation);

                Friends friends1 = new Friends();
                friends1.setObjectId(userInfo.getFriendId());
                friends1.setUserbean(userInfo);
                BmobRelation bmobRelation1 = new BmobRelation();
                bmobRelation1.remove(userInfoo);
                friends1.setFriends(bmobRelation1);


                update_friend(friends, friends1, onUserListener);
            }

            @Override
            public void Failed(int i, String reason) throws NetworkErrorException {
                throw new NetworkErrorException("网络请求错误！！！");
            }
        });
    }

    @Override
    public void getAllFriends(final OnQueryFriendListener onQueryFriendListener) {
        final BmobQuery<UserInfo> query = new BmobQuery<>();
        final Friends friends = new Friends();
        BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
            @Override
            public void Success(UserInfo userInfo) {
                friends.setObjectId(userInfo.getFriendId());
                query.addWhereRelatedTo("friends", new BmobPointer(friends));
                query.findObjects(APP.getContext(), new FindListener<UserInfo>() {
                            @Override
                            public void onSuccess(final List<UserInfo> list) {
                                onQueryFriendListener.onSuccess(list);
                            }

                            @Override
                            public void onError(int i, String s) {
                                onQueryFriendListener.onError(i, s);
                            }
                        }
                );
            }

            @Override
            public void Failed(int i, String reason) throws NetworkErrorException {
                throw new NetworkErrorException("网络请求错误!!!");
            }
        });


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


    //双方的好友表确定好后的保存
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
