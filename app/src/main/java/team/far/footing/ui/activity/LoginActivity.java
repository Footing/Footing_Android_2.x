package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.LoginPresenter;
import team.far.footing.ui.vu.ILoginVu;

/**
 * Created by luoyy on 2015/10/25 0025.
 */
public class LoginActivity extends BaseActivity implements ILoginVu, View.OnClickListener {
    @Bind(R.id.login_lin_bar)
    LinearLayout loginLinBar;
    @Bind(R.id.login_edit_phone)
    EditText loginEditPhone;
    @Bind(R.id.login_edit_password)
    EditText loginEditPassword;
    @Bind(R.id.login_CardView)
    CardView loginCardView;
    @Bind(R.id.login_btn_login)
    Button loginBtnLogin;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.find_password)
    TextView findPassword;

    LoginPresenter loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
        loginPresenter = new LoginPresenter(this);
    }

    private void init() {
        loginBtnLogin.setOnClickListener(this);
        register.setOnClickListener(this);
        findPassword.setOnClickListener(this);
    }

    @Override
    public String getUserName() {
        return loginEditPhone.getText().toString();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public String getPassword() {
        return loginEditPassword.getText().toString();
    }

    @Override
    public void clearUserName() {
        loginEditPhone.setText("");
    }

    @Override
    public void clearPassword() {
        loginEditPassword.setTag("");
    }

    @Override
    public void showLoginLoading(int progress, String status) {
        showdialog(getResources().getString(R.string.login_dialog_Loading));
    }

    @Override
    public void showLoginSuccee() {
        showdialog(getResources().getString(R.string.login_dialog_succee));
    }

    @Override
    public void showLoginFail(int i, String s) {
        showdialog(getResources().getString(R.string.login_dialog_fail));
    }

    @Override
    public void showLogincancel() {
        showdialog(getResources().getString(R.string.login_dialog_cancel));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.login_btn_login:
                loginPresenter.login(getUserName(), getPassword());
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                break;
            case R.id.find_password:

                break;
        }
    }
}
