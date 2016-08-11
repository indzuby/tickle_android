package co.tickle.view.adapter;

import android.content.Context;
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
import co.tickle.utils.Utils;
import co.tickle.view.common.BaseRecyclerAdapter;

/**
 * Created by zuby on 2016-08-02.
 */
public class FindTicketAdapter extends BaseRecyclerAdapter {
    Context mContext;
    List<Ticket> mTickets;
    View.OnClickListener listener;
    int mQuantity;
    public FindTicketAdapter(Context mContext, List<Ticket> mTickets,int mQuantity, View.OnClickListener listener) {
        this.mContext = mContext;
        this.mTickets = mTickets;
        this.mQuantity = mQuantity;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_find,parent,false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemViewHolder h = (ListItemViewHolder) holder;

        Ticket ticket = mTickets.get(position);

        h.itemView.setTag(ticket);
        h.itemView.setOnClickListener(listener);
        Glide.with(mContext).load(ticket.getThumbnail()).into(h.thumbnailView);
        h.companyNameView.setText(ticket.getCompany());
        h.nameView.setText(ticket.getName());
        h.orgPriceView.setText(Utils.getPriceToString(ticket.getOrg_price()));
        h.discountView.setText(Utils.getPriceToString(ticket.getDiscount()));
        h.quantityView.setText(Utils.getPriceToString(mQuantity* CodeDefinition.TICKLE_PRICE));

    }

    @Override
    public int getItemCount() {
        return mTickets.size();
    }

    @Override
    public void onClick(View v) {

    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        ImageView thumbnailView;
        TextView companyNameView;
        TextView nameView;
        TextView orgPriceView;
        TextView discountView;
        TextView quantityView;
        public ListItemViewHolder(View itemView) {
            super(itemView);
            thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnailView);
            companyNameView = (TextView) itemView.findViewById(R.id.companyNameView);
            nameView = (TextView) itemView.findViewById(R.id.nameView);
            orgPriceView = (TextView) itemView.findViewById(R.id.orgPriceView);
            discountView = (TextView) itemView.findViewById(R.id.discountView);
            quantityView = (TextView) itemView.findViewById(R.id.quantityView);
        }
    }
}
