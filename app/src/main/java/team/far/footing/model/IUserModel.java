package team.far.footing.model;

import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.callback.OnUserListener;

/**
 * Created by moi on 15/10/2.
 */
public interface IUserModel {
    /**
     * 发起请求获取验证码
     *
     * @param phonenumber     -----手机号
     * @param onLoginListener -----获取与验证的监听器
     */
    void loginGetCode(String phonenumber, OnLoginListener onLoginListener);

    /**
     * 使用验证码验证
     *
     * @param phonenumber ----手机号
     * @param code        ----验证码
     */
    void loginVerifyCode(String phonenumber, String code);

    /**
     * 注册的监听器  --> 注册的gui包含在这里面，所以调用此方法就会弹出注册的gui
     *
     * @param onUserListener
     */
    void register(OnUserListener onUserListener);

    void logout(OnUserListener onUserListener);


}
