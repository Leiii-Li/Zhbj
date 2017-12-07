package john.com.zhbj.pager;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import john.com.zhbj.MainActivityHome;
import john.com.zhbj.app.SmartCityApplication;

/**
 * Created by John on 2017/12/4.
 */

public class SettingPager extends BasePager {
    public SettingPager(MainActivityHome mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        mMenuButton.setVisibility(View.INVISIBLE);
        mTitleView.setText("设置");
    }

    @Override
    protected Object initView() {
        TextView textView = new TextView(SmartCityApplication.getContext());
        textView.setText("设置中心");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }
}
