package co.tickle.view.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;


import co.tickle.R;
import co.tickle.utils.SwipeDisableViewPager;
import co.tickle.view.adapter.MainMenuAdapter;
import co.tickle.view.common.BaseActivity;



public class MainActivity extends BaseActivity {

    SwipeDisableViewPager viewPager;
    MainMenuAdapter mainAdapter;
    @Override
    public void onClick(View v) {

        super.onClick(v);
        if(v.getId() == R.id.headerCollectButton) {
            viewPager.setCurrentItem(0);
        }else if(v.getId() == R.id.headerChangeButton) {
            viewPager.setCurrentItem(1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void init() {
        super.init();
        viewPager = (SwipeDisableViewPager) findViewById(R.id.mainViewPager);
        mainAdapter = new MainMenuAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(mainAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPagingEnabled(false);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        findViewById(R.id.headerChangeButton).setOnClickListener(this);
        findViewById(R.id.headerCollectButton).setOnClickListener(this);
    }
    public void selectedTab(int position){
        if(position ==0 ){
            findViewById(R.id.headerCollectIcon).setAlpha(1f);
            findViewById(R.id.headerCollectText).setAlpha(1f);
            findViewById(R.id.headerChangeIcon).setAlpha(0.5f);
            findViewById(R.id.headerChangeText).setAlpha(0.5f);
            findViewById(R.id.headerSelectOval).setSelected(false);
        }else {
            findViewById(R.id.headerCollectIcon).setAlpha(0.5f);
            findViewById(R.id.headerCollectText).setAlpha(0.5f);
            findViewById(R.id.headerChangeIcon).setAlpha(1f);
            findViewById(R.id.headerChangeText).setAlpha(1f);
            findViewById(R.id.headerSelectOval).setSelected(true);

        }
    }
}
