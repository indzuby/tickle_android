package co.tickle.model;

import java.util.Date;

import lombok.Data;

/**
 * Created by rlawn on 2016-08-09.
 */
@Data
public class Ticket extends BaseModel{
    String company;
    String ticket_id;
    String name;
    Integer org_price;
    Integer price;
    Integer discount;
    Integer quantity;
    Date expire;
    Date opendate;
    String thumbnail;
    StoreInformation information;
    String barcode;
    Integer status;
    Boolean favorite = false;

    public boolean isFavorite(){
        if(favorite==null || !favorite) return false;
        return true;
    }
}
