package co.tickle.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.view.common.BaseRecyclerAdapter;

/**
 * Created by zuby on 2016-07-07.
 */
public class ComingSoonAdapter extends BaseRecyclerAdapter {
    Context mContext;
    View.OnClickListener listener;
    List<Ticket> mTickets;
    public ComingSoonAdapter(Context mContext, List<Ticket> mTickets, View.OnClickListener listener) {
        this.mContext = mContext;
        this.mTickets = mTickets;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_coming,parent,false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTickets.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder

        public ListItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}
