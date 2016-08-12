package co.tickle.view.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.stmt.query.In;

import co.tickle.R;
import co.tickle.network.controller.UserController;
import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.SignUpForm;
import co.tickle.utils.Utils;
import co.tickle.view.common.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rlawn on 2016-08-01.
 */
public class SignUpActivity extends BaseActivity {


    EditText emailView;
    EditText nameView;
    EditText passwordView;
    EditText passwordReView;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.signUpButton) {
            signUp();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    public void signUp(){
        SignUpForm signUpForm = new SignUpForm();
        String email = emailView.getText().toString();
        String name = nameView.getText().toString();
        String password = passwordView.getText().toString();
        String passwordRe = passwordReView.getText().toString();

        if(!Utils.validCheck(Utils.ValidType.EMAIL,email)) {
            Toast.makeText(this,"이메일 양식을 확인하세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passwordRe)){
            Toast.makeText(this,"비밀번호가 같지 않습니다..",Toast.LENGTH_SHORT).show();
            return;
        }
        signUpForm.setEmail(email);
        signUpForm.setName(name);
        signUpForm.setPassword(password);

        UserController.getInstance(this).signUp(signUpForm, new Callback<ResponseForm>() {
            @Override
            public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response) {
                if(response.body().getCode()==200) {
                    Toast.makeText(SignUpActivity.this,"회원가입에 성공했습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(response.body().getCode() == 401) {
                    Toast.makeText(SignUpActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseForm> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void init() {
        super.init();
        emailView = (EditText) findViewById(R.id.emailView);
        nameView = (EditText) findViewById(R.id.nameEditView);
        passwordView = (EditText) findViewById(R.id.passwordView);
        passwordReView = (EditText) findViewById(R.id.passwordReView);

        findViewById(R.id.signUpButton).setOnClickListener(this);
    }
}
