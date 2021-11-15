package com.tajway.tajwaycabs.responsedata;

public class DriverAccount {
    String booking_id, driver_id, user_id, source, destination;
    int id;

    public DriverAccount(int id, String booking_id, String driver_id, String user_id, String source, String destination) {
        this.booking_id = booking_id;
        this.driver_id = driver_id;
        this.user_id = user_id;
        this.source = source;
        this.destination = destination;
        this.id =id;
    }

    public int getId() {
        return id;
    }

    public String getBookingL_id() {
        return booking_id;
    }

    public String getDriverL_id() {
        return driver_id;
    }

    public String getUserL_id() {
        return user_id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}
