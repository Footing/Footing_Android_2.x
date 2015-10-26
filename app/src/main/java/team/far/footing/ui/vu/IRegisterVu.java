package team.far.footing.ui.vu;

import android.app.Activity;

import team.far.footing.model.bean.Userbean;

/**
 * Created by luoyy on 2015/10/26 0026.
 */
public interface IRegisterVu {
    String getphone();

    String getPassword();

    String getNickName();

    String getVerifyCode();

    Activity getActivity();

    void clearphone();

    void clearPassword();

    void clearNickName();

    void showRegisterLoading(int i, String s);

    void showRegisterSuccee();

    void showRegisterFail(int i, String s);

    void showRegistercancel();

}
