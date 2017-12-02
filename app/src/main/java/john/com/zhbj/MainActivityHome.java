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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mSlidingMenu.addFragment(getSupportFragmentManager(),new HomeFragment(),new MenuFragment());
    }
}
