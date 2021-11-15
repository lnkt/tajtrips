package com.tajway.tajwaycabs.requestdata;

public class UpdateBanlDetailsRequest {

    private String account_mobile;
    private String account_holder_name;
    private String account_no;
    private String bank_name;
    private String ifsc_code;

    public String getAccount_mobile() {
        return account_mobile;
    }

    public void setAccount_mobile(String account_mobile) {
        this.account_mobile = account_mobile;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }


}
