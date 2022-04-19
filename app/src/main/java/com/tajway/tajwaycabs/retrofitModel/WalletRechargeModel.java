package com.tajway.tajwaycabs.retrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WalletRechargeModel implements Serializable {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;

    @SerializedName("amount")
    @Expose
    public String amount;


    public WalletRechargeModel(String amount) {
        this.amount = amount;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("refid")
        @Expose
        public String refid;
        @SerializedName("total")
        @Expose
        public String total;
        @SerializedName("product")
        @Expose
        public String product;


        @SerializedName("name")
        @Expose
        public String name;
        public String email;
        public  String mobile;
        public String hashdata;

        public String getHashdata() {
            return hashdata;
        }

        public void setHashdata(String hashdata) {
            this.hashdata = hashdata;
        }
    }
}
