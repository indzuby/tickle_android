package co.tickle.network.form;

import co.tickle.model.Ticket;
import lombok.Data;

/**
 * Created by rlawn on 2016-08-11.
 */
@Data
public class TicketInfoResponseForm extends ResponseForm {

    Ticket result;
}
