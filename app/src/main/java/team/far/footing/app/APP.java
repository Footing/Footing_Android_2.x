package team.far.footing.app;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext() {
        return context;
    }
}