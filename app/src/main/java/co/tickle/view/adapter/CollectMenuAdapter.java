package co.tickle.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import co.tickle.R;
import co.tickle.utils.ResUtils;
import co.tickle.view.common.BaseFragment;
import co.tickle.view.collect.best.BestFragment;
import co.tickle.view.collect.category.CategoryFragment;
import co.tickle.view.collect.commingsoon.ComingSoonFragment;
import co.tickle.view.collect.today.TodayFragment;

/**
 * Created by zuby on 2016-07-06.
 */
public class CollectMenuAdapter /*extends FragmentStatePagerAdapter*/{
    Context mContext;
    Map<Integer, BaseFragment> fragments;
    FragmentManager fm;
    int nowPosition = -1;
    public CollectMenuAdapter(FragmentManager fm, Context context) {
//        super(fm);
        this.fm = fm;
        mContext = context;
        fragments = new HashMap<>();
    }
//    @Override
    public void getItem(int position) {
        BaseFragment fragment = null;
//        if (fragments.containsKey(position))
//            return fragments.get(position);
        switch (position) {
            case 0:
                fragment = new ComingSoonFragment();
                break;
            case 1:
                fragment = new CategoryFragment();
                break;
            case 2:
                fragment = new BestFragment();
                break;
            case 3:
                fragment = new TodayFragment();
                break;
        }
        FragmentTransaction ft = fm.beginTransaction();
        if(nowPosition>position)
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        else if(nowPosition!=-1)
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        nowPosition=position;
        ft.replace(R.id.collectViewPager,fragment);
        ft.commit();
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.element_header_tab_menu, null);
        TextView name = (TextView) v.findViewById(R.id.tabMenu);
        View tabIndicator = v.findViewById(R.id.tabIndicator);
        tabIndicator.setVisibility(View.GONE);
        name.setText(mContext.getString(ResUtils.collectHeaderTab[position]));
        return v;
    }

//    @Override
    public int getCount() {
        return 4;
    }
}
