package team.far.footing.app;

import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

import me.drakeet.materialdialog.MaterialDialog;
import team.far.footing.R;

/**
 * Created by luoyy on 2015/10/7 0007.
 */
public class BaseActivity extends AppCompatActivity {
    private MaterialDialog dialog;

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
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
