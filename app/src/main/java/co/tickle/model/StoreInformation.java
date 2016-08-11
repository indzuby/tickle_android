package co.tickle.model;

import android.nfc.tech.NfcBarcode;

import lombok.Data;

/**
 * Created by rlawn on 2016-08-09.
 */
@Data
public class StoreInformation extends BaseModel {
    String tel;
    String notice;
    String address;
    String etc;
    Location location;
    String openHours;
}
