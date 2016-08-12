package co.tickle.network.form;

import lombok.Data;

/**
 * Created by rlawn on 2016-08-12.
 */
@Data
public class SignUpForm extends BaseForm {
    String email;
    String password;
    String name;
}
