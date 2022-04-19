package com.tajway.tajwaycabs.requestdata;

public class DriverCreateRequest {
    private String driver_name;
    private String driver_mobile;
    private String driveralternate_no;
    private String car_type;
    private String model_name;
    private String registration_number;

    public String getLicence_no() {
        return licence_no;
    }

    public void setLicence_no(String licence_no) {
        this.licence_no = licence_no;
    }

    private String licence_no;

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_mobile() {
        return driver_mobile;
    }

    public void setDriver_mobile(String driver_mobile) {
        this.driver_mobile = driver_mobile;
    }

    public String getDriveralternate_no() {
        return driveralternate_no;
    }

    public void setDriveralternate_no(String driveralternate_no) {
        this.driveralternate_no = driveralternate_no;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }


}
