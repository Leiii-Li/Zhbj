package john.com.zhbj.pager;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import john.com.zhbj.MainActivityHome;
import john.com.zhbj.app.SmartCityApplication;
import john.com.zhbj.bean.NewsCenterInfo;
import john.com.zhbj.net.NetUrl;
import john.com.zhbj.util.GsonUtil;

/**
 * Created by John on 2017/12/4.
 */

public class NewsCenterPager extends BasePager {

    private NewsCenterInfo mNewsInfo;

    public NewsCenterPager(MainActivityHome activity) {
        super(activity);
    }

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
    }

    @Override
    protected Object initView() {
        TextView textView = new TextView(SmartCityApplication.getContext());
        textView.setText("新闻中心");
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    /**
     * 通过菜单栏来切换界面
     *
     * @param position
     */
    public void switchPage(int position) {
        mTitleView.setText(mNewsInfo.data.get(position).title);
    }
}
