package team.far.footing.app;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import team.far.footing.R;

/**
 *
 * Created by moi on 15/10/2.
 */
public class APP extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 各种初始化
        // 初始化 Bmob SDK
        Bmob.initialize(this, context.getString(R.string.Bmob_Key));
    }

    public static Context getContext() {
        return context;
    }
}