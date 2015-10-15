package team.far.footing2.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.easemob.chat.EMChat;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;
import team.far.footing2.R;
import team.far.footing2.uitl.AppUtils;
import team.far.footing2.uitl.LogUtils;

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
        // 初始化 Bmob SDK，初始化im（）------>>> 初始化方法包含了BmobSDK的初始化步骤，故无需再初始化BmobSDK

        Bmob.initialize(this, context.getString(R.string.Bmob_Key));
        //初始化Log
        LogUtils.isDebug = true;
        //Mob启动
        SMSSDK.initSDK(this, context.getString(R.string.mob_key), context.getString(R.string.mob_secret));


        int pid = android.os.Process.myPid();
        String processAppName = AppUtils.getAppName(context, pid);
        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase("team.far.footing2")) {
            LogUtils.e("enter the service process!");
            //"com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMChat.getInstance().init(context);
        /**
         * debugMode == true 时为打开，sdk 会在log里输入调试信息
         * @param debugMode
         * 在做代码混淆的时候需要设置成false
         */
        EMChat.getInstance().setDebugMode(true);//在做打包混淆时，要关闭debug模式，如果未被关闭，则会出现程序无法运行问题
    }

    public static Context getContext() {
        return context;
    }
}