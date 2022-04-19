package com.tajway.tajwaycabs.retrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WalletBalanceModel implements Serializable {
    @SerializedName("balance")
    @Expose
    public String balance;
    @SerializedName("points")
    @Expose
    public String points;
}
