package co.tickle.view.change.myticket;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.network.controller.TicketController;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.view.adapter.InterestTicketAdapter;
import co.tickle.view.adapter.MyCollectTicketAdapter;
import co.tickle.view.common.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-06.
 */
public class MyTicketListFragment extends BaseFragment {
    MyCollectTicketAdapter adapter;
    GridLayoutManager glm;

    List<Ticket> mTickets;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_myticket,container,false);
        initData();
        return mView;
    }
    public void initData(){
        String category = getArguments().getString(CodeDefinition.CATEGORY_PARAM);
        TicketController.getInstance(getContext()).getMyList(category,new Callback<TicketListResponseForm>() {
            @Override
            public void onResponse(Call<TicketListResponseForm> call, Response<TicketListResponseForm> response) {
                if(response.body().getCode()==200) {
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
        TextView interestCountView = (TextView) mView.findViewById(R.id.interestCountView);
        interestCountView.setText("0");
        RecyclerView listView = (RecyclerView) mView.findViewById(R.id.interestListView);
        listView.setAdapter(new InterestTicketAdapter(getActivity()));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        listView.setNestedScrollingEnabled(true);
        listView.setLayoutManager(llm);

        final RecyclerView gridView = (RecyclerView) mView.findViewById(R.id.myCollectGrid);
        glm = new GridLayoutManager(getContext(),3);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position==0)
                    return 3;
                return 1;
            }
        });
        gridView.setLayoutManager(glm);
        gridView.setHasFixedSize(true);
        adapter = new MyCollectTicketAdapter(getActivity(),mTickets);
        gridView.setAdapter(adapter);

    }
}
