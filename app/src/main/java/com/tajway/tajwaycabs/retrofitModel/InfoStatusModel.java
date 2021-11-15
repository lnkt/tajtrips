package com.tajway.tajwaycabs.retrofitModel;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InfoStatusModel {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("account_data")
    @Expose
    private List<Data> account_data;


    @SerializedName("listing_driver_accounting")
    @Expose
    private List<Data> listing_driver_accounting;

    public List<Data> getAccount_data() {
        return account_data;
    }

    public List<Data> getListing_driver_accounting() {
        return listing_driver_accounting;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public class Data {

        @SerializedName("amount")
        @Expose
        String amount;
        @SerializedName("txn_type")
        @Expose
        String txn_type;
        @SerializedName("txn_date")
        @Expose
        String txn_date;
        @SerializedName("remarks")
        @Expose
        String remarks;

        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("booking_id")
        @Expose
        String booking_id;

        @SerializedName("driver_id")
        @Expose
        String driver_id;

        @SerializedName("user_id")
        @Expose
        String user_id;


        @SerializedName("Source")
        @Expose
        String source;


        @SerializedName("Destination")
        @Expose
        String destination;

        @SerializedName("source")
        @Expose
        String source_book;


        @SerializedName("destination")
        @Expose
        String destination_book;

        @SerializedName("gallery")
        @Expose
        private ArrayList<Gallery> gallery = null;

        public String getBooking_id() {
            return booking_id;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getSource() {
            return source;
        }

        public String getSource_book() {
            return source_book;
        }

        public String getDestination_book() {
            return destination_book;
        }

        public String getDestination() {
            return destination;
        }

        public String getAmount() {
            return amount;
        }

        public String getTxn_type() {
            return txn_type;
        }

        public String getTxn_date() {
            return txn_date;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getId() {
            return id;
        }

        public ArrayList<Gallery> getGallery() {
            return gallery;
        }

        public void setGallery(ArrayList<Gallery> gallery) {
            this.gallery = gallery;
        }

        public class Gallery {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("isactive")
            @Expose
            private Integer isactive;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Integer getIsactive() {
                return isactive;
            }

            public void setIsactive(Integer isactive) {
                this.isactive = isactive;
            }

        }
    }
}
