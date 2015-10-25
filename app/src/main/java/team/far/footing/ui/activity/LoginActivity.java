package team.far.footing.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.ui.vu.ILoginVu;

/**
 * Created by luoyy on 2015/10/25 0025.
 */
public class LoginActivity extends BaseActivity  implements ILoginVu{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void showLoginLoading() {

    }

    @Override
    public void showLoginSuccee(Userbean userbean) {

    }

    @Override
    public void showLoginFail(int i, String s) {

    }

    @Override
    public void showLogincancel() {

    }

}
