package co.tickle.network.form;

import java.util.List;

import co.tickle.model.Ticket;
import co.tickle.model.Trade;
import lombok.Data;

/**
 * Created by rlawn on 2016-08-11.
 */
@Data
public class TradeListResponseForm extends ResponseForm {

    List<Trade> result;
}
