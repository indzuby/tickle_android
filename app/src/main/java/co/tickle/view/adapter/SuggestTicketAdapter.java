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
import co.tickle.model.Trade;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import co.tickle.view.common.BaseRecyclerAdapter;

/**
 * Created by zuby on 2016. 7. 8..
 */
public class SuggestTicketAdapter extends BaseRecyclerAdapter {

    Context mContext;

    List<Trade> mTrades;
    View.OnClickListener listener;

    public SuggestTicketAdapter(Context mContext, List<Trade> mTrades, View.OnClickListener listener) {
        this.mContext = mContext;
        this.mTrades = mTrades;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mTrades.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ListItemViewHolder h = (ListItemViewHolder) viewHolder;
        h.iconCondition.setImageResource(R.mipmap.icon_changing);
        Trade trade = mTrades.get(position);


        Ticket fromTicket = trade.getToTicket();
        Ticket toTicket = trade.getFromTicket();

        // from
        Glide.with(mContext).load(fromTicket.getThumbnail()).into(h.fromThumbnailView);
        h.fromCompanyNameView.setText(fromTicket.getCompany());
        h.fromNameView.setText(fromTicket.getName());
        h.fromQuantityView.setText(Utils.getPriceToString((int) Math.ceil((double) trade.getQuantity() / trade.getRate()) * CodeDefinition.TICKLE_PRICE));

        // to

        Glide.with(mContext).load(toTicket.getThumbnail()).into(h.toThumbnailView);
        h.toCompanyNameView.setText(toTicket.getCompany());
        h.toNameView.setText(toTicket.getName());
        h.toQuantityView.setText(Utils.getPriceToString(trade.getQuantity() * CodeDefinition.TICKLE_PRICE));
        h.removeButton.setTag(position);
        h.removeButton.setOnClickListener(this);
        h.dateTimeView.setText(Utils.dateTimeToString(trade.getDateTime()));
        h.itemView.findViewById(R.id.tradeLayout).setTag(trade.get_id());
        h.itemView.findViewById(R.id.tradeLayout).setOnClickListener(listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_condition, parent, false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }
    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder

        ImageView fromThumbnailView;
        TextView fromCompanyNameView;
        TextView fromNameView;
        TextView fromQuantityView;

        ImageView toThumbnailView;
        TextView toCompanyNameView;
        TextView toNameView;
        TextView toQuantityView;

        ImageView iconCondition;
        ImageView removeButton;
        TextView dateTimeView;



        public ListItemViewHolder(View v) {
            super(v);

            iconCondition = (ImageView) v.findViewById(R.id.iconCondition);

            fromThumbnailView = (ImageView) v.findViewById(R.id.fromThumbnailView);
            fromCompanyNameView = (TextView) v.findViewById(R.id.fromCompanyNameView);
            fromNameView = (TextView) v.findViewById(R.id.fromNameView);
            fromQuantityView = (TextView) v.findViewById(R.id.fromQuantityView);

            toThumbnailView = (ImageView) v.findViewById(R.id.toThumbnailView);
            toCompanyNameView = (TextView) v.findViewById(R.id.toCompanyNameView);
            toNameView = (TextView) v.findViewById(R.id.toNameView);
            toQuantityView = (TextView) v.findViewById(R.id.toQuantityView);

            removeButton = (ImageView) v.findViewById(R.id.removeButton);

            dateTimeView = (TextView) v.findViewById(R.id.dateTimeView);
        }
    }
    @Override
    public void onClick(View v) {
    }
}
