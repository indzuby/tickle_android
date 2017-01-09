package co.tickle.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;

import java.util.List;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.model.Trade;
import co.tickle.network.controller.TradeController;
import co.tickle.network.form.ResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import co.tickle.view.common.BaseRecyclerAdapter;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 8..
 */
public class ConditionTicketAdapter extends BaseRecyclerAdapter {

    Context mContext;
    @Setter
    @Getter
    boolean isChanging;
    @Setter
    @Getter
    boolean isRemove = false;

    List<Trade> mTrades;


    public ConditionTicketAdapter(Context mContext, List<Trade> mTrades, boolean isChanging) {
        this.mContext = mContext;
        this.mTrades = mTrades;
        this.isChanging = isChanging;
    }

    @Override
    public int getItemCount() {
        return mTrades.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ListItemViewHolder h = (ListItemViewHolder) viewHolder;

        if (isChanging)
            h.iconCondition.setImageResource(R.mipmap.icon_changing);
        else
            h.iconCondition.setImageResource(R.mipmap.icon_changing_complete);

        if (isRemove)
            h.removeButton.setVisibility(View.VISIBLE);
        else
            h.removeButton.setVisibility(View.GONE);

        Trade trade = mTrades.get(position);



        Ticket fromTicket = trade.getFromTicket();
        Ticket toTicket = trade.getToTicket();

        // from
        Glide.with(mContext).load(fromTicket.getThumbnail()).into(h.fromThumbnailView);
        h.fromCompanyNameView.setText(fromTicket.getCompany());
        h.fromNameView.setText(fromTicket.getName());
        if (isChanging)
            h.fromQuantityView.setText(Utils.getPriceToString(trade.getQuantity() * CodeDefinition.TICKLE_PRICE));
        else
            h.fromQuantityView.setText(Utils.getPriceToString(trade.getFromQuantity() * CodeDefinition.TICKLE_PRICE));

        // to

        Glide.with(mContext).load(toTicket.getThumbnail()).into(h.toThumbnailView);
        h.toCompanyNameView.setText(toTicket.getCompany());
        h.toNameView.setText(toTicket.getName());
        if (isChanging)
            h.toQuantityView.setText(Utils.getPriceToString((int) Math.ceil((double)trade.getQuantity()/trade.getRate()) * CodeDefinition.TICKLE_PRICE));
        else
            h.toQuantityView.setText(Utils.getPriceToString(trade.getToQuantity() * CodeDefinition.TICKLE_PRICE));
        h.removeButton.setTag(position);
        h.removeButton.setOnClickListener(this);
        h.dateTimeView.setText(Utils.dateTimeToString(trade.getDateTime()));

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
        if (v.getId() == R.id.removeButton) {

            final int position = (Integer) v.getTag();

            AlertDialog.Builder alertDlg = new AlertDialog.Builder(mContext);

            alertDlg.setMessage("교환을 취소하시겠습니까?");
            alertDlg.setPositiveButton(mContext.getString(R.string.confirm), new DialogInterface.OnClickListener() { // 확인 버튼
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    TradeController.getInstance(mContext).cancel(mTrades.get(position).get_id(), new Callback<ResponseForm>() {
                        @Override
                        public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response) {
                            if (response.body().getCode() == 200) {
                                Toast.makeText(mContext, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                                mTrades.remove(position);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseForm> call, Throwable t) {

                        }
                    });

                }
            });
            alertDlg.setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() { // 취소 버튼
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {

                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDlg.create();
            alert.show();
        }
    }
}
