package john.com.zhbj.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import john.com.zhbj.R;

/**
 * Created by John on 2017/11/29.
 */

public class HomeFragment extends BaseFragment {


    @Override
    public View initView() {
        return View.inflate(mActivity, R.layout.home_fragment_layout, null);
    }

    @Override
    protected void initData() {

    }
}
