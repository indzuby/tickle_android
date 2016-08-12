package co.tickle.model;

import co.tickle.utils.CodeDefinition;
import co.tickle.utils.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by rlawn on 2016-08-12.
 */

public class Quantity {
    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    @Getter
    int quantity;

    public String toString(){
        return Utils.getPriceToString(quantity* CodeDefinition.TICKLE_PRICE);
    }
}
