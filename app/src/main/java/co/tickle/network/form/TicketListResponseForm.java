package co.tickle.network.form;

import java.util.List;

import co.tickle.model.Ticket;
import lombok.Data;

/**
 * Created by rlawn on 2016-08-11.
 */
@Data
public class TicketListResponseForm extends ResponseForm {

    List<Ticket> result;
}
