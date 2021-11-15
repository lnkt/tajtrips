package com.tajway.tajwaycabs.retrofitModel;

public class DriverAccountingModel {
    String particular, cardType, credit, debit, none, totalAmount, remarks;
    String id, da_id, booking_id, destination, source, date;

    public DriverAccountingModel(String particular, String cardType, String credit, String debit, String none, String totalAmount, String remarks) {
        this.particular = particular;
        this.cardType = cardType;
        this.credit = credit;
        this.debit = debit;
        this.none = none;
        this.totalAmount = totalAmount;
        this.remarks = remarks;
    }

    public DriverAccountingModel(String id, String da_id, String booking, String source, String destination, String date, String remarks, String totalAmount, String cardType) {
        this.id = id;
        this.da_id = da_id;
        this.booking_id = booking_id;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.remarks = remarks;
        this.totalAmount = totalAmount;
        this.cardType = cardType;
    }

    public String getParticular() {
        return particular;
    }

    public String getCredit() {
        return credit;
    }

    public String getNone() {
        return none;
    }

    public String getDebit() {
        return debit;
    }

    public String getCardType() {
        return cardType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getId() {
        return id;
    }

    public String getDa_id() {
        return da_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public String getDestination() {
        return destination;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }
}
