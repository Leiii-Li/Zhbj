package john.com.zhbj.pager;

import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import john.com.zhbj.MainActivityHome;
import john.com.zhbj.app.SmartCityApplication;
import john.com.zhbj.bean.NewsCenterInfo;
import john.com.zhbj.net.NetUrl;
import john.com.zhbj.pager.menu.MenuBasePager;
import john.com.zhbj.pager.menu.MenuEachActionPager;
import john.com.zhbj.pager.menu.MenuNewsCenterPager;
import john.com.zhbj.pager.menu.MenuPhotosPager;
import john.com.zhbj.pager.menu.MenuSpecialPager;
import john.com.zhbj.util.GsonUtil;

/**
 * Created by John on 2017/12/4.
 */

public class NewsCenterPager extends BasePager {

    private NewsCenterInfo mNewsInfo;
    private FrameLayout mRootView;

    public NewsCenterPager(MainActivityHome activity) {
        super(activity);
    }

    private List<MenuBasePager> mMenuPagerList = new ArrayList();

    @Override
    public void initData() {
        mTitleView.setText("新闻");
        RequestParams params = new RequestParams(NetUrl.NEWS_CENTER_URL);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                if (null != result || result.length() == 0) {
                    processJson(result);
                }
                return false;
            }

            @Override
            public void onSuccess(String result) {
                if (null != result || result.length() == 0) {
                    processJson(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("onCancelled");
            }

            @Override
            public void onFinished() {
                System.out.println("onFinished");
            }
        });
    }

    private void processJson(String result) {
        mNewsInfo = GsonUtil.getObjectFromJson(result, NewsCenterInfo.class);
        mHomeActivity.getMenuFragment().setListData(mNewsInfo.data);
        if (mNewsInfo.data.size() > 0) {
            //数据不为空
            mMenuPagerList.add(new MenuNewsCenterPager(mNewsInfo.data.get(0), mHomeActivity));
            mMenuPagerList.add(new MenuSpecialPager());
            mMenuPagerList.add(new MenuPhotosPager());
            mMenuPagerList.add(new MenuEachActionPager());
            switchMenuDetailPage(0);
        }
    }

    /**
     * 返回根布局容器
     *
     * @return
     */
    @Override
    protected Object initView() {
        mRootView = new FrameLayout(mHomeActivity);
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mRootView;
    }

    /**
     * 通过菜单栏来切换界面
     *
     * @param position
     */

    public void switchMenuDetailPage(int position) {
        // 切换标题
        mTitleView.setText(mNewsInfo.data.get(position).title);
        // 切换界面
        mRootView.removeAllViews();
        MenuBasePager pager = mMenuPagerList.get(position);
        mRootView.addView(pager.getRootView());
        pager.initData();
        if (pager instanceof MenuPhotosPager) {
            mImageOrientationTypeButton.setVisibility(View.VISIBLE);
        } else {
            mImageOrientationTypeButton.setVisibility(View.GONE);
        }
    }
}
