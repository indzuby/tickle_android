package co.tickle.view.main;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import co.tickle.R;
import co.tickle.utils.SwipeDisableViewPager;
import co.tickle.view.adapter.MainMenuAdapter;
import co.tickle.view.common.BaseActivity;
import co.tickle.view.common.BaseFragment;


public class MainActivity extends BaseActivity {

    SwipeDisableViewPager viewPager;
    MainMenuAdapter mainAdapter;
    int nowPosition=-1;
    boolean isInit = false;
    @Override
    public void onClick(View v) {

        super.onClick(v);
        if(v.getId() == R.id.headerCollectButton) {
            selectedTab(0);
        }else if(v.getId() == R.id.headerChangeButton) {
            selectedTab(1);
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
        selectedTab(0);
        isInit = true;
        findViewById(R.id.headerChangeButton).setOnClickListener(this);
        findViewById(R.id.headerCollectButton).setOnClickListener(this);
    }
    public void selectedTab(int position){
        if(position == nowPosition) return;
        nowPosition = position;
        Window window = getWindow();
        int[] res={R.color.themePink70,R.color.themeBlue70};

        BaseFragment fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(position ==0 ){
            fragment = new CollectFragment();
            if(isInit)ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            ft.replace(R.id.mainBody,fragment);
            ft.commit();
            findViewById(R.id.headerCollectIcon).setAlpha(1f);
            findViewById(R.id.headerCollectText).setAlpha(1f);
            findViewById(R.id.headerChangeIcon).setAlpha(0.5f);
            findViewById(R.id.headerChangeText).setAlpha(0.5f);
            findViewById(R.id.headerSelectOval).setSelected(false);
        }else {
            fragment = new ChangeFragment();
            if(isInit)ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            ft.replace(R.id.mainBody,fragment);
            ft.commit();
            findViewById(R.id.headerCollectIcon).setAlpha(0.5f);
            findViewById(R.id.headerCollectText).setAlpha(0.5f);
            findViewById(R.id.headerChangeIcon).setAlpha(1f);
            findViewById(R.id.headerChangeText).setAlpha(1f);
            findViewById(R.id.headerSelectOval).setSelected(true);
        }
        if (Build.VERSION.SDK_INT>=21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, res[position]));
        }
    }
}
