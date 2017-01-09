package co.tickle.view.change.myticket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import co.tickle.network.form.TicketInfoResponseForm;
import co.tickle.network.form.TradeApplyResponseForm;
import co.tickle.network.form.TradeInfoResponseForm;
import co.tickle.network.form.TradeListResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import co.tickle.view.adapter.ConditionTicketAdapter;
import co.tickle.view.adapter.SuggestTicketAdapter;
import co.tickle.view.common.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-08-02.
 */
public class ChangeTicketActivity extends BaseActivity {

    int fromQuantity;
    int fromQuantityPosition = -1;
    int toQuantity;
    int toQuantityPosition = -1;

    Ticket fromTicket;
    Ticket toTicket;
    List<Quantity> fromQuantities;
    List<Quantity> toQuantities;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.findLayout) {
            Intent intent = new Intent(this, FindTicketActivity.class);
            startActivityForResult(intent, CodeDefinition.FIND_REQUEST_CODE);
        } else if (v.getId() == R.id.suggestButton) {
            if (toTicket == null || fromTicket == null) return;
            suggestTicket();
        } else if (v.getId() == R.id.tradeLayout) {
            applyTicket((String) v.getTag());
        }
    }

    public void applyTicket(final String tradeId) {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("교환하시겠습니까?");
        alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() { // 확인 버튼
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                TradeController.getInstance(getBaseContext()).apply(tradeId, new Callback<TradeApplyResponseForm>() {
                    @Override
                    public void onResponse(Call<TradeApplyResponseForm> call, Response<TradeApplyResponseForm> response) {
                        if (response.body().getCode() == 200) {
                            Toast.makeText(ChangeTicketActivity.this, "교환하였습니다", Toast.LENGTH_SHORT).show();
                            sendBroadcast(new Intent(CodeDefinition.FAVORITE_TICKLE_BROADCAST));
                            sendBroadcast(new Intent(CodeDefinition.CONDITION_TICKLE_BROADCAST));
                            getSuggestList();
                        }
                    }

                    @Override
                    public void onFailure(Call<TradeApplyResponseForm> call, Throwable t) {

                    }
                });
            }
        });
        alertDlg.setNegativeButton("아니요", new DialogInterface.OnClickListener() { // 취소 버튼
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.cancel();
            }
        });
        AlertDialog alert = alertDlg.create();
        alert.show();

    }

    public void suggestTicket() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("제안하시겠습니까?");
        alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() { // 확인 버튼
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                TradeController.getInstance(getBaseContext()).propose(fromTicket.getTicket_id(), toTicket.get_id(), fromQuantity, toQuantity, new Callback<ProposeResponseForm>() {
                    @Override
                    public void onResponse(Call<ProposeResponseForm> call, Response<ProposeResponseForm> response) {
                        if (response.body().getCode() == 200) {
                            Toast.makeText(ChangeTicketActivity.this, "제안하였습니다", Toast.LENGTH_SHORT).show();
                            sendBroadcast(new Intent(CodeDefinition.CONDITION_TICKLE_BROADCAST));
                        } else if (response.body().getCode() == 403)
                            Toast.makeText(ChangeTicketActivity.this, "티클 갯수가 부족합니다.", Toast.LENGTH_SHORT).show();
                        else if (response.body().getCode() == 402)
                            Toast.makeText(ChangeTicketActivity.this, "티클이 없습니다.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ChangeTicketActivity.this, "오류가 발생하였습니다.\n고객센터에 문의해주세요.", Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    @Override
                    public void onFailure(Call<ProposeResponseForm> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        alertDlg.setNegativeButton("아니요", new DialogInterface.OnClickListener() { // 취소 버튼
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.cancel();
            }
        });
        AlertDialog alert = alertDlg.create();
        alert.show();
    }

    public void initSuggestLayout(List<Trade> tickets) {
        findViewById(R.id.suggestMsgLayout).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.suggestButton)).setText("제안하기");
        RecyclerView suggestView = (RecyclerView) findViewById(R.id.suggestListView);
        suggestView.setVisibility(View.VISIBLE);
        SuggestTicketAdapter adapter = new SuggestTicketAdapter(this, tickets, this);
        suggestView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        suggestView.setLayoutManager(llm);
    }

    public void getSuggestList() {

        TradeController.getInstance(this).getList("0", toTicket.get_id(), fromTicket.getTicket_id(), new Callback<TradeListResponseForm>() {
            @Override
            public void onResponse(Call<TradeListResponseForm> call, Response<TradeListResponseForm> response) {
                if (response.body().getCode() == 200) {
                    List<Trade> trades = new ArrayList<>();
                    if (response.body().getResult() != null)
                        trades = response.body().getResult();

                    initSuggestLayout(trades);
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
                    fromQuantity = fromTicket.getQuantity();
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

    public View initTicket(Ticket ticket, int status) {
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


        int quantity = 0;
        if (status == 1) {
            quantity = 1000;
            toQuantities = new ArrayList<>();
            if (quantity >= 20) {
                for (int i = (quantity / 10) * 10; i >= 10; i -= 10)
                    toQuantities.add(new Quantity(i));
            } else {
                for (int i = quantity; i >= 1; i -= 1)
                    toQuantities.add(new Quantity(i));
            }
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.element_spinner, toQuantities);
            quantityView.setAdapter(adapter);
            if (toQuantityPosition == -1)
                toQuantityPosition = toQuantities.size() - 1;
            quantityView.setSelection(toQuantityPosition);
            quantityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    toQuantity = toQuantities.get(position).getQuantity();
                    toQuantityPosition = position;
                    ((Spinner) findViewById(R.id.toTicketLayout).findViewById(R.id.quantityView)).setSelection(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            quantity = fromTicket.getQuantity();
            fromQuantities = new ArrayList<>();
            if (quantity >= 20) {
                for (int i = (quantity / 10) * 10; i >= 10; i -= 10)
                    fromQuantities.add(new Quantity(i));
            } else {
                for (int i = quantity; i >= 1; i -= 1)
                    fromQuantities.add(new Quantity(i));
            }
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.element_spinner, fromQuantities);
            quantityView.setAdapter(adapter);
            if (fromQuantityPosition == -1)
                fromQuantityPosition = fromQuantities.size() - 1;
            quantityView.setSelection(fromQuantityPosition);
            quantityView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    fromQuantity = fromQuantities.get(position).getQuantity();
                    fromQuantityPosition = position;
                    ((Spinner) findViewById(R.id.fromTicketLayout).findViewById(R.id.quantityView)).setSelection(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        return view;
    }

    public void initFromTicket() {
        LinearLayout fromTicketLayout = (LinearLayout) findViewById(R.id.fromTicketLayout);
        fromTicketLayout.removeAllViews();
        fromTicketLayout.addView(initTicket(fromTicket, 0));
    }

    public void initToTicket() {
        LinearLayout toTicketLayout = (LinearLayout) findViewById(R.id.toTicketLayout);
        toTicketLayout.removeAllViews();
        toTicketLayout.addView(initTicket(toTicket, 1));
        getSuggestList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == CodeDefinition.FIND_REQUEST_CODE) {
            String _id = data.getStringExtra(CodeDefinition.ID_PARAM);
            if (fromTicket.getTicket_id().equals(_id)) {
                Toast.makeText(this, "같은 아이템은 선택할수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            getTicket(_id);
        }
    }
}
