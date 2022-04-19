package com.tajway.tajwaycabs.retrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InitiatPaymentRsponse implements Serializable {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;
    public class Data {

        @SerializedName("payment_done")
        @Expose
        public String paymentDone;
        @SerializedName("razorpay_order_id")
        @Expose
        public String razorpayOrderId;
        @SerializedName("total")
        @Expose
        public String total;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("currency")
        @Expose
        public String currency;
        @SerializedName("merchantid")
        @Expose
        public String merchantid;

        @SerializedName("refid")
        @Expose
        public String refid;
        @SerializedName("hashdata")
        @Expose
        public String hashdata;
    }
}
