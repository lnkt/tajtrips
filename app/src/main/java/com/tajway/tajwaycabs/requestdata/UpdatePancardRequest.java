package com.tajway.tajwaycabs.requestdata;

public class UpdatePancardRequest {

    private String pan_holder_name;

    public String getPan_holder_name() {
        return pan_holder_name;
    }

    public void setPan_holder_name(String pan_holder_name) {
        this.pan_holder_name = pan_holder_name;
    }

    public String getPan_number() {
        return pan_number;
    }

    public void setPan_number(String pan_number) {
        this.pan_number = pan_number;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    private String pan_number;
    private String dob;
}
