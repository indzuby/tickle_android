package co.tickle.network.service;

import co.tickle.network.form.ResponseForm;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rlawn on 2016-08-11.
 */
public interface TradeService {

    @GET("/trade/list")
    Call<ResponseForm> getList(@Query("status")String status);

    @GET("/trade/info")
    Call<ResponseForm> getInfo(@Query("id")String id);

    @FormUrlEncoded
    @POST("/trade/propose")
    Call<ResponseForm> propose(@Header("token") String token,
                                    @Field("from_ticket")String fromTicket,
                                    @Field("to_ticket")String toTicket,
                                    @Field("quantity")String quantity);
    @FormUrlEncoded
    @POST("/trade/deal")
    Call<ResponseForm> deal(@Header("token") String token,@Field("trade_id") String tradeId);

}
