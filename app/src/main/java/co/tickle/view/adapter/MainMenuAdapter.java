package co.tickle.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

import co.tickle.view.common.BaseFragment;
import co.tickle.view.main.ChangeFragment;
import co.tickle.view.main.CollectFragment;

/**
 * Created by zuby on 2016-07-07.
 */
public class MainMenuAdapter extends FragmentStatePagerAdapter {
    Context mContext;
    Map<Integer, BaseFragment> fragments;

    public MainMenuAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        fragments = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments.containsKey(position))
            return fragments.get(position);
        BaseFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new CollectFragment();
                break;
            case 1:
                fragment = new ChangeFragment();
                break;
        }
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;

    }
}