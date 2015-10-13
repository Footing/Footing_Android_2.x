package team.far.footing2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.bmob.im.inteface.EventListener;
import team.far.footing2.uitl.LogUtils;

/**
 * Created by luoyy on 2015/10/13 0013.
 */
public class MyMessageReceiver extends BroadcastReceiver {

    // 事件监听
    public static ArrayList<EventListener> ehList = new ArrayList<EventListener>();

    @Override
    public void onReceive(Context context, Intent intent) {
        String json = intent.getStringExtra("msg");
        LogUtils.i("收到的message = " + json);
        //省略其他代码
    }
}