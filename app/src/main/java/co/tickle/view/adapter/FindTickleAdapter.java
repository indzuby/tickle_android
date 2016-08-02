package co.tickle.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tickle.R;
import co.tickle.view.common.BaseRecyclerAdapter;

/**
 * Created by zuby on 2016-08-02.
 */
public class FindTickleAdapter extends BaseRecyclerAdapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tickle_find,parent,false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onClick(View v) {

    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder

        public ListItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
