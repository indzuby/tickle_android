package co.tickle.view.collect.commingsoon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.network.controller.TickleController;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.view.adapter.ComingSoonAdapter;
import co.tickle.view.adapter.TickleAdapter;
import co.tickle.view.common.BaseFragment;
import co.tickle.view.popup.AdvertisePopup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-06.
 */
public class ComingListFragment extends BaseFragment {

    List<Ticket> mTickets;
    TickleAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_ticket_list,container,false);
        initData();
        return mView;
    }

    public void initData(){
        String category = getArguments().getString(CodeDefinition.CATEGORY_PARAM);
        TickleController.getInstance(getContext()).getList("commingsoon",category, new Callback<TicketListResponseForm>() {
            @Override
            public void onResponse(Call<TicketListResponseForm> call, Response<TicketListResponseForm> response) {
                if(response.body().getCode() == 200) {
                    mTickets = response.body().getResult();
                    init();
                }
            }

            @Override
            public void onFailure(Call<TicketListResponseForm> call, Throwable t) {

            }
        });
    }
    public void init(){
        super.init();
        RecyclerView listView = (RecyclerView) mView.findViewById(R.id.tickleList);
        listView.setAdapter(new ComingSoonAdapter(getContext(),mTickets,this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        new AdvertisePopup(getActivity()).show();
    }
}
