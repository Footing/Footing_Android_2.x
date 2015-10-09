package team.far.footing.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnUserInfoListener;
import team.far.footing.model.callback.OnUserListener;
import team.far.footing.model.impl.UserModel;
import team.far.footing.uitl.BmobUtils;
import team.far.footing.uitl.LogUtils;

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.tv_hello)
    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        initToolbar();
        initFonts();

        new  UserModel().register(new OnUserListener() {
            @Override
            public void Success(Userbean userbean) {
                BmobUtils.getCurrentUserInfo(new OnUserInfoListener() {
                    @Override
                    public void Success(UserInfo userInfo) {
                        Log.e(userInfo.getFriendId(),"=============>>>>>>>>>>");
                    }

                    @Override
                    public void Failed(int i, String reason) {

                    }
                });
            }

            @Override
            public void Failed(int i, String reason) {

            }
        });
    }

    private void initFonts() {
        Typeface robotoLight = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        tvHello.setTypeface(robotoLight);
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
