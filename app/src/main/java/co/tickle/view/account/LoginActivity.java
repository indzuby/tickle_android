package co.tickle.view.account;

import android.os.Bundle;
import android.view.View;

import co.tickle.R;
import co.tickle.view.common.BaseActivity;

/**
 * Created by rlawn on 2016-08-01.
 */
public class LoginActivity extends BaseActivity {
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    public void init() {
        super.init();
    }
}
