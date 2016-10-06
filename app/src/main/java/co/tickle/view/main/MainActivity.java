package co.tickle.view.main;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import co.tickle.R;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.ContextUtils;
import co.tickle.utils.SessionUtils;
import co.tickle.utils.SwipeDisableViewPager;
import co.tickle.view.adapter.MainMenuAdapter;
import co.tickle.view.common.BaseActivity;
import co.tickle.view.common.BaseFragment;
import co.tickle.view.popup.AccountPopup;


public class MainActivity extends BaseActivity {

    CollapsingToolbarLayout collapsingLayout;
    AppBarLayout appBarLayout;
    int nowPosition = -1;
    boolean isInit = false;
    float bigButtonWidth, bigButtonHeight;

    @Override
    public void onClick(View v) {

        super.onClick(v);
        if (v.getId() == R.id.headerCollectButton || v.getId() == R.id.headerCollectButtonSmall) {
            selectedTab(0);
        } else if (v.getId() == R.id.headerChangeButton || v.getId() == R.id.headerChangeButtonSmall) {

            token = SessionUtils.getString(this, CodeDefinition.TOKEN, "");
            if (token == null || token.length() <= 0) {
                Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                new AccountPopup(this).show();
            } else {
                selectedTab(1);
            }
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
        bigButtonWidth = findViewById(R.id.headerCollectIcon).getLayoutParams().width;
        bigButtonHeight = findViewById(R.id.headerCollectIcon).getLayoutParams().height;
        findViewById(R.id.headerChangeButton).setOnClickListener(this);
        findViewById(R.id.headerCollectButton).setOnClickListener(this);
        findViewById(R.id.headerChangeButtonSmall).setOnClickListener(this);
        findViewById(R.id.headerCollectButtonSmall).setOnClickListener(this);
        collapsingLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = 1 - (float) Math.abs(verticalOffset) / ContextUtils.pxFromDp(getBaseContext(), 120);
                Log.e("offset", verticalOffset + " " + ContextUtils.pxFromDp(getBaseContext(), 120) + " " + percent);
                if (verticalOffset + ContextUtils.pxFromDp(getBaseContext(), 120) <= 0) {
                    findViewById(R.id.bigButtonLayout).setVisibility(View.GONE);
                    findViewById(R.id.smallButtonLayout).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.headerCollectIcon).setVisibility(View.VISIBLE);
                    findViewById(R.id.headerChangeIcon).setVisibility(View.VISIBLE);
                    findViewById(R.id.bigButtonLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.smallButtonLayout).setVisibility(View.GONE);
                    if(verticalOffset + ContextUtils.pxFromDp(getBaseContext(),120) >= ContextUtils.pxFromDp(getBaseContext(),118)){
                        ViewGroup.LayoutParams params = findViewById(R.id.headerCollectIcon).getLayoutParams();
                        params.height = (int)(bigButtonHeight * percent);
                        params.width = (int)(bigButtonWidth * percent);
                        findViewById(R.id.headerCollectIcon).setLayoutParams(params);

                        params = findViewById(R.id.headerChangeIcon).getLayoutParams();
                        params.height = (int)(bigButtonHeight * percent);
                        params.width = (int)(bigButtonWidth * percent);
                        findViewById(R.id.headerChangeIcon).setLayoutParams(params);


                        if (nowPosition == 0) {
                            findViewById(R.id.headerCollectText).setAlpha(1f * percent);
                            findViewById(R.id.headerChangeText).setAlpha(0.5f * percent);
                        } else {
                            findViewById(R.id.headerCollectText).setAlpha(0.5f * percent);
                            findViewById(R.id.headerChangeText).setAlpha(1f * percent);
                        }
                    }
                    findViewById(R.id.headerCollectIcon).setPadding(0, (int)(ContextUtils.pxFromDp(getBaseContext(), 22)*(1-percent)),0,0);
                    findViewById(R.id.headerChangeIcon).setPadding(0, (int)(ContextUtils.pxFromDp(getBaseContext(), 22)*(1-percent)),0,0);

                    findViewById(R.id.headerCollectButton).setPadding(0, (int)(ContextUtils.pxFromDp(getBaseContext(), 120)*(1-percent)),0,0);
                    findViewById(R.id.headerChangeButton).setPadding(0, (int)(ContextUtils.pxFromDp(getBaseContext(), 120)*(1-percent)),0,0);
                    findViewById(R.id.headerSelectOval).setPadding(0, (int)(ContextUtils.pxFromDp(getBaseContext(), 120)*(1-percent)),0,0);
                }
            }
        });
    }

    public void selectedTab(int position) {
        if (position == nowPosition) return;
        nowPosition = position;
        Window window = getWindow();
        int[] res = {R.color.themePink70, R.color.themeBlue70};

        BaseFragment fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (position == 0) {
            fragment = new CollectFragment();
            if (isInit)
                ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            ft.replace(R.id.mainBody, fragment);
            ft.commit();
            findViewById(R.id.headerCollectIcon).setAlpha(1f);
            findViewById(R.id.headerCollectText).setAlpha(1f);
            findViewById(R.id.headerChangeIcon).setAlpha(0.5f);
            findViewById(R.id.headerChangeText).setAlpha(0.5f);
            findViewById(R.id.headerSelectOval).setSelected(false);

            findViewById(R.id.headerCollectIconSmall).setAlpha(1f);
            findViewById(R.id.headerChangeIconSmall).setAlpha(0.5f);
            findViewById(R.id.headerSelectOvalSmall).setSelected(false);
        } else {
            fragment = new ChangeFragment();
            if (isInit)
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            ft.replace(R.id.mainBody, fragment);
            ft.commit();
            findViewById(R.id.headerCollectIcon).setAlpha(0.5f);
            findViewById(R.id.headerCollectText).setAlpha(0.5f);
            findViewById(R.id.headerChangeIcon).setAlpha(1f);
            findViewById(R.id.headerChangeText).setAlpha(1f);
            findViewById(R.id.headerSelectOval).setSelected(true);

            findViewById(R.id.headerCollectIconSmall).setAlpha(0.5f);
            findViewById(R.id.headerChangeIconSmall).setAlpha(1f);
            findViewById(R.id.headerSelectOvalSmall).setSelected(true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, res[position]));
        }
    }
}
