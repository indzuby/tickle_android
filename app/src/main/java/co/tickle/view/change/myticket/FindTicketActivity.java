package co.tickle.view.change.myticket;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import co.tickle.R;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.ResUtils;
import co.tickle.view.adapter.MainItemTabAdapter;
import co.tickle.view.common.BaseActivity;

/**
 * Created by zuby on 2016-08-02.
 */
public class FindTicketActivity extends BaseActivity implements View.OnClickListener {
    MainItemTabAdapter tabAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_find_ticket);
        init();
    }

    public List<Fragment> sampleList() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < ResUtils.categoryTab.length; i++) {
            Fragment fragment = new FindTicketListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CodeDefinition.CATEGORY_PARAM,ResUtils.tabParam[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        return fragments;
    }

    public void init() {
        findViewById(R.id.closeButton).setOnClickListener(this);
        findViewById(R.id.layout).setOnClickListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabAdapter = new MainItemTabAdapter(getSupportFragmentManager(),this, sampleList());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
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

    @Override
    public void onClick(View v) {
        finish();
    }
}
