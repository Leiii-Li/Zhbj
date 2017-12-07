package john.com.zhbj.view;

import android.content.Context;
import android.support.annotation.AnimatorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

import john.com.zhbj.R;
import john.com.zhbj.fragment.BaseFragment;
import john.com.zhbj.fragment.HomeFragment;
import john.com.zhbj.fragment.MenuFragment;

/**
 * Created by John on 2017/11/21.
 */

/**
 * 修改Bug : mDistance = 0 每次执行up 或者 close open 这两个方法时，必须要将一些标记，或者判断条件的元素重置，否则会有一些bug,以后写自定义控件时也是如此！！！
 * 自定义控件，当业务逻辑变多时，很多没有的Bug都会出来，很多一部分都是因为一些标记，所以在以后的开发中需要很注重标记的重置,细心。
 */
public class SlidingMenu extends ViewGroup {
    private int mMenuViewWidth;
    private int mDistance;
    private Scroller mScroller = new Scroller(getContext());
    private int downY;
    private int downX;

    private int HOME_LAYOUT_ID;
    private int MENU_LAYOUT_ID;
    private State currentState = State.CLOSE;
    private Fragment mHomeFragment, mMenuFragment;
    // 内容FrameLayout
    private FrameLayout mHomeLayout;
    // 菜单FrameLayout
    private FrameLayout mMenuLayout;
    // Fragment管理器
    private FragmentManager mFragmentManager;

    // 当前是否在执行滑动

    // 当前是否可用
    private boolean isEnable = true;

    public void addFragment(FragmentManager supportFragmentManager, Fragment homeFragment, Fragment menuFragment) {
        this.mFragmentManager = supportFragmentManager;
        this.mHomeFragment = homeFragment;
        this.mMenuFragment = menuFragment;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(HOME_LAYOUT_ID, mHomeFragment);
        transaction.add(MENU_LAYOUT_ID, mMenuFragment);
        transaction.commit();
    }

    public void toggle() {
        if (currentState == State.OPEN) {
            currentState = State.CLOSE;
            close();
        } else {
            currentState = State.OPEN;
            open();
        }
    }

    private enum State {
        OPEN, CLOSE;
    }


    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHomeLayout = new FrameLayout(getContext());
        HOME_LAYOUT_ID = View.generateViewId();
        mHomeLayout.setId(HOME_LAYOUT_ID);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mHomeLayout.setLayoutParams(params);
        this.addView(mHomeLayout);


        mMenuLayout = new FrameLayout(getContext());
        MENU_LAYOUT_ID = View.generateViewId();
        mMenuLayout.setId(MENU_LAYOUT_ID);
        params = new ViewGroup.LayoutParams(260, LayoutParams.MATCH_PARENT);
        mMenuLayout.setLayoutParams(params);
        this.addView(mMenuLayout);
    }

    // super.onMeasure(widthMeasureSpec, heightMeasureSpec); 这行代码系统可以自动测量该自定义布局
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        mMenuViewWidth = mMenuLayout.getMeasuredWidth();
    }

    // 两个子布局只是添加在了该自定义控件中，并没有指明其Layout的位置，在此需要手动设置位置
    // 这里回调的left top right bottom 是该自定义控件的摆放位置
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        mHomeLayout.layout(i, i1, i2, i3);
        mMenuLayout.layout(i - mMenuViewWidth, i1, 0, i3);
    }

    private float startX;
    private int mLastDx = 0;
    private long downTime = 0;
    private long upTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 通过测试发现当侧拉菜单打开时，再点击首页，便无法通过滑动来隐藏侧拉菜单
        // 这里我准确的规定了是否可用的情况，首次打开，页面定位在首页，两个条件成立无法滑动
        // 当点击到新闻中心界面，打开菜单栏，在打开的状态下点击切换到首页，此时状态为OPEN,依然可以通过滑动来关闭侧滑菜单
        if (!isEnable && currentState == State.CLOSE) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                if (currentState == State.OPEN) {
                    int a = getDisplay().getWidth() - mMenuViewWidth;
                    if (startX >= a && startX <= getDisplay().getWidth()) {
                        downTime = System.currentTimeMillis();
                        System.out.println("当前为开启状态记录Down时间");
                    }
                }
                System.out.println("  Down:  " + currentState);
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果点击了规定区域，但是滑动了不视为单点
                downTime = 0;
                float endX = event.getX();
                mDistance = mLastDx + (int) (endX - startX);
                if (mDistance <= 0) {
                    mDistance = 0;
                }
                if (mDistance >= mMenuViewWidth) {
                    mDistance = mMenuViewWidth;
                }
                mScrollTo(mDistance);
                System.out.println("  Move:  " + currentState + " StartX:" + startX + " EndX: " + endX);
                break;
            case MotionEvent.ACTION_UP:
                upTime = System.currentTimeMillis();
                if (upTime - downTime <= 500) {
                    // 如果点击与释放的时间在500 毫秒内，说明是快速点击，且如果点击的不是规定区域downTime为0，相减的值为一个很大的数
                    if (currentState == State.OPEN) {
                        System.out.println("Up  当前为开启状态且Up时间Down时间相隔不超过500毫秒，执行关闭");
                        close();
                        // 执行了close，那么上次的滑动距离就为0
                        downTime = 0;
                        break;
                    }
                }
                System.out.println("  LastDx:  " + mLastDx + "  Distance:  " + mDistance + "  State:" + currentState);
                // Final版
                if (mDistance != 0) {
                    mLastDx = mDistance;
                    if (mDistance <= mMenuViewWidth / 2) {
                        System.out.println("Close");
                        currentState = State.CLOSE;
                        scroll(mDistance, 0);
                        mLastDx = 0;
                        mDistance = 0;
                    } else {
                        System.out.println("Open");
                        currentState = State.OPEN;
                        scroll(mDistance, mMenuViewWidth);
                        mLastDx = mMenuViewWidth;
                        mDistance = 0;
                    }
                }
                startX = 0;
                System.out.println("Up  LastDx:  " + mLastDx + "  Distance:  " + mDistance + "  State:" + currentState);
                break;
        }
        return true;
    }

    /**
     * 缓慢移动
     * startX:开始的距离
     * dx:移动的距离
     *
     * @param startX
     * @param toX
     */
    private void scroll(int startX, int toX) {
        int dx = toX - startX;
        int scaleTime = Math.abs(1000 / mMenuViewWidth * dx);
        mScroller.startScroll(startX, 0, dx, 0, scaleTime);
        //当执行这个方法时，ViewGroup会绘制自己以及自己的子控件，跟踪源码发现其中有computeScroll这个方法
        //在computeScroll这个方法中来获取Scroller滑动器当前的计算到的X从而来滑动整个SlidingMenu
        invalidate();
    }

    // 是dispatchTouchEvent判断，具体判断操作有它来做
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 通过测试发现当侧拉菜单打开时，再点击首页，便无法通过滑动来隐藏侧拉菜单
        // 这里我准确的规定了是否可用的情况，首次打开，页面定位在首页，两个条件成立无法滑动
        // 当点击到新闻中心界面，打开菜单栏，在打开的状态下点击切换到首页，此时状态为OPEN,依然可以通过滑动来关闭侧滑菜单
        if (!isEnable && currentState == State.CLOSE) {
            return false;
        }
        // 判断，如果是上下滑动，return false：不拦截传递给scrollview，如果左右滑动，return true：拦截事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int distanceX = moveX - downX;
                int distanceY = moveY - downY;
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public void computeScroll() {
        // 判断是否还有移动的小段
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            mScrollTo(currX);
            invalidate();
        }
    }

    private void mScrollTo(int x) {
        scrollTo(-x, 0);
    }

    public void open() {
        mLastDx = mMenuViewWidth;
        mDistance = 0;
        currentState = State.OPEN;
        scroll(0, mMenuViewWidth);
    }

    public void close() {
        // 为什么需要在这里将上一次的滑动距离手动至为正确的值
        // 当界面通过MenuButton来打开或关闭界面，当下一次滑动时就会出现瞬移的情况。
        mLastDx = 0;
        mDistance = 0;
        currentState = State.CLOSE;
        scroll(mMenuViewWidth, 0);
    }

    public void setEnable(boolean flag) {
        this.isEnable = flag;
    }
}
