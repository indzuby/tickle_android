package co.tickle.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import co.tickle.utils.ImproveDateTypeAdapter;
import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zuby on 2016. 7. 20..
 */
public class RestApi {


    public final static String url_v1 = "http://182.252.177.154:3000/";

    public final static String TESTER1 = "57ab5d8341ba5ffea20d6f19";
    public final static String TESTER2 = "57ab5d9041ba5ffea20d6f1a";

    @Getter
    private Context context;
    private static RestApi instance ;
    @Getter
    private Retrofit retrofit;


    public RestApi(Context mContext) {
        this.context = mContext;
        GsonBuilder builder = new GsonBuilder();

// Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new ImproveDateTypeAdapter());

        Gson gson = builder.create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url_v1)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RestApi getInstance(Context context) {
        if(instance ==null) {
            instance = new RestApi(context);
        }
        return instance;
    }


}
