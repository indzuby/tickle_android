package co.tickle.view.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import co.tickle.R;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;
import co.tickle.view.common.BaseActivity;
import co.tickle.view.main.MainActivity;

/**
 * Created by zuby on 2016. 7. 5..
 */

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if(!SessionUtils.getBoolean(this, CodeDefinition.ADD_SHORTCUT,false)) {
            SessionUtils.putBoolean(this, CodeDefinition.ADD_SHORTCUT, true);
            addShortcut(this);
        }
        startMainActivity();
    }

    protected void startMainActivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
