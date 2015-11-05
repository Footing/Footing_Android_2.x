package team.far.footing.presenter;

import android.accounts.NetworkErrorException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import cn.bmob.v3.datatype.BmobFile;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnUploadListener;
import team.far.footing.model.callback.OnUserInfoListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.IEditUserInfoVu;
import team.far.footing.uitl.BmobUtils;

/**
 * Created by moi on 2015/8/12.
 */
public class EditUserInfoPresenter {

    private IUserModel userModel;
    private IEditUserInfoVu v;
    private FileModel fileModel;
    private UserInfo mUserInfo;

    public EditUserInfoPresenter(IEditUserInfoVu v) {
        this.v = v;
        userModel = UserModel.getInstance();
        fileModel = FileModel.getInstance();
        getUserInfo();

    }

    public void updateUserInformation() {
        if (v != null) v.showEditLoading();
        userModel.updataUserInfo(v.getNickName(), v.getSignature(),
                new OnUserListener() {
                    @Override
                    public void Success() {
                        if (v != null) v.showEditSuccee();
                    }

                    @Override
                    public void Failed(int i, String reason) {
                        v.showEditFail(i);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                }

        );
    }

    public void updatePic(Uri uri) {
        if (v != null) v.showUpdatePicLoading();
        userModel.uploadHeadPortrait(uri.getPath(), new OnUploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                if (v != null) {
                    v.dismissPicLoading();
                    v.showUpdatePicSuccess();
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
                    v.dismissPicLoading();
                    v.showUpdatePicFailed(statuscode);
                }
            }
        });
    }

    public void refreshUserInformation() {
        getUserInfo();
    }

    public void showUserInformation() {
        Bitmap bitmap = fileModel.getLocalPic(mUserInfo.getHeadPortraitFileName());
        if (bitmap != null)
            v.showUserInformation(mUserInfo, bitmap);
    }

    // 解除view的绑定
    public void onRelieveView() {
        if (v != null) v = null;
    }


    private void getUserInfo() {
        BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
            @Override
            public void Success(UserInfo userInfo) {
                mUserInfo = userInfo;
                showUserInformation();
            }

            @Override
            public void Failed(int i, String reason) throws NetworkErrorException {
                getUserInfo();
            }
        });
    }
}
