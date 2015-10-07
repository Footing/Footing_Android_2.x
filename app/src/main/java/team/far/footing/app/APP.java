package team.far.footing.app;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;
import team.far.footing.R;
import team.far.footing.uitl.LogUtils;

/**
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
        //初始化Log
        LogUtils.isDebug = true;
        //Mob启动
        SMSSDK.initSDK(this, context.getString(R.string.mob_key), context.getString(R.string.mob_secret));
    }

    public static Context getContext() {
        return context;
    }
}