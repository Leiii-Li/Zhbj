package john.com.zhbj.fragment;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.text.util.LinkifyCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import john.com.zhbj.R;
import john.com.zhbj.app.SmartCityApplication;
import john.com.zhbj.pager.BasePager;
import john.com.zhbj.pager.GovPager;
import john.com.zhbj.pager.HomePager;
import john.com.zhbj.pager.NewsCenterPager;
import john.com.zhbj.pager.SettingPager;
import john.com.zhbj.pager.SmartServicePager;

/**
 * Created by John on 2017/11/29.
 */

public class HomeFragment extends BaseFragment {

    private int index = 0;

    @InjectView(R.id.mPagerContainer)
    ViewPager mPagerContainer;

    @InjectView(R.id.mHone_RadioGroup)
    RadioGroup mHomeRadioGroup;

    private List<BasePager> mData = new ArrayList<>();
    private MyViewPager mAdapter;

    @Override
    public View initView() {
        return View.inflate(SmartCityApplication.getContext(), R.layout.home_fragment_layout, null);
    }

    @Override
    protected void initData() {
        mData.clear();
        mData.add(new HomePager(mActivity));
        mData.add(new NewsCenterPager(mActivity));
        mData.add(new SmartServicePager(mActivity));
        mData.add(new GovPager(mActivity));
        mData.add(new SettingPager(mActivity));
        if (mAdapter == null) {
            mAdapter = new MyViewPager();
            mPagerContainer.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mPagerContainer.setCurrentItem(index);
        mData.get(index).initData();
        mActivity.getSlidingMenu().setEnable((mPagerContainer.getCurrentItem() == 0 || mPagerContainer.getCurrentItem() == mData.size() - 1) ? false : true);
        mPagerContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mData.get(index).initData();
                mActivity.getSlidingMenu().setEnable((position == 0 || position == mData.size() - 1) ? false : true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mHomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mRadioButton_Home:
                        index = 0;
                        break;
                    case R.id.mRadioButton_NewsCenter:
                        index = 1;
                        break;
                    case R.id.mRadioButton_SmartService:
                        index = 2;
                        break;
                    case R.id.mRadioButton_Gov:
                        index = 3;
                        break;
                    case R.id.mRadioButton_Setting:
                        index = 4;
                        break;
                }
                mPagerContainer.setCurrentItem(index, false);
            }
        });
    }

    public NewsCenterPager getNewsCenterPager(){
        return (NewsCenterPager) mData.get(1);
    }

    public BasePager getPager(int position) {
        return mData.get(position);
    }

    class MyViewPager extends PagerAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            BasePager pager = mData.get(position);
            container.addView(pager.getRootView());
            return pager.getRootView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        index = mPagerContainer.getCurrentItem();
    }
}
