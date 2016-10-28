package co.tickle.view.change.condition;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.tickle.R;
import co.tickle.model.Trade;
import co.tickle.network.controller.TradeController;
import co.tickle.network.form.TradeListResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.view.adapter.ConditionTicketAdapter;
import co.tickle.view.common.BaseFragment;
import co.tickle.view.main.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-06.
 */
public class ChangeConditionFragment extends BaseFragment implements MainActivity.onKeyBackPressedListener {
    boolean isChanging;
    ConditionTicketAdapter adapter;
    RecyclerView listView;
    LinearLayoutManager llm;
    List<Trade> mTrades;
    boolean isCancel = false;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.changing)
            setSort(true);
        else if(v.getId() == R.id.changed)
            setSort(false);
        else if(v.getId() == R.id.cancelButton) {
            adapter.setRemove(!adapter.isRemove());
            adapter.notifyDataSetChanged();
            isCancel = !isCancel;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_change_condition,container,false);
        getActivity().registerReceiver(initDataBroadCastReceiver,new IntentFilter(CodeDefinition.FAVORITE_TICKLE_BROADCAST));
        init();
        return mView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(initDataBroadCastReceiver);
    }

    public void initData(final boolean isChanging){
        String status = "0";
        if(!isChanging) status = "1";
        TradeController.getInstance(getContext()).getMyList(status,new Callback<TradeListResponseForm>() {
            @Override
            public void onResponse(Call<TradeListResponseForm> call, Response<TradeListResponseForm> response) {
                if(response.body().getCode() == 200){
                    mTrades = response.body().getResult();
                    adapter = new ConditionTicketAdapter(getContext(),mTrades,isChanging);
                    listView.setAdapter(adapter);
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
        adapter = new ConditionTicketAdapter(getContext(),mTrades,true);
        listView.setAdapter(adapter);
        mView.findViewById(R.id.changing).setSelected(true);

        llm = new LinearLayoutManager(getContext());
        listView.setLayoutManager(llm);

        mView.findViewById(R.id.changing).setOnClickListener(this);
        mView.findViewById(R.id.changed).setOnClickListener(this);
        mView.findViewById(R.id.cancelButton).setOnClickListener(this);
        initData(isChanging);
    }
    public void setSort(boolean isChanging) {
        if(this.isChanging == isChanging) return;
        if(isChanging) {
            mView.findViewById(R.id.changing).setSelected(true);
            mView.findViewById(R.id.changed).setSelected(false);
            mView.findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);
        }else {
            mView.findViewById(R.id.changing).setSelected(false);
            mView.findViewById(R.id.changed).setSelected(true);
            mView.findViewById(R.id.cancelButton).setVisibility(View.GONE);
        }
        initData(isChanging);
        llm.scrollToPositionWithOffset(0, 0);
        this.isChanging = isChanging;
    }
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        ((MainActivity) getActivity()).setOnKeyBackPressedListener(this);
    }
    @Override
    public void onBack() {
        if (isCancel) {
            isCancel = !isCancel;
            adapter.setRemove(!adapter.isRemove());
            adapter.notifyDataSetChanged();
        } else {
            MainActivity activity = (MainActivity) getActivity();
            activity.setOnKeyBackPressedListener(null);
            activity.onBackPressed();
        }
    }
    private final BroadcastReceiver initDataBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("BROADCAST","ACTION");
            if(intent.getAction().equals(CodeDefinition.CONDITION_TICKLE_BROADCAST))
                initData(isChanging);
        }
    };
}
