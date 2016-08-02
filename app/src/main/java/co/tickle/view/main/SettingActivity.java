package co.tickle.view.main;

import android.os.Bundle;
import android.view.View;

import co.tickle.R;
import co.tickle.view.common.BaseActivity;

/**
 * Created by zuby on 2016-08-02.
 */
public class SettingActivity extends BaseActivity {
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_coupon);
        init();
    }

    @Override
    public void init() {
        super.init();
    }
}
