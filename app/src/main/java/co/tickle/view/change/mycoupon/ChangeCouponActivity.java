package co.tickle.view.change.mycoupon;

import android.os.Bundle;
import android.view.View;

import co.tickle.R;
import co.tickle.view.common.BaseActivity;
import co.tickle.view.popup.FindTicklePopup;

/**
 * Created by zuby on 2016-08-02.
 */
public class ChangeCouponActivity extends BaseActivity {
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.findLayout) {
            new FindTicklePopup(ChangeCouponActivity.this).show();
        }
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
        findViewById(R.id.findLayout).setOnClickListener(this);
    }
}
