package co.tickle.view.change.mycoupon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import co.tickle.R;
import co.tickle.view.adapter.InterestTickleAdapter;
import co.tickle.view.adapter.MyCollectTickleAdapter;
import co.tickle.view.adapter.TickleAdapter;
import co.tickle.view.common.BaseFragment;

/**
 * Created by zuby on 2016-07-06.
 */
public class MyCouponListFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_mycoupon,container,false);
        init();
        return mView;
    }
    public void init(){
        RecyclerView listView = (RecyclerView) mView.findViewById(R.id.interestListView);
        listView.setAdapter(new InterestTickleAdapter(getContext()));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        listView.setLayoutManager(llm);
 
        RecyclerView gridView = (RecyclerView) mView.findViewById(R.id.myCollectGridView);
        gridView.setAdapter(new MyCollectTickleAdapter(getContext()));
        GridLayoutManager glm = new GridLayoutManager(getContext(),3);
        gridView.setLayoutManager(glm);
    }
}
