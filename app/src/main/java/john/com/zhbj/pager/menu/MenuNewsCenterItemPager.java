package john.com.zhbj.pager.menu;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import john.com.zhbj.app.SmartCityApplication;

/**
 * Created by John on 2017/12/18.
 */

public class MenuNewsCenterItemPager extends MenuBasePager {
    @Override
    public void initData() {

    }

    @Override
    protected Object initView() {
        TextView textView = new TextView(SmartCityApplication.getContext());
        textView.setText("政务");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
