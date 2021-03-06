package team.far.footing.presenter;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import cn.bmob.v3.datatype.BmobFile;
import team.far.footing.model.IFileModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.callback.OnUploadListener;
import team.far.footing.model.callback.OnUserInfoListener;
import team.far.footing.model.callback.OngetUserPicListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.activity.EditUserInfoActivity;
import team.far.footing.ui.vu.IUserInfoVu;
import team.far.footing.uitl.BmobUtils;
import team.far.footing.uitl.LogUtils;

/**
 * Created by moi on 2015/8/12.
 */
public class UserInfoPresenter {

    private IUserInfoVu v;
    private UserInfo mUserInfo;
    private IUserModel userModel;
    private IFileModel fileModel;

    public UserInfoPresenter(final IUserInfoVu v) {
        this.v = v;
        userModel = UserModel.getInstance();
        fileModel = FileModel.getInstance();
        getUserInfo();

    }

    public void updatePic(Uri uri) {
        if (v != null) v.showUpdateLoading();
        userModel.uploadHeadPortrait(uri.getPath(), new OnUploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                if (v != null) {
                    v.dismissLoading();
                    v.showUpdateSuccess();
                }

                fileModel.downloadPic(fileName, new com.bmob.btp.callback.DownloadListener() {

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        if (v != null) v.showUserPic(BitmapFactory.decodeFile(s));
                    }

                    @Override
                    public void onProgress(String s, int i) {

                    }
                });
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onError(int statuscode, String errormsg) {
                if (v != null) {
                    v.dismissLoading();
                    v.showUpdateFailed(statuscode);
                }
            }
        });
    }

    public void refreshUserInformation() {
        getUserInfo();

    }

    public void showUserInformation() {

        fileModel.getUserPic(mUserInfo, new OngetUserPicListener() {
            @Override
            public void onSucess(Bitmap bitmap) {
                if (v != null) v.showUserInformation(mUserInfo, bitmap);
            }

            @Override
            public void onError() {
                return;
            }
        });

    }

    public void startEditUserInfoActivity(Context context) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    // 解除view的绑定
    public void onRelieveView() {
        if (v != null) v = null;
    }


    public void setUserPic(String filename) {

        fileModel.getUserPic(mUserInfo, new OngetUserPicListener() {
            @Override
            public void onSucess(Bitmap bitmap) {

            }

            @Override
            public void onError() {

            }
        });

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
}
