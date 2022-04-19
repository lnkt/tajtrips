package com.tajway.tajwaycabs.retrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WalletHistoryModel implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;


    public class Data  {
        @SerializedName("history")
        @Expose
        public ArrayList<History> history = null;
        @SerializedName("balance")
        @Expose
        public String balance;
        @SerializedName("points")
        @Expose
        public String points;

        @SerializedName("cashback")
        @Expose
        public ArrayList<Cashback> cashback = null;

    }
    public class Cashback {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("refid")
        @Expose
        public String refid;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("amount")
        @Expose
        public String amount;
        @SerializedName("amount_type")
        @Expose
        public String amountType;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("icon")
        @Expose
        public String icon;
        @SerializedName("date")
        @Expose
        public String date;
    }

    public class History  {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("refid")
        @Expose
        public String refid;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("amount")
        @Expose
        public String amount;
        @SerializedName("amount_type")
        @Expose
        public String amountType;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("icon")
        @Expose
        public String icon;
        @SerializedName("date")
        @Expose
        public String date;


    }

}
