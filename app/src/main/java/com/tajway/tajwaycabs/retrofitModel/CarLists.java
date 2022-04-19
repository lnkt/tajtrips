package com.tajway.tajwaycabs.retrofitModel;

import java.util.List;

public class CarLists {
    public String status;
    public String message;
    public List<Datum> data;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum{
        public int id;
        public String car_name;
        public String isactive;
        public String car_type;
        public String vehicle_number;
        public String registration_year;
        public String file_rc;
        public String file_insurance;
        public String file_permite;
        public String car_ownerid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCar_name() {
            return car_name;
        }

        public void setCar_name(String car_name) {
            this.car_name = car_name;
        }

        public String getIsactive() {
            return isactive;
        }

        public void setIsactive(String isactive) {
            this.isactive = isactive;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getVehicle_number() {
            return vehicle_number;
        }

        public void setVehicle_number(String vehicle_number) {
            this.vehicle_number = vehicle_number;
        }

        public String getRegistration_year() {
            return registration_year;
        }

        public void setRegistration_year(String registration_year) {
            this.registration_year = registration_year;
        }

        public String getFile_rc() {
            return file_rc;
        }

        public void setFile_rc(String file_rc) {
            this.file_rc = file_rc;
        }

        public String getFile_insurance() {
            return file_insurance;
        }

        public void setFile_insurance(String file_insurance) {
            this.file_insurance = file_insurance;
        }

        public String getFile_permite() {
            return file_permite;
        }

        public void setFile_permite(String file_permite) {
            this.file_permite = file_permite;
        }

        public String getCar_ownerid() {
            return car_ownerid;
        }

        public void setCar_ownerid(String car_ownerid) {
            this.car_ownerid = car_ownerid;
        }
    }

}
