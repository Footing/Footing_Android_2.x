package team.far.footing.model.impl;

import java.util.HashMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import team.far.footing.app.APP;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.callback.OnSMSListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.uitl.BmobUtils;
import team.far.footing.uitl.LogUtils;

/**
 * Created by moi on 15/10/2.
 */
public class UserModel implements IUserModel {

    @Override
    public void loginGetCode(String phonenumber, final OnLoginListener onLoginListener) {
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                onLoginListener.afterEvent(event, result);
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
    public void register(final OnUserListener onUserListener) {
        sendSMS(new OnSMSListener() {
            @Override
            public void success(String country, String phone) {
                final Userbean userbean = new Userbean();
                userbean.setUsername(phone);
                userbean.setPassword(phone);
                userbean.setMobilePhoneNumber(phone);
                userbean.signUp(APP.getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        LogUtils.e("注册成功");
                        UserInfo userInfo = new UserInfo();
                        userInfo.setUserbean(BmobUtils.getCurrentUser());
                        userInfo.save(APP.getContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                onUserListener.Success(BmobUtils.getCurrentUser());
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                LogUtils.e("注册失败" + s + i);
                                onUserListener.Failed(i, s);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        LogUtils.e("注册失败" + s + i);
                        onUserListener.Failed(i, s);
                    }
                });
            }

            @Override
            public void failed() {
                onUserListener.Failed(233, "短信发送错误");
            }
        });
    }

    @Override
    public void logout(OnUserListener onUserListener) {
        BmobUser.logOut(APP.getContext());
    }

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

}
