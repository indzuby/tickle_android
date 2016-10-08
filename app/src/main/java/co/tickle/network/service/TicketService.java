package co.tickle.network.service;

import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.TicketInfoResponseForm;
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
public interface TicketService {

    @GET("/ticket/list")
    Call<TicketListResponseForm> getList(@Query("category") String category);

    @GET("/ticket/info")
    Call<TicketInfoResponseForm> getInfo(@Query("id") String ticketId);

    @GET("/ticket/my/list")
    Call<TicketListResponseForm> getMyList(@Header("token") String token,@Query("category") String category,@Query("favorite")  Boolean favorite);

    @GET("/ticket/my")
    Call<TicketInfoResponseForm> getMyInfo(@Header("token") String token, @Query("id") String id);

    @FormUrlEncoded
    @POST("/ticket/my/favorite")
    Call<ResponseForm> favorite(@Header("token") String token, @Field("ticket_id") String tickleId,@Field("favorite") Boolean favorite);
}
