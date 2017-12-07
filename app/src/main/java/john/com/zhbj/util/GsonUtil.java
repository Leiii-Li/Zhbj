package john.com.zhbj.util;

import com.google.gson.Gson;

/**
 * Created by John on 2017/12/6.
 */

public class GsonUtil {
    public static <T extends Object> T getObjectFromJson(String json, Class<T> t) {
        Gson gson = new Gson();
        T obj = gson.fromJson(json, t);
        return obj;
    }
}
