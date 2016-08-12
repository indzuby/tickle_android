package co.tickle.view.change.condition;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.tickle.R;
import co.tickle.model.Trade;
import co.tickle.network.controller.TradeController;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.network.form.TradeListResponseForm;
import co.tickle.view.adapter.ChangeTicketAdapter;
import co.tickle.view.common.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

/**
 * Created by zuby on 2016-07-06.
 */
public class ChangeConditionFragment extends BaseFragment {
    boolean isChanging;
    ChangeTicketAdapter adapter;
    RecyclerView listView;
    LinearLayoutManager llm;
    List<Trade> mTrades;

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
    public void initData(boolean isChanging){
        String status = "0";
        if(!isChanging) status = "1";
        TradeController.getInstance(getContext()).getMyList(status, "", "", new Callback<TradeListResponseForm>() {
            @Override
            public void onResponse(Call<TradeListResponseForm> call, Response<TradeListResponseForm> response) {
                if(response.body().getCode() == 200){
                    mTrades = response.body().getResult();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TradeListResponseForm> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void init(){
        isChanging = true;
        mTrades = new ArrayList<>();
        listView = (RecyclerView) mView.findViewById(R.id.tickleList);
        adapter = new ChangeTicketAdapter(getContext(),mTrades,true);
        listView.setAdapter(adapter);
        mView.findViewById(R.id.changing).setSelected(true);

        llm = new LinearLayoutManager(getContext());
        listView.setLayoutManager(llm);

        mView.findViewById(R.id.changing).setOnClickListener(this);
        mView.findViewById(R.id.changed).setOnClickListener(this);
        initData(isChanging);
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
        initData(isChanging);
        llm.scrollToPositionWithOffset(0, 0);
        this.isChanging = isChanging;
    }
}
