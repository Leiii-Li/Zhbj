package john.com.zhbj;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.InjectView;
import john.com.zhbj.fragment.HomeFragment;
import john.com.zhbj.fragment.MenuFragment;
import john.com.zhbj.view.SlidingMenu;

public class MainActivityHome extends FragmentActivity {

    @InjectView(R.id.mSlidingMenu)
    SlidingMenu mSlidingMenu;
    HomeFragment mHomeFragment;
    MenuFragment mMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        ButterKnife.inject(this);
        initData();
        // TODO: 2017/12/5  在有很多按钮，且按钮有不同的选中状态时，使用RadioGroup RadioButton 他会自动切换按钮的状态，可节省很多的工作。 
    }

    private void initData() {
        mHomeFragment = new HomeFragment();
        mMenuFragment = new MenuFragment();
        mSlidingMenu.addFragment(getSupportFragmentManager(), mHomeFragment, mMenuFragment);
    }

    /**
     * 获取SlidingMenu
     * @return
     */
    public SlidingMenu getSlidingMenu(){
        return mSlidingMenu;
    }

    /**
     * 获取菜单Fragment
     * @return
     */
    public MenuFragment getMenuFragment() {
        return mMenuFragment;
    }

    /**
     * 获取主页面Fragment
     * @return
     */
    public HomeFragment getHomeFragment(){
        return mHomeFragment;
    }

}
