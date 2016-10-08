package co.tickle.view.change.myticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import co.tickle.R;
import co.tickle.model.Quantity;
import co.tickle.model.Ticket;
import co.tickle.model.Trade;
import co.tickle.network.controller.TicketController;
import co.tickle.network.controller.TradeController;
import co.tickle.network.form.ProposeResponseForm;
import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.TicketInfoResponseForm;
import co.tickle.network.form.TradeListResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import co.tickle.view.adapter.ChangeTicketAdapter;
import co.tickle.view.common.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-08-02.
 */
public class ChangeTicketActivity extends BaseActivity {
    int mQuantity;
    int mQuantityPosition;
    Ticket fromTicket;
    Ticket toTicket;
    List<Quantity> quantities;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.findLayout) {
            Intent intent = new Intent(this, FindTicketActivity.class);
            intent.putExtra(CodeDefinition.QUANTITY_PARAM, mQuantity);
            startActivityForResult(intent, CodeDefinition.FIND_REQUEST_CODE);
        } else if (v.getId() == R.id.suggestButton) {
            if (toTicket == null || fromTicket == null) return;

            suggestTicket();

        }
    }

    public void suggestTicket() {
        TradeController.getInstance(this).propose(fromTicket.getTicket_id(), toTicket.get_id(), mQuantity, new Callback<ProposeResponseForm>() {
            @Override
            public void onResponse(Call<ProposeResponseForm> call, Response<ProposeResponseForm> response) {
                if (response.body().getCode() == 200) {
                    if (response.body().getResult())
                        Toast.makeText(ChangeTicketActivity.this, "교환되었습니다.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ChangeTicketActivity.this, "제안하였습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ProposeResponseForm> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void initSuggestLayout(List<Trade> tickets) {
        findViewById(R.id.suggestMsgLayout).setVisibility(View.GONE);
        RecyclerView suggestView = (RecyclerView) findViewById(R.id.suggestListView);
        suggestView.setVisibility(View.VISIBLE);
        ChangeTicketAdapter adapter = new ChangeTicketAdapter(this, tickets, true);
        suggestView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        suggestView.setLayoutManager(llm);
    }

    public void getSuggestList() {

        TradeController.getInstance(this).getList("0", toTicket.getTicket_id(), fromTicket.getTicket_id(), new Callback<TradeListResponseForm>() {
            @Override
            public void onResponse(Call<TradeListResponseForm> call, Response<TradeListResponseForm> response) {
                if (response.body().getCode() == 200) {
                    initSuggestLayout(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<TradeListResponseForm> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ticket);
        initData();
        findViewById(R.id.suggestButton).setOnClickListener(this);
    }

    public void initData() {
        String _id = getIntent().getStringExtra(CodeDefinition.ID_PARAM);
        TicketController.getInstance(this).getMyInfo(_id, new Callback<TicketInfoResponseForm>() {
            @Override
            public void onResponse(Call<TicketInfoResponseForm> call, Response<TicketInfoResponseForm> response) {
                if (response.body().getCode() == 200) {
                    fromTicket = response.body().getResult();
                    initFromTicket();
                    mQuantity = fromTicket.getQuantity();
                    findViewById(R.id.findLayout).setOnClickListener(ChangeTicketActivity.this);
                }
            }

            @Override
            public void onFailure(Call<TicketInfoResponseForm> call, Throwable t) {

            }
        });
    }

    public void getTicket(String id) {
        findViewById(R.id.suggestButton).setBackgroundColor(ContextCompat.getColor(this, R.color.green));
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
        Spinner quantityView = (Spinner) view.findViewById(R.id.quantityView);

        Glide.with(this).load(ticket.getThumbnail()).into(thumbnailView);
        companyNameView.setText(ticket.getCompany());
        nameView.setText(ticket.getName());
        orgPriceView.setText(Utils.getPriceToString(ticket.getOrg_price()));
        discountView.setText(Utils.getPriceToString(ticket.getDiscount()));

        quantities = new ArrayList<>();
        if (fromTicket.getQuantity() >= 20) {
            for (int i = (fromTicket.getQuantity() / 10) * 10; i >= 10; i -= 10)
                quantities.add(new Quantity(i));
        } else {
            for (int i = fromTicket.getQuantity(); i >= 1; i -= 1)
                quantities.add(new Quantity(i));
        }


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.element_spinner, quantities);
        quantityView.setAdapter(adapter);
        quantityView.setSelection(mQuantityPosition);
        quantityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mQuantity = quantities.get(position).getQuantity();
                mQuantityPosition = position;
                ((Spinner) findViewById(R.id.toTicketLayout).findViewById(R.id.quantityView)).setSelection(position);
                ((Spinner) findViewById(R.id.fromTicketLayout).findViewById(R.id.quantityView)).setSelection(position);

//                if(toTicket!=null)
//                    getSuggestList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void initFromTicket() {
        LinearLayout fromTicketLayout = (LinearLayout) findViewById(R.id.fromTicketLayout);
        fromTicketLayout.removeAllViews();
        fromTicketLayout.addView(initTicket(fromTicket));
    }

    public void initToTicket() {
        LinearLayout toTicketLayout = (LinearLayout) findViewById(R.id.toTicketLayout);
        toTicketLayout.removeAllViews();
        toTicketLayout.addView(initTicket(toTicket));
        getSuggestList();
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
