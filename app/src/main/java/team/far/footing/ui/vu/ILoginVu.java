package team.far.footing.ui.vu;

import android.app.Activity;

import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 15/10/2.
 */
public interface ILoginVu {

    String getUserName();

    Activity getActivity();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoginLoading(int progress, String status);

    void showLoginSuccee();

    void showLoginFail(int i, String s);

    void showLogincancel();
}
