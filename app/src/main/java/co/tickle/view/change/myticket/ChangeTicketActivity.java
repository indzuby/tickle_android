package co.tickle.view.change.myticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.tickle.R;
import co.tickle.model.Ticket;
import co.tickle.network.controller.TicketController;
import co.tickle.network.form.TicketInfoResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import co.tickle.view.common.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-08-02.
 */
public class ChangeTicketActivity extends BaseActivity {
    int mQuantity;
    Ticket mTicket;
    Ticket toTicket;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.findLayout) {
            Intent intent = new Intent(this, FindTicketActivity.class);
            intent.putExtra(CodeDefinition.QUANTITY_PARAM, mQuantity);
            startActivityForResult(intent, CodeDefinition.FIND_REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ticket);
        initData();
    }

    public void initData() {
        String _id = getIntent().getStringExtra(CodeDefinition.ID_PARAM);
        TicketController.getInstance(this).getMyInfo(_id, new Callback<TicketInfoResponseForm>() {
            @Override
            public void onResponse(Call<TicketInfoResponseForm> call, Response<TicketInfoResponseForm> response) {
                if (response.body().getCode() == 200) {
                    mTicket = response.body().getResult();
                    initFromTicket();
                    mQuantity = mTicket.getQuantity();
                    findViewById(R.id.findLayout).setOnClickListener(ChangeTicketActivity.this);
                }
            }

            @Override
            public void onFailure(Call<TicketInfoResponseForm> call, Throwable t) {

            }
        });
    }

    public void getTicket(String id) {
        TicketController.getInstance(this).getInfo(id, new Callback<TicketInfoResponseForm>() {
            @Override
            public void onResponse(Call<TicketInfoResponseForm> call, Response<TicketInfoResponseForm> response) {
                if (response.body().getCode() == 200) {
                    toTicket = response.body().getResult();
                    initToTicket();
                }
            }

            @Override
            public void onFailure(Call<TicketInfoResponseForm> call, Throwable t) {

            }
        });


    }

    @Override
    public void init() {
        super.init();
    }

    public View initTicket(Ticket ticket) {
        View view = LayoutInflater.from(this).inflate(R.layout.element_change_ticket, null, false);
        ImageView thumbnailView = (ImageView) view.findViewById(R.id.thumbnailView);
        TextView companyNameView = (TextView) view.findViewById(R.id.companyNameView);
        TextView nameView = (TextView) view.findViewById(R.id.nameView);
        TextView orgPriceView = (TextView) view.findViewById(R.id.orgPriceView);
        TextView discountView = (TextView) view.findViewById(R.id.discountView);
        TextView quantityView = (TextView) view.findViewById(R.id.quantityView);

        Glide.with(this).load(ticket.getThumbnail()).into(thumbnailView);
        companyNameView.setText(ticket.getCompany());
        nameView.setText(ticket.getName());
        orgPriceView.setText(Utils.getPriceToString(ticket.getOrg_price()));
        discountView.setText(Utils.getPriceToString(ticket.getDiscount()));
        quantityView.setText(Utils.getPriceToString(mTicket.getQuantity() * CodeDefinition.TICKLE_PRICE));
        return view;
    }

    public void initFromTicket() {
        LinearLayout fromTicketLayout = (LinearLayout) findViewById(R.id.fromTicketLayout);
        fromTicketLayout.removeAllViews();
        fromTicketLayout.addView(initTicket(mTicket));
    }

    public void initToTicket() {
        LinearLayout toTicketLayout = (LinearLayout) findViewById(R.id.toTicketLayout);
        toTicketLayout.removeAllViews();
        toTicketLayout.addView(initTicket(toTicket));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == CodeDefinition.FIND_REQUEST_CODE) {
            String _id = data.getStringExtra(CodeDefinition.ID_PARAM);
            getTicket(_id);
        }
    }
}
