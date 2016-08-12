package co.tickle.network.form;

import lombok.Data;

/**
 * Created by rlawn on 2016-08-12.
 */
@Data
public class LoginForm extends BaseForm {
    String email;
    String password;
}
