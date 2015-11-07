package team.far.footing.presenter;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import team.far.footing.model.IFileModel;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.callback.OnIsMyFriendListener;
import team.far.footing.model.callback.OnUserInfoListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.ui.vu.IFriendInfoVu;
import team.far.footing.uitl.BmobUtils;
import team.far.footing.uitl.LogUtils;

/**
 * Created by moi on 2015/8/18.
 */
public class FriendInfoPresenter {

    private IFriendModel friendModel;
    private IFriendInfoVu v;
    private IFileModel fileModel;
    private UserInfo mUserInfo;

    private final MyHandler myHandler;


    public FriendInfoPresenter(IFriendInfoVu v, UserInfo userInfo, Activity activity) {
        this.v = v;
        friendModel = FriendModel.getInstance();
        fileModel = FileModel.getInstance();
        myHandler = new MyHandler(activity);
        mUserInfo = userInfo;
        showUserInformation();
    }

    public void initView() {
        friendModel.isMyFriendByUsername(mUserInfo.getUsername(), new OnIsMyFriendListener() {
            @Override
            public void onSuccess(boolean b) {
                LogUtils.d("是不是好友？" + b);
                if (b) {
                    if (v != null) v.initFriendView();
                } else {
                    if (v != null) v.initNotFriendView();
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (v != null) {
                    v.initNotFriendView();
                    v.onError("加载出错了！");
                }
            }
        });
    }

    public void addFriend() {
        v.showProgress();
        friendModel.addFriend(mUserInfo, new OnUserListener() {
            @Override
            public void Success() {
                Message msg = myHandler.obtainMessage();
                msg.what = 0x0001;
                myHandler.sendMessage(msg);

            }

            @Override
            public void Failed(int i, String reason) {

                Message msg = myHandler.obtainMessage();
                msg.what = 0x0002;
                msg.arg1 = i;
                myHandler.sendMessage(msg);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public void deleteFriend() {
        if (v != null) v.showProgress();
        friendModel.deleteFriend(mUserInfo, new OnUserListener() {
            @Override
            public void Success() {
                Message msg = myHandler.obtainMessage();
                msg.what = 0x0003;
                myHandler.sendMessage(msg);


            }

            @Override
            public void Failed(int i, String reason) {

                Message msg = myHandler.obtainMessage();
                msg.what = 0x0004;
                msg.arg1 = i;
                myHandler.sendMessage(msg);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        } );

    }

    public void talk() {

    }

    public void onRelieveView() {
        if (v != null) v = null;
    }

    public void refreshUserInformation() {
        getUserInfo();
    }

    public void showUserInformation() {
        Bitmap bitmap = fileModel.getLocalPic(mUserInfo.getHeadPortraitFileName());
        if (v != null) v.showUserInformation(mUserInfo, bitmap);
    }


    //得到 mUserInfo
    private void getUserInfo() {
        BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
            @Override
            public void Success(UserInfo userInfo) {

                mUserInfo = userInfo;
                LogUtils.e(mUserInfo.getNickName());
                showUserInformation();
            }

            @Override
            public void Failed(int i, String reason) throws NetworkErrorException {
                LogUtils.e("failed");
                getUserInfo();
            }
        });
    }

    class MyHandler extends Handler {

        private final WeakReference<Activity> mActivity;


        public MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x0001:
                    if (v != null) {
                        v.dismissProgress();
                        v.onAddSuccess();
                    }
                    break;

                case 0x0002:

                    if (v != null) {
                        v.dismissProgress();
                        v.onAddFail(msg.arg1);
                    }
                    break;

                case 0x0003:
                    if (v != null) {
                        v.dismissProgress();
                        v.onDeleteSuccess();
                    }
                    break;

                case 0x0004:
                    if (v != null) {
                        v.dismissProgress();
                        v.onDeleteFail(msg.arg1);
                    }
                    break;
            }
        }
    }

}
