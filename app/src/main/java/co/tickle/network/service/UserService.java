package co.tickle.network.service;

import java.util.List;

import co.tickle.network.form.LoginForm;
import co.tickle.network.form.LoginResponseForm;
import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.SignUpForm;
import co.tickle.network.form.UserInfoResponseForm;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by rlawn on 2016-08-12.
 */
public interface UserService {


    @POST("/user/signup")
    Call<ResponseForm> signUp(@Body SignUpForm signUpForm);


    @POST("/user/signin")
    Call<LoginResponseForm> login(@Body LoginForm loginForm);

    @GET("/user/info")
    Call<UserInfoResponseForm> getInfo(@Header("token")String token);


}
