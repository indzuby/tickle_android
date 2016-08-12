package co.tickle.model;

import lombok.Data;

/**
 * Created by rlawn on 2016-08-12.
 */
@Data
public class Trade extends BaseModel {

    Ticket toTicket;

    Ticket fromTicket;

    String proposer;

    Integer quantity;

    String status;

}