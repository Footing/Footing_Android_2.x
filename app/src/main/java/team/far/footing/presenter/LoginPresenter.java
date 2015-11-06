package team.far.footing.presenter;

import android.content.BroadcastReceiver;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import cn.smssdk.SMSSDK;
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


    private final static int LOGIN_SUCCESS = 0001;
    private final static int LOGIN_PROGRESS = 0002;
    private final static int LOGIN_ERROR = 0003;
    private final static int REGISTER_SUCCESS = 0004;
    private final static int REGISTER_PROGRESS = 0005;
    private final static int REGISTER_ERROR = 0006;


    private ILoginVu iLoginVu;
    private IRegisterVu iRegisterVu;
    private IUserModel userModel;
    private Handler handler;

    public LoginPresenter(ILoginVu iLoginVu) {
        this();
        this.iLoginVu = iLoginVu;
        userModel = UserModel.getInstance();

    }

    public LoginPresenter() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.arg1) {
                    case LOGIN_SUCCESS:
                        iLoginVu.showLoginSuccee();
                        break;
                    case LOGIN_PROGRESS:
                        int progress = msg.what;
                        iLoginVu.showLoginLoading(progress);
                        break;
                    case LOGIN_ERROR:
                        int errorCode = msg.what;
                        iLoginVu.showLoginFail(errorCode);
                        break;
                    case REGISTER_SUCCESS:
                        iRegisterVu.showRegisterSuccee();
                        break;
                    case REGISTER_PROGRESS:
                        int pro = msg.what;
                        iRegisterVu.showRegisterLoading(pro);
                        break;
                    case REGISTER_ERROR:
                        int error = msg.what;
                        iRegisterVu.showRegisterFail(error);
                        break;

                }
            }
        };
    }

    public LoginPresenter(final IRegisterVu iRegisterVu) {
        this();
        this.iRegisterVu = iRegisterVu;
        userModel = UserModel.getInstance();

    }

    public void login(String username, String password) {
        userModel.login(username, password, new OnLoginListener() {
            @Override
            public void onSuccess() {
                LogUtils.e("登录成功");
                Message message = handler.obtainMessage();
                message.arg1 = LOGIN_SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onProgress(int progress, String status) {
                LogUtils.e("正在登录");
                Message message = handler.obtainMessage();
                message.arg1 = LOGIN_PROGRESS;
                message.what = progress;
                handler.sendMessage(message);
            }

            @Override
            public void onError(int code, String msg) {
                LogUtils.e("登录失败");
                Message message = handler.obtainMessage();
                message.arg1 = LOGIN_ERROR;
                message.what = code;
                handler.sendMessage(message);
            }
        });

    }


    public void register(String phone, String password, String nickname) {

        userModel.register(phone, password, nickname, new OnUserListener() {
            @Override
            public void Success() {
                LogUtils.e("注册成功");
                //  这里你是在子线程哟
                Message message = handler.obtainMessage();
                message.arg1 = REGISTER_SUCCESS;
                handler.sendMessage(message);

            }

            @Override
            public void Failed(int i, String reason) {
                LogUtils.e("注册失败" + reason);
                Message message = handler.obtainMessage();
                message.arg1 = REGISTER_ERROR;
                message.what = i;
                handler.sendMessage(message);
            }

            @Override
            public void onProgress(int i, String s) {
                LogUtils.e("正在注册");
                Message message = handler.obtainMessage();
                message.arg1 = REGISTER_PROGRESS;
                message.what = i;
                handler.sendMessage(message);
            }
        });
    }


    public void VerifyCode(String phone, String code) {
        userModel.loginVerifyCode(phone, code);
        LogUtils.e("正在提交验证");
    }


    public  void onDestroy(){
       userModel.unregisterEventHandler();
    }
}
