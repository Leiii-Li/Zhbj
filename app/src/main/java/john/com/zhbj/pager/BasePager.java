package john.com.zhbj.pager;

import android.app.Activity;
import android.media.Image;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import john.com.zhbj.MainActivityHome;
import john.com.zhbj.R;
import john.com.zhbj.app.SmartCityApplication;

/**
 * Created by John on 2017/12/2.
 */

public abstract class BasePager {
    private View mRootView;

    @InjectView(R.id.titleBar_tv_title)
    protected TextView mTitleView;

    @InjectView(R.id.titleBar_btn_menu)
    protected ImageButton mMenuButton;

    @InjectView(R.id.titleBar_btn_back)
    protected ImageButton mBackButton;

    @InjectView(R.id.titleBar_ll_ShareAndTextSize)
    protected LinearLayout mShareAndTextSizeContainer;

    @InjectView(R.id.titleBar_btn_TextSize)
    protected ImageButton mTextSizeButton;

    @InjectView(R.id.titleBar_btn_Share)
    protected ImageButton mShareButton;

    @InjectView(R.id.titleBar_btn_Image)
    protected ImageButton mImageOrientationTypeButton;

    public MainActivityHome mHomeActivity;

    public BasePager(MainActivityHome activity) {
        this.mHomeActivity = activity;
        initRootView(initView());
        ButterKnife.inject(this, mRootView);
    }

    // 初始化布局
    private void initRootView(Object i) {
        View contentView;
        if (i instanceof Integer) {
            contentView = View.inflate(SmartCityApplication.getContext(), (Integer) i, null);
        } else {
            contentView = (View) i;
        }
        mRootView = View.inflate(SmartCityApplication.getContext(), R.layout.base_pager_rootview, null);
        ((FrameLayout) mRootView.findViewById(R.id.basePager_ContentViewContainer)).addView(contentView);
    }

    public View getRootView() {
        return mRootView;
    }

    // 初始化数据
    public abstract void initData();

    // 初始化布局
    protected abstract Object initView();

    @OnClick(R.id.titleBar_btn_menu)
    public void click() {
        mHomeActivity.getSlidingMenu().toggle();
    }
}
