package co.tickle.network.controller;

import android.content.Context;

import co.tickle.network.RestApi;
import co.tickle.network.form.LoginForm;
import co.tickle.network.form.LoginResponseForm;
import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.SignUpForm;
import co.tickle.network.form.UserInfoResponseForm;
import co.tickle.network.service.TickleService;
import co.tickle.network.service.UserService;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by rlawn on 2016-08-12.
 */
public class UserController extends BaseController {
    UserService userService;
    protected static BaseController instance ;


    public UserController(Context context) {
        super(context);
    }

    public static synchronized UserController getInstance(Context context) {
        if(instance ==null) {
            instance = new UserController(context);
        }
        return (UserController) instance;
    }
    @Override
    protected void initService() {
        if(userService ==null)
            userService = retrofit.create(UserService.class);
    }

    public void login(LoginForm loginForm, Callback<LoginResponseForm> callback) {
        Call<LoginResponseForm> call = userService.login(loginForm);
        call.enqueue(callback);
    }

    public void signUp(SignUpForm signUpForm, Callback<ResponseForm> callback) {
        Call<ResponseForm> call = userService.signUp(signUpForm);
        call.enqueue(callback);
    }
    public void getInfo(Callback<UserInfoResponseForm> callback) {
        String token = SessionUtils.getString(context, CodeDefinition.TOKEN, RestApi.TESTER1);
        Call<UserInfoResponseForm> call = userService.getInfo(token);
        call.enqueue(callback);
    }

}
