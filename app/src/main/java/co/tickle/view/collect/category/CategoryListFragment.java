package co.tickle.view.collect.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.network.controller.TickleController;
import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;
import co.tickle.view.adapter.TickleAdapter;
import co.tickle.view.common.BaseFragment;
import co.tickle.view.popup.AccountPopup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-06.
 */
public class CategoryListFragment extends BaseFragment {

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
        TickleController.getInstance(getContext()).getList(CodeDefinition.MODE_CATEGORY,category, new Callback<TicketListResponseForm>() {
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
        mAdapter = new TickleAdapter(getContext(),mTickets,this);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void downloadTickle(final Ticket ticket){
        TickleController.getInstance(getContext()).dowload(ticket.get_id(), new Callback<ResponseForm>() {
            @Override
            public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response) {
                if(response.body().getCode()==200) {
                    ticket.setQuantity(ticket.getQuantity()-1);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseForm> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.tickleSelectLayout) {
            token = SessionUtils.getString(getContext(),CodeDefinition.TOKEN,"");
            if(token== null || token.length()<=0) {
                Toast.makeText(getContext(),"로그인이 필요합니다.",Toast.LENGTH_SHORT).show();
                new AccountPopup(getActivity()).show();
            }else {
                Ticket ticket = (Ticket) v.getTag();
                downloadTickle(ticket);
            }
        }
    }
}
