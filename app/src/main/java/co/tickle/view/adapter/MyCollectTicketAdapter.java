package co.tickle.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.ContextUtils;
import co.tickle.utils.Utils;
import co.tickle.view.common.BaseRecyclerAdapter;
import co.tickle.view.popup.CouponPopup;

/**
 * Created by zuby on 2016. 7. 7..
 */
public class MyCollectTicketAdapter extends BaseRecyclerAdapter {

    List<Ticket> mTickets;

    public MyCollectTicketAdapter(Context context, List<Ticket> mTickets) {
        mContext = context;
        this.mTickets = mTickets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == LIST_VIEW_TYPE_HEADER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_have_header, parent, false);
            return new ListHeaderViewHolder(itemView);
        }
        else itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_have_ticket, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(!isHeader(position)){
            ListItemViewHolder h = (ListItemViewHolder) holder;
            Ticket ticket = mTickets.get(position-1);
            Glide.with(mContext).load(ticket.getThumbnail()).into(h.thumbnailView);
            h.nameView.setText(ticket.getName());
            h.quantityView.setText(Utils.getPriceToString(ticket.getQuantity()* CodeDefinition.TICKLE_PRICE));
            h.itemView.setTag(ticket);
            h.itemView.setOnClickListener(this);
            if(position%3 ==0 ) {
                int padding  = ContextUtils.pxFromDp(mContext,15);
                h.itemView.setPadding(padding,0,padding,0);
            }
        }else {
            ListHeaderViewHolder h = (ListHeaderViewHolder) holder;
            h.tickleCountView.setText(mTickets.size()+"");
            h.interestCountView.setText("0");
            if(h.adapter == null) {
                h.adapter =new InterestTicketAdapter(mContext);
                h.interestListView.setAdapter(h.adapter);
            }
            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            h.interestListView.setNestedScrollingEnabled(true);
            h.interestListView.setLayoutManager(llm);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mTickets.size()+1;
    }

    class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        TextView tickleCountView;
        RecyclerView interestListView;
        TextView interestCountView;
        InterestTicketAdapter adapter;
        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            tickleCountView = (TextView) itemView.findViewById(R.id.tickleCountView);
            interestCountView = (TextView) itemView.findViewById(R.id.interestCountView);
            interestListView= (RecyclerView) itemView.findViewById(R.id.interestListView);
        }
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        ImageView thumbnailView;
        TextView nameView;
        TextView quantityView;
        public ListItemViewHolder(View itemView) {
            super(itemView);
            thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnailView);
            nameView = (TextView) itemView.findViewById(R.id.nameView);
            quantityView = (TextView) itemView.findViewById(R.id.quantityView);
        }
    }

    @Override
    public void onClick(View v) {

        new CouponPopup(mContext,(Ticket)v.getTag()).show();
    }
}
