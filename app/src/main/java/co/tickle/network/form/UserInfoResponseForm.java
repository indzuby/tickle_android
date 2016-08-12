package co.tickle.network.form;

import co.tickle.model.User;
import lombok.Data;

/**
 * Created by rlawn on 2016-08-12.
 */
@Data
public class UserInfoResponseForm extends ResponseForm {
    User result;
}
