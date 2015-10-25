package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.ui.fragment.HomeFragment;
import team.far.footing.ui.vu.ILoginVu;

public class HomeActivity extends BaseActivity implements ILoginVu{
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    public static final String MAP_ACTION_TYPE = "action_type";
    public static final String MAP_WALK = "walk";
    public static final String MAP_DRAW = "draw";
    private long mExitTime;
    private boolean mIsPendingIntroAnimation;
    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            setupHomeFragment();
            mIsPendingIntroAnimation = true;
        }
    }


    /**
     * 主界面的内容区域{@link HomeFragment}
     */
    private void setupHomeFragment() {
        mHomeFragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_container, mHomeFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem userItem = menu.findItem(R.id.home_menu_user);
        userItem.setActionView(R.layout.layout_home_menu_user);
        if(mIsPendingIntroAnimation){
            mIsPendingIntroAnimation = false;
            mHomeFragment.startIntroAnimation(userItem.getActionView());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.home_menu_user:
                break;
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
