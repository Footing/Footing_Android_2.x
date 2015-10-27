package team.far.footing.presenter;

import team.far.footing.model.IUserModel;
import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.ILoginVu;
import team.far.footing.ui.vu.IRegisterVu;
import team.far.footing.uitl.LogUtils;

/**
 * Created by moi on 15/10/2.
 */


// 为了防止 P 过多，就把 登录注册的 P 写在一起。
public class LoginPresenter {

    private ILoginVu iLoginVu;
    private IRegisterVu iRegisterVu;
    private IUserModel userModel;

    public LoginPresenter(ILoginVu iLoginVu) {
        this.iLoginVu = iLoginVu;
        userModel = UserModel.getInstance();
    }
    public LoginPresenter(IRegisterVu iRegisterVu) {
        this.iRegisterVu = iRegisterVu;
        userModel = UserModel.getInstance();
    }

    public void login(String username, String password) {

        userModel.login(username, password, new OnLoginListener() {
            @Override
            public void onSuccess() {
                iLoginVu.showLoginSuccee();
            }

            @Override
            public void onProgress(int progress, String status) {
                iLoginVu.showLoginLoading(progress, status);
            }

            @Override
            public void onError(int code, String message) {
                iLoginVu.showLoginFail(code, message);
            }
        });

    }


    public void register(String phone, String password, String nickname) {

        userModel.register(phone, password, nickname, new OnUserListener() {
            @Override
            public void Success() {
                iRegisterVu.showRegisterSuccee();
            }

            @Override
            public void Failed(int i, String reason) {
                iRegisterVu.showRegisterFail(i, reason);
            }

            @Override
            public void onProgress(int i, String s) {
                iRegisterVu.showRegisterLoading(i, s);
            }
        });
    }


    public void VerifyCode(String phone, String code) {
        userModel.loginVerifyCode(phone, code);
        LogUtils.e("正在提交验证");
    }

}
