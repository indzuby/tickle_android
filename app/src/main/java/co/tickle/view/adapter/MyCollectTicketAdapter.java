package co.tickle.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import co.tickle.view.common.BaseRecyclerAdapter;
import co.tickle.view.popup.TickleUsePopup;

/**
 * Created by zuby on 2016. 7. 7..
 */
public class MyCollectTicketAdapter extends BaseRecyclerAdapter {

    List<Ticket> mTickets;
    List<Ticket> mFavoriteTickets;
    LinearLayout mFavoriteLayout;
    int pageNo = 0;

    public MyCollectTicketAdapter(Context context, List<Ticket> mTickets, List<Ticket> mFavoriteTickets) {
        mContext = context;
        this.mTickets = mTickets;
        this.mFavoriteTickets = mFavoriteTickets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == LIST_VIEW_TYPE_HEADER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_have_header, parent, false);
            return new ListHeaderViewHolder(itemView);
        } else
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_have_ticket, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isHeader(position)) {
            ListItemViewHolder h = (ListItemViewHolder) holder;
            Ticket ticket = mTickets.get(position - 1);
            Glide.with(mContext).load(ticket.getThumbnail()).into(h.thumbnailView);
            h.nameView.setText(ticket.getName());
            h.quantityView.setText(Utils.getPriceToString(ticket.getQuantity() * CodeDefinition.TICKLE_PRICE));
            h.itemView.setTag(ticket);
            h.itemView.setOnClickListener(this);
//            if(position%3 ==0 ) {
//                int padding  = ContextUtils.pxFromDp(mContext,15);
//                h.itemView.setPadding(padding,0,padding,0);
//            }

            h.newView.setVisibility(View.GONE);
            h.changeView.setVisibility(View.GONE);
            h.usedView.setVisibility(View.GONE);
            if (ticket.getStatus() == 1)
                h.changeView.setVisibility(View.VISIBLE);
            else if (ticket.getStatus() == 2)
                h.newView.setVisibility(View.VISIBLE);
            else if (ticket.getStatus() == 3)
                h.usedView.setVisibility(View.VISIBLE);

        } else {
            ListHeaderViewHolder h = (ListHeaderViewHolder) holder;
            h.tickleCountView.setText(mTickets.size() + "");
            h.favoriteCountView.setText(mFavoriteTickets.size() + "");
            h.itemView.findViewById(R.id.next).setOnClickListener(this);
            h.itemView.findViewById(R.id.before).setOnClickListener(this);
            makeFavoriteTicket();
        }
    }

    public void makeFavoriteTicket() {
        mFavoriteLayout.removeAllViews();
        for (int i = pageNo * 3; i < pageNo * 3 + 3 && i < mFavoriteTickets.size(); i++) {
            Ticket ticket = mFavoriteTickets.get(i);
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_have_ticket, null, false);

            ImageView thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnailView);
            TextView nameView = (TextView) itemView.findViewById(R.id.nameView);
            TextView quantityView = (TextView) itemView.findViewById(R.id.quantityView);
            ImageView newView = (ImageView) itemView.findViewById(R.id.newView);
            ImageView changeView = (ImageView) itemView.findViewById(R.id.changeView);
            ImageView usedView = (ImageView) itemView.findViewById(R.id.usedView);

            Glide.with(mContext).load(ticket.getThumbnail()).into(thumbnailView);
            nameView.setText(ticket.getName());
            quantityView.setText(Utils.getPriceToString(ticket.getQuantity() * CodeDefinition.TICKLE_PRICE));
            newView.setVisibility(View.GONE);
            changeView.setVisibility(View.GONE);
            usedView.setVisibility(View.GONE);
            if (ticket.getStatus() == 1)
                changeView.setVisibility(View.VISIBLE);
            else if (ticket.getStatus() == 2)
                newView.setVisibility(View.VISIBLE);
            else if (ticket.getStatus() == 3)
                usedView.setVisibility(View.VISIBLE);

            itemView.setTag(ticket);
            itemView.setOnClickListener(this);
            itemView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            mFavoriteLayout.addView(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mTickets.size() + 1;
    }

    class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        TextView tickleCountView;
        TextView favoriteCountView;
        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            tickleCountView = (TextView) itemView.findViewById(R.id.tickleCountView);
            favoriteCountView = (TextView) itemView.findViewById(R.id.favoriteCountView);
            mFavoriteLayout = (LinearLayout) itemView.findViewById(R.id.favoriteLayout);
        }
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        ImageView thumbnailView;
        TextView nameView;
        TextView quantityView;
        ImageView newView, changeView, usedView;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnailView);
            nameView = (TextView) itemView.findViewById(R.id.nameView);
            quantityView = (TextView) itemView.findViewById(R.id.quantityView);
            newView = (ImageView) itemView.findViewById(R.id.newView);
            changeView = (ImageView) itemView.findViewById(R.id.changeView);
            usedView = (ImageView) itemView.findViewById(R.id.usedView);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            if ((pageNo + 1) * 3 < mFavoriteTickets.size()) {
                pageNo++;
                makeFavoriteTicket();
            }
        } else if (v.getId() == R.id.before) {
            if (pageNo > 0) {
                pageNo--;
                makeFavoriteTicket();
            }
        } else
            new TickleUsePopup(mContext, (Ticket) v.getTag()).show();
    }
}
