package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.LoginPresenter;
import team.far.footing.ui.vu.IRegisterVu;
import team.far.footing.uitl.LogUtils;

public class RegisterActivity extends BaseActivity implements IRegisterVu, View.OnClickListener {

    @Bind(R.id.register_lin_bar)
    LinearLayout registerLinBar;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.send)
    Button send;
    @Bind(R.id.register_edit_password)
    EditText registerEditPassword;
    @Bind(R.id.register_edit_VerifyCode)
    EditText registerEditVerifyCode;

    private LoginPresenter loginPresenter;
    private int type = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
        init();
    }

    private void init() {
        send.setOnClickListener(this);
    }


    @Override
    public String getphone() {
        return phone.getText().toString();
    }

    @Override
    public String getPassword() {
        return registerEditPassword.getText().toString();
    }

    @Override
    public String getNickName() {
        return name.getText().toString();
    }

    @Override
    public String getVerifyCode() {
        return registerEditVerifyCode.getText().toString();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void clearphone() {
        phone.setText("");
    }

    @Override
    public void clearPassword() {
        registerEditPassword.setText("");
    }

    @Override
    public void clearNickName() {
        name.setText("");
    }

    @Override
    public void showRegisterLoading(int i) {
        showdialog(getResources().getString(R.string.register_dialog_Loading));
    }

    @Override
    public void showRegisterSuccee() {
        showdialog(getResources().getString(R.string.register_dialog_succee));

    }

    @Override
    public void showRegisterFail(int i) {
        showdialog(getResources().getString(R.string.register_dialog_fail));
    }

    @Override
    public void showRegistercancel() {
        showdialog(getResources().getString(R.string.register_dialog_cancel));
    }

    @Override
    public void onClick(View v) {
        if (type == 0) {
            loginPresenter.register(getphone(), getPassword(), getNickName());
            send.setText("点击验证");
            ++type;
            return;
        }
        if (type == 1) {
            LogUtils.e("点击了点击验证" + type + "button");
            loginPresenter.VerifyCode(getphone(), getVerifyCode());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }
}
