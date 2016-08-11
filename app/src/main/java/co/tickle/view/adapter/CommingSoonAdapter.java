package co.tickle.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tickle.view.common.BaseRecyclerAdapter;

/**
 * Created by zuby on 2016-07-07.
 */
public class CommingSoonAdapter extends BaseRecyclerAdapter {
    Context mContext;
    int mLayout;
    View.OnClickListener listener;
    public CommingSoonAdapter(Context mContext, int layout, View.OnClickListener listener) {
        this.mContext = mContext;
        this.mLayout = layout;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayout,parent,false);
        itemView.setOnClickListener(this);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
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
