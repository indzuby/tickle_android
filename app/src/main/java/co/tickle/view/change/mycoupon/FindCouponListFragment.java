package co.tickle.view.change.mycoupon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tickle.R;
import co.tickle.view.adapter.TickleAdapter;
import co.tickle.view.common.BaseFragment;

/**
 * Created by zuby on 2016-07-06.
 */
public class FindCouponListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_tickle_list,container,false);
        init();
        return mView;
    }
    public void init(){
        RecyclerView listView = (RecyclerView) mView.findViewById(R.id.tickleList);
        listView.setAdapter(new TickleAdapter(getContext(),R.layout.item_tickle_find,this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}