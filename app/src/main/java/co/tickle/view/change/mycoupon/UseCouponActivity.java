package co.tickle.view.change.mycoupon;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import co.tickle.R;
import co.tickle.view.common.BaseActivity;

/**
 * Created by rlawn on 2016-07-28.
 */
public class UseCouponActivity extends BaseActivity {

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_coupon);
        init();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
