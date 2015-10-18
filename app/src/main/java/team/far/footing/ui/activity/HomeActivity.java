package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.ui.fragment.HomeFragment;
import team.far.footing.uitl.LogUtils;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.home_toolbar)
    Toolbar mToolbar;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupToolbar();
        if (savedInstanceState == null) {
            setupHomeFragment();
        }
    }

    private void setupToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle(getResources().getString(R.string.app_name));
            setSupportActionBar(mToolbar);
        }
        LogUtils.d("Test");
        LogUtils.d("Test %s","here");
    }

    /**
     * 主界面的内容区域{@link HomeFragment}
     */
    private void setupHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_container, new HomeFragment())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.home_menu_help:
                break;
            case R.id.home_menu_feedback:
                break;
            case R.id.home_menu_setting:
                break;
            case R.id.home_menu_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
