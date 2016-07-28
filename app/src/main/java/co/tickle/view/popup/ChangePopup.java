package co.tickle.view.popup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import co.tickle.R;
import co.tickle.view.change.mycoupon.UseCouponActivity;

/**
 * Created by zuby on 2016. 7. 8..
 */
public class ChangePopup extends Dialog implements View.OnClickListener{
    public ChangePopup(Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
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

        findViewById(R.id.useCouponButton).setOnClickListener(this);
        findViewById(R.id.changeCouponButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.useCouponButton) {
            Intent intent = new Intent(getContext(), UseCouponActivity.class);
            getContext().startActivity(intent);
        }
        dismiss();
    }
}
