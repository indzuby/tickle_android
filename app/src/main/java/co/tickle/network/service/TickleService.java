package co.tickle.network.service;

import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.TicketListResponseForm;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rlawn on 2016-08-09.
 */
public interface TickleService {

    @GET("/tickle/list")
    Call<TicketListResponseForm> getList(@Query("mode")String mode, @Query("category") String category);

    @FormUrlEncoded
    @POST("/tickle/get")
    Call<ResponseForm> download(@Header("token") String token, @Field("id") String tickleId);

}
