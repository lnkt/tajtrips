package com.tajway.tajwaycabs.requestdata;

public class UpdateProfileRequest {

    private String name;
    private String address;
    private String company_name;
    private String email;
    private String alternate_no;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternate_no() {
        return alternate_no;
    }

    public void setAlternate_no(String alternate_no) {
        this.alternate_no = alternate_no;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
