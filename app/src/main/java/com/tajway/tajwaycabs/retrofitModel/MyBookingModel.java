package com.tajway.tajwaycabs.retrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyBookingModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public class Data {

        @SerializedName("mybooktrip")
        @Expose
        private ArrayList<Mybooktrip> mybooktrip = null;

        public ArrayList<Mybooktrip> getMybooktrip() {
            return mybooktrip;
        }

        public void setMybooktrip(ArrayList<Mybooktrip> mybooktrip) {
            this.mybooktrip = mybooktrip;
        }

        public class Mybooktrip {


            @SerializedName("cab")
            @Expose
            private String cab;

            @SerializedName("id")
            @Expose
            private Integer id;

            @SerializedName("user_id")
            @Expose
            private Integer userId;

            @SerializedName("booking_id")
            @Expose
            private String booking_id;


            @SerializedName("type")
            @Expose
            private String type;

            @SerializedName("car_type")
            @Expose
            private String carType;
            @SerializedName("trip_id")
            @Expose
            private String tripId;
            @SerializedName("trip_type")
            @Expose
            private String tripType;
            @SerializedName("total_kms")
            @Expose
            private String totalKms;
            @SerializedName("total_hours")
            @Expose
            private String totalHours;
            @SerializedName("trip_price")
            @Expose
            private String tripPrice;
            @SerializedName("extra_kms")
            @Expose
            private String extraKms;
            @SerializedName("extra_hours")
            @Expose
            private String extraHours;
            @SerializedName("start_date")
            @Expose
            private String startDate;
            @SerializedName("start_time")
            @Expose
            private String startTime;
            @SerializedName("end_date")
            @Expose
            private String endDate;
            @SerializedName("remarks")
            @Expose
            private String remarks;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("isactive")
            @Expose
            private Integer isactive;


            @SerializedName("tripcity")
            @Expose
            private ArrayList<Tripcity> tripcity = null;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public void setBooking_id(String booking_id) {
                this.booking_id = booking_id;
            }

            public void setCab(String cab) {
                this.cab = cab;
            }


            public String getBooking_id() {
                return booking_id;
            }

            public String getTrip_type() {
                return tripType;
            }

            public String getCab() {
                return cab;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCarType() {
                return carType;
            }

            public void setCarType(String carType) {
                this.carType = carType;
            }

            public String getTripId() {
                return tripId;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

      
            public String getTotalKms() {
                return totalKms;
            }

            public void setTotalKms(String totalKms) {
                this.totalKms = totalKms;
            }

            public String getTotalHours() {
                return totalHours;
            }

            public void setTotalHours(String totalHours) {
                this.totalHours = totalHours;
            }

            public String getTripPrice() {
                return tripPrice;
            }

            public void setTripPrice(String tripPrice) {
                this.tripPrice = tripPrice;
            }

            public String getExtraKms() {
                return extraKms;
            }

            public void setExtraKms(String extraKms) {
                this.extraKms = extraKms;
            }

            public String getExtraHours() {
                return extraHours;
            }

            public void setExtraHours(String extraHours) {
                this.extraHours = extraHours;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Integer getIsactive() {
                return isactive;
            }

            public void setIsactive(Integer isactive) {
                this.isactive = isactive;
            }

            public ArrayList<Tripcity> getTripcity() {
                return tripcity;
            }


            public void setTripcity(ArrayList<Tripcity> tripcity) {
                this.tripcity = tripcity;
            }

            public class Tripcity {

                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("booktrip_id")
                @Expose
                private Integer booktripId;
                @SerializedName("trip_city")
                @Expose
                private String tripCity;
                @SerializedName("trip_order")
                @Expose
                private Integer tripOrder;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public Integer getBooktripId() {
                    return booktripId;
                }

                public void setBooktripId(Integer booktripId) {
                    this.booktripId = booktripId;
                }

                public String getTripCity() {
                    return tripCity;
                }

                public void setTripCity(String tripCity) {
                    this.tripCity = tripCity;
                }

                public Integer getTripOrder() {
                    return tripOrder;
                }

                public void setTripOrder(Integer tripOrder) {
                    this.tripOrder = tripOrder;
                }


            }
        }
    }
}
