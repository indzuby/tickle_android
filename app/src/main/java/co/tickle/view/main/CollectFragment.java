package co.tickle.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tickle.R;
import co.tickle.utils.ResUtils;
import co.tickle.utils.SwipeDisableViewPager;
import co.tickle.view.adapter.CollectMenuAdapter;
import co.tickle.view.common.BaseFragment;
import co.tickle.view.popup.AccountPopup;

/**
 * Created by zuby on 2016-07-07.
 */
public class CollectFragment extends BaseFragment {

    TabLayout tabLayout;
    SwipeDisableViewPager viewPager;
    CollectMenuAdapter mainAdapter;


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.settingButton) {
            new AccountPopup(getActivity()).show();

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_tickle_collect,container,false);
        init();
        return mView;
    }
    public void init(){
        tabLayout = (TabLayout) mView.findViewById(R.id.collectHeaderTabs);
//        viewPager = (SwipeDisableViewPager) mView.findViewById(R.id.collectViewPager);
        mainAdapter = new CollectMenuAdapter(getFragmentManager(),getContext());
//        viewPager.setAdapter(mainAdapter);
//        viewPager.setPagingEnabled(false);
//        tabLayout.setupWithViewPager(viewPager);
        for(int i = 0; i<ResUtils.collectHeaderTab.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(mainAdapter.getTabView(i));
            tabLayout.addTab(tab);
        }
        for (int i=0; i<tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(5, 0, 0, 0);
            tab.setPadding(5,0,0,0);
            tab.requestLayout();
        }
        selectedTab(0);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mView.findViewById(R.id.settingButton).setOnClickListener(this);

    }
    public void selectedTab(int position){
        for(int i = 0; i<tabLayout.getTabCount();i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View name = tab.getCustomView().findViewById(R.id.tabMenu);
            View indicator = tab.getCustomView().findViewById(R.id.tabIndicator);
            name.setSelected(false);
            indicator.setVisibility(View.GONE);
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View name = tab.getCustomView().findViewById(R.id.tabMenu);
        View indicator = tab.getCustomView().findViewById(R.id.tabIndicator);
        name.setSelected(true);
        indicator.setVisibility(View.VISIBLE);
        tab.select();
        mainAdapter.getItem(position);
//        viewPager.setCurrentItem(position);
    }
}
