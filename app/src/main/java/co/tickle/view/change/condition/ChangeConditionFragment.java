package co.tickle.view.change.condition;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tickle.R;
import co.tickle.view.adapter.ChangeTicketAdapter;
import co.tickle.view.common.BaseFragment;

/**
 * Created by zuby on 2016-07-06.
 */
public class ChangeConditionFragment extends BaseFragment {
    boolean isChanging;
    ChangeTicketAdapter adapter;
    RecyclerView listView;
    LinearLayoutManager llm;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.changing)
            setSort(true);
        else if(v.getId() == R.id.changed)
            setSort(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_change_condition,container,false);
        init();
        return mView;
    }
    public void init(){
        isChanging = true;
        listView = (RecyclerView) mView.findViewById(R.id.tickleList);
        adapter = new ChangeTicketAdapter(getContext(),true);
        listView.setAdapter(adapter);
        mView.findViewById(R.id.changing).setSelected(true);

        llm = new LinearLayoutManager(getContext());
        listView.setLayoutManager(llm);

        mView.findViewById(R.id.changing).setOnClickListener(this);
        mView.findViewById(R.id.changed).setOnClickListener(this);
    }
    public void setSort(boolean isChanging) {
        if(this.isChanging == isChanging) return;
        adapter.setChanging(isChanging);
        adapter.notifyDataSetChanged();
        if(isChanging) {
            mView.findViewById(R.id.changing).setSelected(true);
            mView.findViewById(R.id.changed).setSelected(false);
        }else {
            mView.findViewById(R.id.changing).setSelected(false);
            mView.findViewById(R.id.changed).setSelected(true);
        }

        llm.scrollToPositionWithOffset(0, 0);
        this.isChanging = isChanging;
    }
}
