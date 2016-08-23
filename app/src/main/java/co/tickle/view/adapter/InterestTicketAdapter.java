package co.tickle.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.view.common.BaseRecyclerAdapter;
import co.tickle.view.popup.CouponPopup;

/**
 * Created by zuby on 2016. 7. 7..
 */
public class InterestTicketAdapter extends BaseRecyclerAdapter {

    public InterestTicketAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == LIST_VIEW_TYPE_FOOTER || getItemCount() == 1) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest_add, parent, false);
        }
        else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest_ticket, parent, false);
            itemView.setOnClickListener(this);
        }
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 1    ;
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder

        public ListItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onClick(View v) {
        new CouponPopup(mContext, (Ticket)v.getTag() ).show();
    }
}
