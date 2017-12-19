package john.com.zhbj.pager.menu;

import android.graphics.Color;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.zip.Adler32;

import butterknife.InjectView;
import john.com.zhbj.MainActivityHome;
import john.com.zhbj.R;
import john.com.zhbj.app.SmartCityApplication;
import john.com.zhbj.bean.NewsCenterInfo;
import john.com.zhbj.net.NetUrl;
import john.com.zhbj.pager.BasePager;
import john.com.zhbj.view.MenuNewsCenterPagerViewPager;

/**
 * Created by John on 2017/12/7.
 */

public class MenuNewsCenterPager extends MenuBasePager {

    @InjectView(R.id.mMenuNewsPagerIndicator)
    TabPageIndicator mPagerIndicator;

    @InjectView(R.id.mMenuNewsPagerViewPager)
    MenuNewsCenterPagerViewPager mViewPager;

    private MainActivityHome mHomeActivity;

    private NewsCenterInfo.NewsChildInfo mInfo;
    private List<MenuBasePager> mData = new ArrayList<>();
    private MenuNewsCenterPagerAdapter mViewPagerAdapter;

    public MenuNewsCenterPager(NewsCenterInfo.NewsChildInfo newsChildInfo, MainActivityHome homeActivity) {
        this.mInfo = newsChildInfo;
        this.mHomeActivity = homeActivity;
    }

    @Override
    public void initData() {
        mData.clear();
        for (int i = 0; i < mInfo.children.size(); i++) {
            mData.add(new MenuNewsCenterItemPager());
        }
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new MenuNewsCenterPagerAdapter();
            mViewPager.setAdapter(mViewPagerAdapter);
        } else {
            mViewPagerAdapter.notifyDataSetChanged();
        }
        mPagerIndicator.setViewPager(mViewPager);
        mViewPager.setSlidingMenu(mHomeActivity.getSlidingMenu());
        mPagerIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 当页面切换到第一页时ViewPager做特殊的处理
                mHomeActivity.getSlidingMenu().setEnable(position == 0 ? true : false);
                mPagerIndicator.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected Object initView() {
        return R.layout.menu_newspager_layout;
    }

    private class MenuNewsCenterPagerAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mInfo.children.get(position).title;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MenuBasePager pager = mData.get(position);
            View view = pager.getRootView();
            pager.initData();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
