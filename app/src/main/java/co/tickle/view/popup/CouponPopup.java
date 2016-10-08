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
import co.tickle.network.RestApi;
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
public class CouponPopup extends Dialog implements View.OnClickListener{
    Ticket mTicket;
    public CouponPopup(Context context, Ticket ticket) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        mTicket = ticket;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_change);
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
        if(mTicket.isFavorite())
            findViewById(R.id.favoriteView).setSelected(true);
        else
            findViewById(R.id.favoriteView).setSelected(false);
        findViewById(R.id.useCouponButton).setOnClickListener(this);
        findViewById(R.id.changeCouponButton).setOnClickListener(this);
        findViewById(R.id.favoriteView).setOnClickListener(this);
    }

    public void favorite(boolean favorite){
        TicketController.getInstance(getContext()).favorite(mTicket.get_id(), favorite, new Callback<ResponseForm>() {
            @Override
            public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response) {
                if(response.body().getCode() == 200) {
                    findViewById(R.id.favoriteView).setSelected(!findViewById(R.id.favoriteView).isSelected());
                    mTicket.setFavorite(!mTicket.isFavorite());
                    if(mTicket.isFavorite())
                        Toast.makeText(getContext(),"관심쿠폰으로 등록되었습니다.",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(),"관심쿠폰에서 제거되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseForm> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.useCouponButton) {
            Intent intent = new Intent(getContext(), UseTicketActivity.class);
            intent.putExtra(CodeDefinition.ID_PARAM,mTicket.get_id());
            getContext().startActivity(intent);
        }else if(v.getId() == R.id.changeCouponButton) {
            Intent intent = new Intent(getContext(), ChangeTicketActivity.class);
            intent.putExtra(CodeDefinition.ID_PARAM,mTicket.get_id());
            getContext().startActivity(intent);
        }else if(v.getId() == R.id.favoriteView) {
            favorite(!findViewById(R.id.favoriteView).isSelected());
        }
        dismiss();
    }
}
