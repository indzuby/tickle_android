package co.tickle.view.popup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.network.controller.TicketController;
import co.tickle.network.form.ResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import co.tickle.view.change.myticket.ChangeTicketActivity;
import co.tickle.view.change.myticket.UseTicketActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 8..
 */
public class TickleInfoPopup extends Dialog implements View.OnClickListener{
    Ticket mTicket;
    public TickleInfoPopup(Context context, Ticket ticket) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        mTicket = ticket;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_tickle_info);
        init();
    }
    public void init(){
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.color.black50));
        findViewById(R.id.closeButton).setOnClickListener(this);
        findViewById(R.id.layout).setOnClickListener(this);
        setCanceledOnTouchOutside(true);

        ImageView thumbnailView = (ImageView) findViewById(R.id.thumbnailView);
        TextView companyNameView = (TextView) findViewById(R.id.companyNameView);
        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView orgPriceView = (TextView) findViewById(R.id.orgPriceView);
        TextView discountView = (TextView) findViewById(R.id.discountView);
        TextView quantityView = (TextView) findViewById(R.id.quantityView);

        Glide.with(getContext()).load(mTicket.getThumbnail()).into(thumbnailView);
        companyNameView.setText(mTicket.getCompany());
        nameView.setText(mTicket.getName());
        orgPriceView.setText(Utils.getPriceToString(mTicket.getOrg_price()));
        discountView.setText(Utils.getPriceToString(mTicket.getDiscount()));
        quantityView.setText(Utils.getPriceToString(mTicket.getQuantity()* CodeDefinition.TICKLE_PRICE));
        findViewById(R.id.confirmButton).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        dismiss();
    }
}
