package john.com.zhbj.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by John on 2017/10/20.
 */

public class SharedPreferencesUtil {

    private static SharedPreferences mSp = null;

    public static void putBoolean(Context context, String key, boolean value) {
        hashCreateSp(context);
        mSp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        hashCreateSp(context);
        boolean b = mSp.getBoolean(key, defaultValue);
        return b;
    }

    public static void putString(Context context, String key, String value) {
        hashCreateSp(context);
        mSp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        hashCreateSp(context);
        String s = mSp.getString(key, defaultValue);
        return s;
    }

    public static void putInt(Context context, String key, int value) {
        hashCreateSp(context);
        mSp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        hashCreateSp(context);
        int i = mSp.getInt(key, defaultValue);
        return i;
    }

    private static void hashCreateSp(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
    }
}
