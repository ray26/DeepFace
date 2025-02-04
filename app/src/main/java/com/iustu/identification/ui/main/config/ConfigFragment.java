package com.iustu.identification.ui.main.config;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.iustu.identification.R;
import com.iustu.identification.ui.base.BaseFragment;
import com.iustu.identification.util.DataCache;
import com.iustu.identification.util.SqliteUtil;

import butterknife.BindView;

/**
 * Created by Liu Yuchuan on 2017/11/5.
 */

public class ConfigFragment extends BaseFragment {

    @BindView(R.id.tab_config)
    TabLayout tabLayout;

    @BindView(R.id.config_pager)
    ViewPager viewPager;

    private ConfigPagerAdapter mAdapter;


    @Override
    protected int postContentView() {
        return R.layout.fragment_config;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState, View view) {
        mAdapter = new ConfigPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onHide() {
        super.onHide();
        if(mAdapter != null) {
            final int count = mAdapter.getCount();
            for (int i = 0; i < count; i++) {
                BaseFragment baseFragment = (BaseFragment) mAdapter.getItem(i);
                baseFragment.onHide();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        DataCache.saveCache();
        SqliteUtil.updataLibrariedInUsed();
    }

    @Override
    public void onStop() {
        super.onStop();
        DataCache.saveCache();
        SqliteUtil.updataLibrariedInUsed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataCache.saveCache();
        SqliteUtil.updataLibrariedInUsed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DataCache.saveCache();
        SqliteUtil.updataLibrariedInUsed();
    }
}
