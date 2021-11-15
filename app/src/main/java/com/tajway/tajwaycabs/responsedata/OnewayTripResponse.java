package com.tajway.tajwaycabs.responsedata;

import java.util.ArrayList;

public class OnewayTripResponse {
    private String id;
    private String user_id;
    private String type;
    private String cab;
    private String booking_id;
    private String trip_type;
    private String total_kms;
    private String total_hours;
    private String trip_price;
    private String extra_kms;
    private String extra_hours;
    private String start_date;
    private String start_time;
    private String end_date;
    private String remarks;
    private String status;
    private String isactive;


    ArrayList<TripCityData>tripcity;

    public ArrayList<TripCityData> getTripcity() {
        return tripcity;
    }

    public void setTripcity(ArrayList<TripCityData> tripcity) {
        this.tripcity = tripcity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCab() {
        return cab;
    }

    public void setCab(String car_type) {
        this.cab = cab;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }

    public String getTotal_kms() {
        return total_kms;
    }

    public void setTotal_kms(String total_kms) {
        this.total_kms = total_kms;
    }

    public String getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(String total_hours) {
        this.total_hours = total_hours;
    }

    public String getTrip_price() {
        return trip_price;
    }

    public void setTrip_price(String trip_price) {
        this.trip_price = trip_price;
    }

    public String getExtra_kms() {
        return extra_kms;
    }

    public void setExtra_kms(String extra_kms) {
        this.extra_kms = extra_kms;
    }

    public String getExtra_hours() {
        return extra_hours;
    }

    public void setExtra_hours(String extra_hours) {
        this.extra_hours = extra_hours;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
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

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }






}
