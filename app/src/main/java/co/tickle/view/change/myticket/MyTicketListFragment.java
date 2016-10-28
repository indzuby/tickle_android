package co.tickle.view.change.myticket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.network.controller.TicketController;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.utils.CodeDefinition;
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
    List<Ticket> mFavoriteTickets;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_myticket,container,false);
        getActivity().registerReceiver(initDataBroadCastReceiver,new IntentFilter(CodeDefinition.FAVORITE_TICKLE_BROADCAST));
        initData();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        getActivity().registerReceiver(initDataBroadCastReceiver,new IntentFilter(CodeDefinition.FAVORITE_TICKLE_BROADCAST));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(initDataBroadCastReceiver);
    }

    public void initData(){
        String category = getArguments().getString(CodeDefinition.CATEGORY_PARAM);
        TicketController.getInstance(getContext()).getMyList(category,false,new Callback<TicketListResponseForm>() {
            @Override
            public void onResponse(Call<TicketListResponseForm> call, Response<TicketListResponseForm> response) {
                if(response.body().getCode()==200) {
                    mTickets = response.body().getResult();
                    initDataFavorite();
                }
            }

            @Override
            public void onFailure(Call<TicketListResponseForm> call, Throwable t) {

            }
        });
    }
    public void initDataFavorite(){
        String category = getArguments().getString(CodeDefinition.CATEGORY_PARAM);
        TicketController.getInstance(getContext()).getMyList(category,true,new Callback<TicketListResponseForm>() {
            @Override
            public void onResponse(Call<TicketListResponseForm> call, Response<TicketListResponseForm> response) {
                if(response.body().getCode()==200) {
                    mFavoriteTickets = response.body().getResult();
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
        adapter = new MyCollectTicketAdapter(getActivity(),mTickets,mFavoriteTickets);
        gridView.setAdapter(adapter);
    }
    private final BroadcastReceiver initDataBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("BROADCAST","ACTION");
            if(intent.getAction().equals(CodeDefinition.FAVORITE_TICKLE_BROADCAST))
                initData();
        }
    };
}
