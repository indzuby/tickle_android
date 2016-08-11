package co.tickle.network.form;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import lombok.Data;

/**
 * Created by rlawn on 2016-08-11.
 */
@Data
public class ResponseForm extends BaseForm{

    protected Integer code;
    protected String msg;

}
