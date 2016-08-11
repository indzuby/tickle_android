package co.tickle.view.change.myticket;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;

import org.joda.time.DateTime;

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
 * Created by rlawn on 2016-07-28.
 */
public class UseTicketActivity extends BaseActivity {

    Ticket mTicket;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.createBarcodeButton) {
            createBarcode();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_ticket);

        initData();
    }
    public void initData(){
        String _id = getIntent().getStringExtra(CodeDefinition.ID_PARAM);
        TicketController.getInstance(this).getMyInfo(_id, new Callback<TicketInfoResponseForm>() {
            @Override
            public void onResponse(Call<TicketInfoResponseForm> call, Response<TicketInfoResponseForm> response) {
                if(response.body().getCode() == 200){
                    mTicket = response.body().getResult();
                    init();
                }
            }

            @Override
            public void onFailure(Call<TicketInfoResponseForm> call, Throwable t) {

            }
        });
    }

    public void createBarcode(){
        ImageView barcodeImageView = (ImageView) findViewById(R.id.barcodeImageView);
        TextView expireView = (TextView) findViewById(R.id.expireView);
        TextView barcodeView = (TextView) findViewById(R.id.barcodeView);
        findViewById(R.id.barcodeCreateLayout).setVisibility(View.GONE);
        findViewById(R.id.barcodeLayout).setVisibility(View.VISIBLE);
        DateTime expire = new DateTime(mTicket.getExpire());
        expireView.setText(expire.toString("yyyy.MM.dd 까지 사용가능"));
        barcodeView.setText(mTicket.getBarcode());
        Bitmap bitmap;
        try{
            bitmap = Utils.encodeAsBitmap(mTicket.getBarcode(), BarcodeFormat.CODE_128, 700, 200);
            barcodeImageView.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void init() {
        super.init();
        ImageView thumbnailView =  (ImageView) findViewById(R.id.thumbnailView);
        TextView companyNameView = (TextView) findViewById(R.id.companyNameView);
        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView orgPriceView = (TextView) findViewById(R.id.orgPriceView);
        TextView discountView = (TextView) findViewById(R.id.discountView);
        TextView quantityView = (TextView) findViewById(R.id.quantityView);
        TextView telView = (TextView) findViewById(R.id.telView);
        TextView noticeView = (TextView) findViewById(R.id.noticeView);
        TextView addressView = (TextView) findViewById(R.id.addressView);
        TextView openHoursView = (TextView) findViewById(R.id.openHoursView);
        TextView etcView = (TextView) findViewById(R.id.etcView);


        Glide.with(this).load(mTicket.getThumbnail()).into(thumbnailView);
        companyNameView.setText(mTicket.getCompany());
        nameView.setText(mTicket.getName());
        orgPriceView.setText(Utils.getPriceToString(mTicket.getOrg_price()));
        discountView.setText(Utils.getPriceToString(mTicket.getDiscount()));
        quantityView.setText(Utils.getPriceToString(mTicket.getQuantity()*CodeDefinition.TICKLE_PRICE));
        telView.setText(mTicket.getInformation().getTel());
        noticeView.setText(mTicket.getInformation().getNotice());
        addressView.setText(mTicket.getInformation().getAddress());
        openHoursView.setText(mTicket.getInformation().getOpenHours());
        etcView.setText(mTicket.getInformation().getEtc());

        findViewById(R.id.createBarcodeButton).setOnClickListener(this);

    }
}
