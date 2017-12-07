package john.com.zhbj.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import john.com.zhbj.R;
import john.com.zhbj.app.SmartCityApplication;
import john.com.zhbj.bean.NewsCenterInfo;
import john.com.zhbj.pager.NewsCenterPager;

/**
 * Created by John on 2017/11/29.
 */

public class MenuFragment extends BaseFragment {
    @InjectView(R.id.mMenuListView)
    ListView mListView;
    private Myadapter mAdapter;
    private List<NewsCenterInfo.NewsChildInfo> mData = new ArrayList<>();
    private int currentPosition = 0;

    @Override
    public View initView() {
        return View.inflate(SmartCityApplication.getContext(), R.layout.menu_fragment_layout, null);
    }

    @Override
    protected void initData() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                mAdapter.notifyDataSetChanged();
                mActivity.getSlidingMenu().close();
                mActivity.getHomeFragment().getNewsCenterPager().switchPage(position);
            }
        });
    }

    public void setListData(List<NewsCenterInfo.NewsChildInfo> listData) {
        this.mData = listData;
        currentPosition = 0;
        if (mAdapter == null) {
            mAdapter = new Myadapter();
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * listview的adapter
     **/
    private class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            ViewHolder viewHolder;
            if (convertView == null) {
                v = View.inflate(SmartCityApplication.getContext(), R.layout.menu_listview_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mArrow = (ImageView) v.findViewById(R.id.item_iv_arrow);
                viewHolder.mTitle = (TextView) v.findViewById(R.id.item_tv_title);
                v.setTag(viewHolder);
            } else {
                v = convertView;
                viewHolder = (ViewHolder) v.getTag();
            }
            viewHolder.mArrow.setImageResource(R.mipmap.menu_arr_select);
            viewHolder.mTitle.setText(mData.get(position).title);
            if (currentPosition == position) {
                viewHolder.mArrow.setImageResource(R.mipmap.menu_arr_select);
                viewHolder.mTitle.setTextColor(Color.RED);
            } else {
                viewHolder.mArrow.setImageResource(R.mipmap.menu_arr_normal);
                viewHolder.mTitle.setTextColor(Color.WHITE);
            }
            return v;
        }

    }

    static class ViewHolder {
        TextView mTitle;
        ImageView mArrow;
    }
}
