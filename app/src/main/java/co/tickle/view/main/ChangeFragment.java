package co.tickle.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tickle.R;
import co.tickle.utils.SwipeDisableViewPager;
import co.tickle.view.adapter.ChangeMenuAdapter;
import co.tickle.view.adapter.CollectMenuAdapter;
import co.tickle.view.common.BaseFragment;

/**
 * Created by zuby on 2016-07-07.
 */
public class ChangeFragment extends BaseFragment {

    TabLayout tabLayout;
    SwipeDisableViewPager viewPager;
    ChangeMenuAdapter mainAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_tickle_change,container,false);
        init();
        return mView;
    }
    public void init(){

        tabLayout = (TabLayout) mView.findViewById(R.id.changeHeaderTabs);
        viewPager = (SwipeDisableViewPager) mView.findViewById(R.id.changeViewPager);
        mainAdapter = new ChangeMenuAdapter(getFragmentManager(),getContext());
        viewPager.setAdapter(mainAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(false);
        tabLayout.setupWithViewPager(viewPager);
        for(int i = 0; i<tabLayout.getTabCount();i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mainAdapter.getTabView(i));
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
        viewPager.setCurrentItem(position);
    }
}
