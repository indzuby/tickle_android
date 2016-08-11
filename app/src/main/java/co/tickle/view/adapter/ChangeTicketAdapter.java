package co.tickle.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import co.tickle.R;
import co.tickle.view.common.BaseRecyclerAdapter;
import lombok.Setter;

/**
 * Created by zuby on 2016. 7. 8..
 */
public class ChangeTicketAdapter extends BaseRecyclerAdapter {

    Context mContext;
    @Setter
    boolean isChanging;

    public ChangeTicketAdapter(Context mContext, boolean isChanging) {
        this.mContext = mContext;
        this.isChanging = isChanging;
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ListItemViewHolder holder = (ListItemViewHolder) viewHolder;

        if(isChanging)
            holder.iconCondition.setImageResource(R.mipmap.icon_changing);
        else
            holder.iconCondition.setImageResource(R.mipmap.icon_changing_complete);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_condition, parent, false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder
        ImageView iconCondition;
        public ListItemViewHolder(View itemView) {
            super(itemView);

            iconCondition = (ImageView) itemView.findViewById(R.id.iconCondition);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
