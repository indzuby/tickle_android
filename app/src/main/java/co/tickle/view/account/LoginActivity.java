package co.tickle.view.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.tickle.R;
import co.tickle.network.controller.UserController;
import co.tickle.network.form.LoginForm;
import co.tickle.network.form.LoginResponseForm;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;
import co.tickle.view.common.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rlawn on 2016-08-01.
 */
public class LoginActivity extends BaseActivity {


    EditText emailView;
    EditText passwordView;


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.loginButton) {
            login();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    public void login(){
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail(emailView.getText().toString());
        loginForm.setPassword(passwordView.getText().toString());

        UserController.getInstance(this).login(loginForm, new Callback<LoginResponseForm>() {
            @Override
            public void onResponse(Call<LoginResponseForm> call, Response<LoginResponseForm> response) {
                if(response.body().getCode() == 200) {
                    Toast.makeText(LoginActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                    SessionUtils.putString(LoginActivity.this,CodeDefinition.TOKEN,response.body().getResult().get_id());
                    finish();
                }else if(response.body().getCode() == 401) {
                    Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseForm> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    public void init() {
        super.init();
        emailView = (EditText) findViewById(R.id.emailView);
        passwordView = (EditText) findViewById(R.id.passwordView);
        findViewById(R.id.loginButton).setOnClickListener(this);
    }
}
