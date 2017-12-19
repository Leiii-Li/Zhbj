package john.com.zhbj.pager.menu;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import john.com.zhbj.MainActivityHome;
import john.com.zhbj.R;
import john.com.zhbj.app.SmartCityApplication;

/**
 * Created by John on 2017/12/18.
 * 菜单页的BasePager
 */

public abstract class MenuBasePager {
    private View mRootView;

    public MenuBasePager() {
        initRootView(initView());
        ButterKnife.inject(this, mRootView);
    }

    // 初始化布局
    private void initRootView(Object i) {
        if (null != i) {
            if (i instanceof Integer) {
                mRootView = View.inflate(SmartCityApplication.getContext(), (Integer) i, null);
            } else {
                mRootView = (View) i;
            }
        }
    }

    public View getRootView() {
        return mRootView;
    }

    // 初始化数据
    public abstract void initData();

    // 初始化布局
    protected abstract Object initView();


}
