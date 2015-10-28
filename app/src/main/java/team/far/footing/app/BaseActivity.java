package team.far.footing.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import me.drakeet.materialdialog.MaterialDialog;
import team.far.footing.R;

/**
 * Created by luoyy on 2015/10/7 0007.
 */
public class BaseActivity extends AppCompatActivity {
    private MaterialDialog dialog;
    private SystemBarTintManager tintManager;

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //KITKAT状态栏透明
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //SB颜色
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        setBarTintColor(getResources().getColor(R.color.primary_dark_color));
    }

    //让继承的Activity自己可以更换SB的颜色
    protected void setBarTintColor(int color) {
        tintManager.setTintColor(color);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    public void showdialog(String title) {
        dialog = new MaterialDialog(this)
                .setTitle(getResources().getString(R.string.activity_dialog))
                .setMessage(title);
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
