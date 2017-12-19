package john.com.zhbj.pager.menu;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import john.com.zhbj.MainActivityHome;
import john.com.zhbj.app.SmartCityApplication;
import john.com.zhbj.pager.BasePager;

/**
 * Created by John on 2017/12/7.
 */

public class MenuSpecialPager extends MenuBasePager {
    public MenuSpecialPager() {
    }

    @Override
    public void initData() {

    }

    @Override
    protected Object initView() {
        TextView textView = new TextView(SmartCityApplication.getContext());
        textView.setText("菜单详情页-专题");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
