package co.tickle.network.controller;

import android.content.Context;

import co.tickle.network.RestApi;
import co.tickle.network.form.ResponseForm;
import co.tickle.network.form.TicketListResponseForm;
import co.tickle.network.service.TicketService;
import co.tickle.network.service.TickleService;
import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by rlawn on 2016-08-11.
 */
public class TickleController extends BaseController {
    TickleService tickleService;
    protected static BaseController instance ;

    public TickleController(Context context) {
        super(context);
    }

    public static synchronized TickleController getInstance(Context context) {
        if(instance ==null) {
            instance = new TickleController(context);
        }
        return (TickleController) instance;
    }

    public void getList(String mode,String category, Callback<TicketListResponseForm> callback){
        Call<TicketListResponseForm> call = tickleService.getList(mode,category);
        call.enqueue(callback);

    }
    public void dowload(String tickleId, Callback<ResponseForm> callback){
        String token = SessionUtils.getString(context, CodeDefinition.TOKEN, RestApi.TESTER1);
        Call<ResponseForm> call = tickleService.download(token,tickleId);
        call.enqueue(callback);

    }
    @Override
    protected void initService() {
        if(tickleService==null)
            tickleService = retrofit.create(TickleService.class);
    }
}
