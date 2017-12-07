package john.com.zhbj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import john.com.zhbj.MainActivityHome;

/**
 * Created by John on 2017/11/30.
 */

public abstract class BaseFragment extends Fragment {


    protected MainActivityHome mActivity;

    public BaseFragment() {
        super();
    }

    // 初始化数据
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivityHome) getActivity();
    }

    // 加载Fragment布局的方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView();
        ButterKnife.inject(this, view);
        return view;
    }

    // 加载显示数据的方法，类似于Activity中的onCreate
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    public abstract View initView();

    protected abstract void initData();
}
