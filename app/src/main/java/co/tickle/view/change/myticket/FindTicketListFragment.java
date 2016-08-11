package co.tickle.view.change.myticket;

import android.app.Activity;
import android.content.Intent;
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
import co.tickle.network.controller.TicketController;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.view.adapter.FindTicketAdapter;
import co.tickle.view.common.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-06.
 */
public class FindTicketListFragment extends BaseFragment {



    List<Ticket> mTickets;
    FindTicketAdapter mAdapter;
    int mQuantity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_ticket_list,container,false);
        initData();
        return mView;
    }
    public void initData(){
        String category = getArguments().getString(CodeDefinition.CATEGORY_PARAM);
        TicketController.getInstance(getContext()).getList(category, new Callback<TicketListResponseForm>() {
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
        mQuantity = getActivity().getIntent().getIntExtra(CodeDefinition.QUANTITY_PARAM,0);
        RecyclerView listView = (RecyclerView) mView.findViewById(R.id.tickleList);
        mAdapter = new FindTicketAdapter(getContext(),mTickets,mQuantity,itemListener);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
    View.OnClickListener itemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Ticket ticket = (Ticket) v.getTag();
            Intent intent = new Intent();
            intent.putExtra(CodeDefinition.ID_PARAM,ticket.get_id());
            getActivity().setResult(Activity.RESULT_OK,intent);
            getActivity().finish();
        }
    };
}
