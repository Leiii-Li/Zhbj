package john.com.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by John on 2017/12/18.
 */

public class MenuNewsCenterPagerViewPager extends ViewPager {
    private SlidingMenu mSlidingMenu;

    public MenuNewsCenterPagerViewPager(Context context) {
        super(context);
    }

    public MenuNewsCenterPagerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        System.out.println("  ViewPagerSuperOnInterceptor :" + b);
        return b;
    }

    public void setSlidingMenu(SlidingMenu slidingMenu) {
        this.mSlidingMenu = slidingMenu;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 如果当前页为0说明，SlidingMenu可能需要这个事件先不消费
                System.out.println("  ViewPagerSuperOnTouchEvent  Down :" + b);
                if (null != mSlidingMenu) {
                    mSlidingMenu.setDownX(ev.getRawX());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("  ViewPagerSuperOnTouchEvent  Move :" + b);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("  ViewPagerSuperOnTouchEvent  Up   :" + b);
                break;
        }
        return b;
    }
}
