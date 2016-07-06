package co.tickle.view.common;

import android.app.Application;

import com.tsengvn.typekit.Typekit;


/**
 * Created by zuby on 2016. 7. 5..
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "notokr-regular.ttf"))
                .addBold(Typekit.createFromAsset(this, "notokr-medium.ttf"))
                .addItalic(Typekit.createFromAsset(this, "notokr-thin.ttf"))
                .addCustom1(Typekit.createFromAsset(this, "notokr-thin.ttf"));
    }

}
