package co.tickle.view.popup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import co.tickle.R;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;
import co.tickle.view.account.LoginActivity;
import co.tickle.view.account.SettingActivity;
import co.tickle.view.account.SignUpActivity;

/**
 * Created by zuby on 2016. 7. 8..
 */
public class AccountPopup extends Dialog implements View.OnClickListener{
    public AccountPopup(Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_account);
        init();
    }
    public void init(){
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.color.black50));
        findViewById(R.id.layout).setOnClickListener(this);
        setCanceledOnTouchOutside(true);
        String token = SessionUtils.getString(getContext(), CodeDefinition.TOKEN,"");
        if(token!=null && token.length()>0) {
            findViewById(R.id.loginButton).setVisibility(View.GONE);
            findViewById(R.id.signUpButton).setVisibility(View.GONE);
            findViewById(R.id.logoutButton).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.logoutButton).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.settingButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId() == R.id.loginButton) {
            intent = new Intent(getContext(), LoginActivity.class);
        }else if(v.getId() == R.id.signUpButton) {
            intent = new Intent(getContext(), SignUpActivity.class);
        }else if(v.getId() == R.id.settingButton) {
            intent = new Intent(getContext(), SettingActivity.class);
        }else if(v.getId() == R.id.logoutButton) {
            SessionUtils.putString(getContext(),CodeDefinition.TOKEN,"");
            Toast.makeText(getContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
        }
        if(intent!=null)getContext().startActivity(intent);
        dismiss();
    }
}
