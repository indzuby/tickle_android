package co.tickle.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

/**
 * Created by rlawn on 2016-08-12.
 */
@Data
public class Trade extends BaseModel {

    @SerializedName("to_ticket")
    Ticket toTicket;
    @SerializedName("from_ticket")
    Ticket fromTicket;

    String proposer;

    Integer quantity;

    @SerializedName("org_quantity")
    Integer orgQuantity;

    String status;

    @SerializedName("datetime")
    Date dateTime;


    @SerializedName("from_quantity")
    Integer fromQuantity;

    @SerializedName("to_quantity")
    Integer toQuantity;

    Double rate;



}