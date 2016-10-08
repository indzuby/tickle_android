package co.tickle.network.controller;

import android.content.Context;

import co.tickle.network.RestApi;
import co.tickle.network.form.ProposeResponseForm;
import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.network.form.TradeListResponseForm;
import co.tickle.network.service.TradeService;
import co.tickle.network.service.UserService;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by rlawn on 2016-08-12.
 */
public class TradeController extends BaseController {

    TradeService tradeService;
    protected static BaseController instance ;


    public TradeController(Context context) {
        super(context);
    }

    public static synchronized TradeController getInstance(Context context) {
        if(instance ==null) {
            instance = new TradeController(context);
        }
        return (TradeController) instance;
    }
    @Override
    protected void initService() {
        if(tradeService ==null)
            tradeService = retrofit.create(TradeService.class);
    }

    public void propose(String fromTicket, String toTicket, int quantity, Callback<ProposeResponseForm> callback){
        String token = SessionUtils.getString(context, CodeDefinition.TOKEN, RestApi.TESTER1);
        Call<ProposeResponseForm> call = tradeService.propose(token,fromTicket,toTicket,quantity);
        call.enqueue(callback);
    }
    public void getMyList(String status,Callback<TradeListResponseForm> callback){
        String token = SessionUtils.getString(context, CodeDefinition.TOKEN, RestApi.TESTER1);
        Call<TradeListResponseForm> call = tradeService.getList(token,status,"","");
        call.enqueue(callback);
    }
    public void getList(String status, String fromTicket, String toTicket, Callback<TradeListResponseForm> callback){
        Call<TradeListResponseForm> call = tradeService.getList("",status,fromTicket,toTicket);
        call.enqueue(callback);
    }
    public void cancel(String tradeId,Callback<ResponseForm> callback) {
        String token = SessionUtils.getString(context, CodeDefinition.TOKEN, RestApi.TESTER1);
        Call<ResponseForm> call = tradeService.cancel(token,tradeId);
        call.enqueue(callback);
    }
}
