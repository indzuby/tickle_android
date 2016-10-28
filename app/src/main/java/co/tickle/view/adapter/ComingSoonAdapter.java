package co.tickle.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Seconds;

import java.util.ArrayList;
import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import co.tickle.view.common.BaseRecyclerAdapter;

/**
 * Created by zuby on 2016-07-07.
 */
public class ComingSoonAdapter extends BaseRecyclerAdapter {
    public final static int LIST_VIEW_TYPE_COMING = 3;
    public final static int LIST_VIEW_TYPE_OPEN = 4;
    Context mContext;
    View.OnClickListener listener;
    List<Ticket> mTickets;
    TextView[] mRestTimes;
    public ComingSoonAdapter(Context mContext, List<Ticket> mTickets, View.OnClickListener listener) {
        this.mContext = mContext;
        this.mTickets = mTickets;
        this.listener = listener;
        mRestTimes = new TextView[mTickets.size()];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == LIST_VIEW_TYPE_COMING)
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_coming,parent,false);
        else
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_get,parent,false);

        return new ListItemViewHolder(itemView,viewType);
    }


    @Override
    public int getItemViewType(int position) {
        DateTime nowTime = new DateTime();
        DateTime openTime = new DateTime(mTickets.get(position).getOpendate());

        if(nowTime.getMillis()>openTime.getMillis())
            return LIST_VIEW_TYPE_OPEN;
        else
            return LIST_VIEW_TYPE_COMING;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemViewHolder h = (ListItemViewHolder) holder;

        Ticket ticket = mTickets.get(position);

        Glide.with(mContext).load(ticket.getThumbnail()).into(h.thumbnailView);
        h.companyNameView.setText(ticket.getCompany());
        h.nameView.setText(ticket.getName());
        h.orgPriceView.setText(Utils.getPriceToString(ticket.getOrg_price()));
        h.discountView.setText(Utils.getPriceToString(ticket.getDiscount()));
        h.quantityView.setText(Utils.getPriceToString(ticket.getQuantity()* CodeDefinition.TICKLE_PRICE));

        if(h.viewType == LIST_VIEW_TYPE_COMING) {
            long seconds = (new DateTime(ticket.getOpendate()).getMillis()-new DateTime().getMillis())/1000;
            h.restTimeView.setText(Utils.getRestTime(seconds));
            mRestTimes[position] = h.restTimeView;
        }else {
            h.itemView.findViewById(R.id.tickleSelectLayout).setTag(ticket);
            h.itemView.findViewById(R.id.tickleSelectLayout).setOnClickListener(listener);
        }
        h.itemView.findViewById(R.id.tickleInfoLayout).setTag(ticket);
        h.itemView.findViewById(R.id.tickleInfoLayout).setOnClickListener(listener);
    }
    public void restTime(int position){

        long seconds = (new DateTime(mTickets.get(position).getOpendate()).getMillis()-new DateTime().getMillis())/1000;
        mRestTimes[position].setText(Utils.getRestTime(seconds));
    }


    @Override
    public int getItemCount() {
        return mTickets.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        int viewType;
        ImageView thumbnailView;
        TextView companyNameView;
        TextView nameView;
        TextView orgPriceView;
        TextView discountView;
        TextView quantityView;
        TextView restTimeView;
        public ListItemViewHolder(View itemView,int viewType)
        {
            super(itemView);
            this.viewType = viewType;
            thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnailView);
            companyNameView = (TextView) itemView.findViewById(R.id.companyNameView);
            nameView = (TextView) itemView.findViewById(R.id.nameView);
            orgPriceView = (TextView) itemView.findViewById(R.id.orgPriceView);
            discountView = (TextView) itemView.findViewById(R.id.discountView);
            quantityView = (TextView) itemView.findViewById(R.id.quantityView);
            if(viewType == LIST_VIEW_TYPE_COMING)
                restTimeView = (TextView) itemView.findViewById(R.id.restTimeView);


        }
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }
}
