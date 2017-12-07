package john.com.zhbj.app;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import org.xutils.x;

/**
 * Created by John on 2017/12/2.
 */

public class SmartCityApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(false);// 开启Debug模式，会影响性能
    }

    public static Context getContext() {
        return mContext;
    }
}
