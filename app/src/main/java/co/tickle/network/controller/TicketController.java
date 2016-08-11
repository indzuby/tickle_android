package co.tickle.network.controller;

import android.content.Context;

import co.tickle.network.RestApi;
import co.tickle.network.form.TicketInfoResponseForm;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.network.service.TicketService;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by rlawn on 2016-08-11.
 */
public class TicketController extends BaseController {
    TicketService ticketService;
    protected static BaseController instance ;

    public TicketController(Context context) {
        super(context);
    }

    public static synchronized TicketController getInstance(Context context) {
        if(instance ==null) {
            instance = new TicketController(context);
        }
        return (TicketController) instance;
    }

    public void getList(String category, Callback<TicketListResponseForm> callback){
        Call<TicketListResponseForm> call = ticketService.getList(category);
        call.enqueue(callback);

    }
    public void getInfo(String ticketId, Callback<TicketInfoResponseForm> callback){
        Call<TicketInfoResponseForm> call = ticketService.getInfo(ticketId);
        call.enqueue(callback);

    }
    public void getMyList(Callback<TicketListResponseForm> callback){
        String token = SessionUtils.getString(context, CodeDefinition.TOKEN, RestApi.TESTER1);
        Call<TicketListResponseForm> call = ticketService.getMyList(token);
        call.enqueue(callback);
    }

    public void getMyInfo(String ticketId, Callback<TicketInfoResponseForm> callback){
        String token = SessionUtils.getString(context, CodeDefinition.TOKEN, RestApi.TESTER1);
        Call<TicketInfoResponseForm> call = ticketService.getMyInfo(token,ticketId);
        call.enqueue(callback);
    }

    @Override
    protected void initService() {
        if(ticketService==null)
            ticketService = retrofit.create(TicketService.class);
    }
}
