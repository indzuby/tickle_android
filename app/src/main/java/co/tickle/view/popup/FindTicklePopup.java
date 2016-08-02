package co.tickle.view.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.tickle.R;
import co.tickle.utils.ResUtils;
import co.tickle.view.adapter.CollectMenuAdapter;
import co.tickle.view.adapter.FindTickleAdapter;
import co.tickle.view.adapter.MainItemTabAdapter;
import co.tickle.view.change.mycoupon.FindCouponListFragment;
import co.tickle.view.change.mycoupon.UseCouponActivity;
import co.tickle.view.collect.category.CategoryListFragment;
import co.tickle.view.common.BaseActivity;

/**
 * Created by zuby on 2016-08-02.
 */
public class FindTicklePopup extends Dialog implements View.OnClickListener {
    MainItemTabAdapter tabAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    BaseActivity mActivity;

    public FindTicklePopup(BaseActivity activity) {
        super(activity, android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_find_coupon);
        init();
    }

    public List<Fragment> sampleList() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < ResUtils.categoryTab.length; i++)
            fragments.add(new FindCouponListFragment());
        return fragments;
    }

    public void init() {
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.black50));
        findViewById(R.id.closeButton).setOnClickListener(this);
        findViewById(R.id.layout).setOnClickListener(this);
        setCanceledOnTouchOutside(true);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        tabAdapter = new MainItemTabAdapter(mActivity.getSupportFragmentManager(), getContext(), sampleList());
//        viewPager.setAdapter(tabAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setText(ResUtils.categoryTab[i]);
//        }
//        viewPager.setCurrentItem(0);
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
