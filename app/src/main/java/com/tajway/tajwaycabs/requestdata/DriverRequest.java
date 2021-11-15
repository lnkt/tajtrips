package com.tajway.tajwaycabs.requestdata;

public class DriverRequest {
    String booking_id, driver_id, user_id, source, destination;

    public DriverRequest() {
    }

    public DriverRequest(String booking_id, String driver_id, String user_id, String source, String destination) {
        this.booking_id = booking_id;
        this.driver_id = driver_id;
        this.user_id = user_id;
        this.source = source;
        this.destination = destination;
    }

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

    public String getDestination() {
        return destination;
    }
}
