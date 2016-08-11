package co.tickle.view.collect.category;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.tickle.R;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.ResUtils;
import co.tickle.view.adapter.MainItemTabAdapter;
import co.tickle.view.common.BaseFragment;

/**
 * Created by zuby on 2016-07-06.
 */
public class CategoryFragment extends BaseFragment {
    MainItemTabAdapter tabAdapter;
    ViewPager viewPager;
    TabLayout tabLayout ;
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_default,container,false);
        init();
        return mView;
    }
    public List<Fragment> sampleList(){
        List<Fragment> fragments = new ArrayList<>();
        for(int i=0;i<ResUtils.categoryTab.length;i++) {
            Fragment fragment  = new CategoryListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CodeDefinition.CATEGORY_PARAM,ResUtils.tabParam[i]);
            fragment.setArguments(bundle);

            fragments.add(fragment);
        }
        return fragments;
    }

    public void init(){
        tabLayout = (TabLayout) mView.findViewById(R.id.tab);
        viewPager = (ViewPager) mView.findViewById(R.id.viewPager);
        tabAdapter =new MainItemTabAdapter(getFragmentManager(),getContext(),sampleList());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for(int i = 0; i<tabLayout.getTabCount();i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(ResUtils.categoryTab[i]);
        }
        viewPager.setCurrentItem(0);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
