package john.com.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import john.com.zhbj.util.SharedPreferencesUtil;

public class MainActivityGuide extends Activity {
    @InjectView(R.id.guide_viewPager)
    ViewPager mGuideViewPager;
    @InjectView(R.id.guide_button)
    Button mGuideButton;
    @InjectView(R.id.mGuidePointerContainer)
    LinearLayout mGuidePointerContainer;
    @InjectView(R.id.mGuidePointerFocus)
    ImageView mGuidePointerFocus;

    private int[] mImageIds = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private List<ImageView> mImages = new ArrayList<>();
    private int mGuidePointerContainerWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        initView();
        mGuidePointerContainer.measure(0, 0);
        mGuidePointerContainerWidth = mGuidePointerContainer.getMeasuredWidth();
        mGuideViewPager.setAdapter(new MyAdapter());
        mGuideViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int marginLeft = (int) ((mGuidePointerContainerWidth / 3) * positionOffset + position * (mGuidePointerContainerWidth / 3));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mGuidePointerFocus.getLayoutParams();
                layoutParams.leftMargin = marginLeft;
                mGuidePointerFocus.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                mGuideButton.setVisibility(position == 2 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        boolean isFirst = SharedPreferencesUtil.getBoolean(getApplicationContext(), "isFirst", true);
        if (!isFirst) {
            Intent intent = new Intent(MainActivityGuide.this, MainActivityHome.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {
        mImages.clear();
        for (int resId : mImageIds) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(resId);
            mImages.add(image);
            addPointer2Container();
        }
    }

    private void addPointer2Container() {
        ImageView pointer = new ImageView(this);
        pointer.setBackgroundResource(R.drawable.guide_pointer_normal);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 10;
        pointer.setLayoutParams(params);
        mGuidePointerContainer.addView(pointer);
    }


    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            //   根据条目的索引获取要显示添加的ImageView
            ImageView imageView = mImages.get(position);
            container.addView(imageView);
            //   添加什么view对象，返回什么View对象，方便判断
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object); 为什么需要注释，因为其父类实现抛出了一个异常
            container.removeView((View) object);
        }
    }

    @OnClick(R.id.guide_button)
    public void onclick() {
        SharedPreferencesUtil.putBoolean(getApplicationContext(), "isFirst", false);
        Intent intent = new Intent(MainActivityGuide.this, MainActivityHome.class);
        startActivity(intent);
        finish();
    }
}
