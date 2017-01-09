package co.tickle.network.service;

import co.tickle.network.form.ProposeResponseForm;
import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.TicketInfoResponseForm;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.network.form.TradeApplyResponseForm;
import co.tickle.network.form.TradeInfoResponseForm;
import co.tickle.network.form.TradeListResponseForm;
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
    Call<TradeListResponseForm> getList(@Header("token") String token, @Query("status") String status
            , @Query("from_ticket") String fromTicket,
                                        @Query("to_ticket") String toTicket);

    @GET("/trade/info")
    Call<TicketInfoResponseForm> getInfo(@Query("id") String id);

    @FormUrlEncoded
    @POST("/trade/propose")
    Call<ProposeResponseForm> propose(@Header("token") String token,
                                      @Field("from_ticket") String fromTicket,
                                      @Field("to_ticket") String toTicket,
                                      @Field("from_quantity") Integer fromQuantity,
                                      @Field("to_quantity") Integer toQuantity);

    @FormUrlEncoded
    @POST("/trade/apply")
    Call<TradeApplyResponseForm> apply(@Header("token") String token, @Field("trade_id") String tradeId);

    @FormUrlEncoded
    @POST("/trade/cancel")
    Call<ResponseForm> cancel(@Header("token") String token, @Field("trade_id") String tradeId);

    @GET("/trade/log")
    Call<TradeListResponseForm> log(@Header("token") String token, @Query("status") String status);


}
