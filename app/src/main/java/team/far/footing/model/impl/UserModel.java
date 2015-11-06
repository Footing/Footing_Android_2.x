package team.far.footing.model.impl;

import android.os.Looper;
import android.util.Log;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.easemob.EMCallBack;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;

import java.util.HashMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import team.far.footing.app.APP;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.Friends;
import team.far.footing.model.bean.MessageCenter;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.callback.OnSMSListener;
import team.far.footing.model.callback.OnUploadListener;
import team.far.footing.model.callback.OnUserInfoListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.uitl.BmobUtils;
import team.far.footing.uitl.LogUtils;

/**
 * Created by moi on 15/10/2.
 */
public class UserModel implements IUserModel {


    public static final UserModel instance = new UserModel();

    private   EventHandler eventHandler;


    final public static UserModel getInstance() {
        return instance;
    }

    private UserModel() {

    }

    @Override
    public void loginGetCode(final String phonenumber, final OnLoginListener onLoginListener) {
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(final int event, final int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                        LogUtils.e("提交验证码成功");
                        //提交验证码成功
                        final Userbean loginBean = new Userbean();
                        loginBean.setUsername(phonenumber);
                        loginBean.setPassword(phonenumber);
                        loginBean.login(APP.getContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                EMChatManager.getInstance().login(phonenumber, phonenumber, new EMCallBack() {//回调
                                    @Override
                                    public void onSuccess() {
                                        EMGroupManager.getInstance().loadAllGroups();
                                        EMChatManager.getInstance().loadAllConversations();
                                        LogUtils.d("main", "登陆聊天服务器成功！");
                                        onLoginListener.onSuccess();
                                    }

                                    @Override
                                    public void onProgress(int progress, String status) {
                                        onLoginListener.onProgress(progress, status);
                                    }

                                    @Override
                                    public void onError(int code, String message) {
                                        Log.d("main", "登陆聊天服务器失败！");
                                        onLoginListener.onError(event, message);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                onLoginListener.onError(event, s);
                            }
                        });


                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }


        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        SMSSDK.getVerificationCode("86", phonenumber);
    }

    @Override
    public void loginVerifyCode(String phonenumber, String code) {
        SMSSDK.submitVerificationCode("86", phonenumber, code);
    }

    @Override
    public void login(final String phone, String password, final OnLoginListener onLoginListener) {
        Userbean userbean = new Userbean();
        userbean.setUsername(phone);
        userbean.setPassword(password);
        userbean.login(APP.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                EMChatManager.getInstance().login(phone, phone, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        LogUtils.e("main", "登陆聊天服务器成功！");
                        onLoginListener.onSuccess();
                        // EMGroupManager.getInstance().loadAllGroups();
                        //EMChatManager.getInstance().loadAllConversations();
                    }

                    @Override
                    public void onError(int i, String s) {
                        onLoginListener.onError(i, s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        onLoginListener.onProgress(i, s);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                onLoginListener.onProgress(i, s);
            }
        });
    }

    @Override
    public void register(final String password, final OnUserListener onUserListener) {
        sendSMS(new OnSMSListener() {
                    @Override
                    public void success(String country, final String phone) {


                        final Userbean userbean = new Userbean();
                        userbean.setUsername(phone);
                        userbean.setPassword(password);
                        userbean.setMobilePhoneNumber(phone);
                        userbean.signUp(APP.getContext(), new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        final Friends friends = new Friends();
                                        friends.setUsername(phone);
                                        friends.save(APP.getContext(), new SaveListener() {
                                                    @Override
                                                    public void onSuccess() {
                                                        final UserInfo userInfo = new UserInfo();
                                                        userInfo.setUsername(phone);
                                                        userInfo.setToday_distance(0);
                                                        userInfo.setAll_distance(0);
                                                        userInfo.setIsAuth(1);
                                                        userInfo.setToday_date("");
                                                        userInfo.setPraiseCount(0);
                                                        userInfo.setIs_finish_today(0);
                                                        userInfo.setLevel(1);
                                                        userInfo.setFriendId(friends.getObjectId());
                                                        userInfo.setUserbean(BmobUtils.getCurrentUser());
                                                        userInfo.save(APP.getContext(), new SaveListener() {
                                                                    @Override
                                                                    public void onSuccess() {
                                                                        friends.setUserbean(userInfo);
                                                                        friends.save(APP.getContext());

                                                                        final MessageCenter messageCenter = new MessageCenter();
                                                                        messageCenter.setUserInfo(userInfo);
                                                                        messageCenter.save(APP.getContext(), new SaveListener() {
                                                                                    @Override
                                                                                    public void onSuccess() {
                                                                                        userInfo.setMessageCenterId(messageCenter.getObjectId());
                                                                                        userInfo.save(APP.getContext());
                                                                                        new Thread(new Runnable() {
                                                                                            public void run() {
                                                                                                try {
                                                                                                    // 调用sdk注册方法
                                                                                                    EMChatManager.getInstance().createAccountOnServer(phone, phone);
                                                                                                    onUserListener.Success();
                                                                                                } catch (final EaseMobException e) {
                                                                                                    onUserListener.Failed(404, "注册出错了");
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(int i, String s) {
                                                                                        LogUtils.e("注册失败" + s + i);
                                                                                        onUserListener.Failed(i, s);
                                                                                    }
                                                                                }
                                                                        );
                                                                    }

                                                                    @Override
                                                                    public void onFailure(int i, String s) {
                                                                        LogUtils.e("注册失败" + s + i);
                                                                        onUserListener.Failed(i, s);
                                                                    }
                                                                }

                                                        );
                                                    }

                                                    @Override
                                                    public void onFailure(int i, String s) {
                                                        LogUtils.e("注册失败" + s + i);
                                                        onUserListener.Failed(i, s);
                                                    }
                                                }

                                        );
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        LogUtils.e("注册失败" + s + i);
                                        onUserListener.Failed(i, s);
                                    }
                                }

                        );
                    }

                    @Override
                    public void failed() {
                        onUserListener.Failed(233, "短信发送错误");
                    }
                }

        );
    }

    @Override
    public void register(final String phone, final String password, final String nickname, final OnUserListener onUserListener) {

        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(final int event, final int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        final Userbean userbean = new Userbean();
                        userbean.setUsername(phone);
                        userbean.setPassword(password);
                        userbean.setMobilePhoneNumber(phone);
                        userbean.signUp(APP.getContext(), new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        final Friends friends = new Friends();
                                        friends.setUsername(phone);
                                        friends.save(APP.getContext(), new SaveListener() {
                                                    @Override
                                                    public void onSuccess() {
                                                        final UserInfo userInfo = new UserInfo();
                                                        userInfo.setUsername(phone);
                                                        userInfo.setToday_distance(0);
                                                        userInfo.setAll_distance(0);
                                                        userInfo.setIsAuth(1);
                                                        userInfo.setToday_date("");
                                                        userInfo.setPraiseCount(0);
                                                        userInfo.setIs_finish_today(0);
                                                        userInfo.setLevel(1);
                                                        userInfo.setNickName(nickname);
                                                        userInfo.setFriendId(friends.getObjectId());
                                                        userInfo.setUserbean(BmobUtils.getCurrentUser());
                                                        userInfo.save(APP.getContext(), new SaveListener() {
                                                                    @Override
                                                                    public void onSuccess() {
                                                                        friends.setUserbean(userInfo);
                                                                        friends.save(APP.getContext());

                                                                        final MessageCenter messageCenter = new MessageCenter();
                                                                        messageCenter.setUserInfo(userInfo);
                                                                        messageCenter.save(APP.getContext(), new SaveListener() {
                                                                                    @Override
                                                                                    public void onSuccess() {
                                                                                        userInfo.setMessageCenterId(messageCenter.getObjectId());
                                                                                        userInfo.save(APP.getContext());
                                                                                        new Thread(new Runnable() {
                                                                                            public void run() {
                                                                                                try {
                                                                                                    // 调用sdk注册方法
                                                                                                    Looper.prepare();
                                                                                                    EMChatManager.getInstance().createAccountOnServer(phone, phone);
                                                                                                    onUserListener.Success();
                                                                                                } catch (final EaseMobException e) {
                                                                                                    //注册失败
                                                                                                    int errorCode = e.getErrorCode();
                                                                                                    if (errorCode == EMError.NONETWORK_ERROR) {
                                                                                                        LogUtils.e("网络异常，请检查网络！");
                                                                                                    } else if (errorCode == EMError.USER_ALREADY_EXISTS) {
                                                                                                        LogUtils.e("用户已存在！");
                                                                                                    } else if (errorCode == EMError.UNAUTHORIZED) {
                                                                                                        LogUtils.e("注册失败，无权限！");
                                                                                                    } else {
                                                                                                        LogUtils.e("注册失败: " + e.getMessage());
                                                                                                    }
                                                                                                    onUserListener.Failed(433, "注册出错了");

                                                                                                }
                                                                                            }
                                                                                        }).start();
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(int i, String s) {
                                                                                        LogUtils.e("注册失败" + s + i);
                                                                                        onUserListener.Failed(i, s);
                                                                                    }
                                                                                }
                                                                        );
                                                                    }

                                                                    @Override
                                                                    public void onFailure(int i, String s) {
                                                                        LogUtils.e("注册失败" + s + i);
                                                                        onUserListener.Failed(i, s);
                                                                    }
                                                                }

                                                        );
                                                    }

                                                    @Override
                                                    public void onFailure(int i, String s) {
                                                        LogUtils.e("注册失败" + s + i);
                                                        onUserListener.Failed(i, s);
                                                    }
                                                }
                                        );
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        LogUtils.e("注册失败" + s + i);
                                        onUserListener.Failed(i, s);
                                    }
                                }
                        );

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
        SMSSDK.getVerificationCode("86", phone);
    }


    @Override
    public void logout(OnUserListener onUserListener) {
        BmobUser.logOut(APP.getContext());
        EMChatManager.getInstance().logout();
    }

    @Override
    public void updataUserInfo(String nickname, String signature, OnUserListener
            onUserListener) {
        UserInfo newUser = new UserInfo();
        newUser.setNickName(nickname);
        newUser.setSignature(signature);
        updateUser(newUser, onUserListener);
    }

    @Override
    public void uploadHeadPortrait(String filePath,
                                   final OnUploadListener onUploadHeadPortraitListener) {

        BTPFileResponse response = BmobProFile.getInstance(APP.getContext()).upload(filePath, new UploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                // TODO Auto-generated method stub
                LogUtils.i("bmob", "文件上传成功：" + fileName + ",可访问的文件地址：" + file.getUrl());
                if (onUploadHeadPortraitListener != null)
                    updateUser_HeadPortraitFilePath(url, fileName, file, onUploadHeadPortraitListener);
            }

            @Override
            public void onProgress(int progress) {
                // TODO Auto-generated method stub
                LogUtils.i("bmob", "onProgress :" + progress);
                if (onUploadHeadPortraitListener != null)
                    onUploadHeadPortraitListener.onProgress(progress);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                LogUtils.e("bmob", "文件上传失败：" + errormsg);
                if (onUploadHeadPortraitListener != null)
                    onUploadHeadPortraitListener.onError(statuscode, errormsg);
            }
        });

    }

    @Override
    public void unregisterEventHandler() {
        SMSSDK.unregisterEventHandler(eventHandler);
    }


    @Override
    public void updateUser_Signature(String signature, final OnUserListener onUserListener) {
        UserInfo newUser = new UserInfo();
        newUser.setSignature(signature);
        updateUser(newUser, onUserListener);
    }

    @Override
    public void updateUser_level(int level, OnUserListener onUserListener) {
        UserInfo newUser = new UserInfo();
        newUser.setLevel(level);
        updateUser(newUser, onUserListener);

    }

    @Override
    public void update_NickName(String nickname, OnUserListener onUserListener) {
        UserInfo newUser = new UserInfo();
        newUser.setNickName(nickname);
        updateUser(newUser, onUserListener);
    }

    @Override
    public void update_PraiseCount(int PraiseCount, OnUserListener onUserListener) {
        UserInfo newUser = new UserInfo();
        newUser.setPraiseCount(PraiseCount);
        updateUser(newUser, onUserListener);
    }

    @Override
    public void update_distance(int today_distance, int all_distance, OnUserListener
            onUserListener) {
        UserInfo newUser = new UserInfo();
        newUser.setAll_distance(all_distance);
        newUser.setToday_distance(today_distance);
        updateUser(newUser, onUserListener);
    }

    @Override
    public void update_today_distance(int today_distance, String date, OnUserListener
            onUserListener) {

    }

    @Override
    public void update_all_distance(int all_distance, OnUserListener onUserListener) {
        UserInfo newUser = new UserInfo();
        newUser.setAll_distance(all_distance);
        updateUser(newUser, onUserListener);
    }

    @Override
    public void update_is_finish_today(int update_is_finish_today, OnUserListener
            onUserListener) {
        UserInfo newUser = new UserInfo();
        newUser.setToday_distance(update_is_finish_today);
        updateUser(newUser, onUserListener);
    }


    @Override
    public void resetPasswordByEmail(String email, OnUserListener onUserListener) {

    }

    //调用第三方的注册界面

    private void sendSMS(final OnSMSListener onSMSListener) {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    // 验证成功
                    onSMSListener.success(country, phone);
                } else {
                    //验证失败
                    onSMSListener.failed();
                }
            }
        });
        registerPage.show(APP.getContext());
    }

    //设置数据完毕后，调用更新服务端数据。
    private void updateUser(final UserInfo newUser, final OnUserListener onUserListener) {

        BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
            @Override
            public void Success(UserInfo userInfo) {
                newUser.update(APP.getContext(), userInfo.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        if (onUserListener != null)
                            onUserListener.Success();
                        return;
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        if (onUserListener != null)
                            onUserListener.Failed(i, s);
                        return;
                    }
                });
            }

            @Override
            public void Failed(int i, String reason) {
                onUserListener.Failed(i, reason);
            }
        });

    }


    //文件上传的辅助方法 ---->>  不对外调用
    private void updateUser_HeadPortraitFilePath(final String url, final String filename, final BmobFile file, final OnUploadListener onUploadHeadPortraitListener) {
        final UserInfo newUser = new UserInfo();
        newUser.setHeadPortraitFilePath(file.getUrl());
        newUser.setHeadPortraitFileName(filename);

        BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
            @Override
            public void Success(UserInfo userInfo) {
                newUser.update(APP.getContext(), userInfo.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        onUploadHeadPortraitListener.onSuccess(filename, url, file);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        onUploadHeadPortraitListener.onError(code, msg);
                    }
                });
            }

            @Override
            public void Failed(int i, String reason) {
                onUploadHeadPortraitListener.onError(i, reason);
            }
        });
    }


}
