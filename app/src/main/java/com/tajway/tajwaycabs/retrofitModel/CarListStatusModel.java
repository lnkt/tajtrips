package com.tajway.tajwaycabs.retrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CarListStatusModel {
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

        @SerializedName("cars")
        @Expose
        private ArrayList<Car> cars = null;

        public ArrayList<Car> getCars() {
            return cars;
        }

        public void setCars(ArrayList<Car> cars) {
            this.cars = cars;
        }

        public class Car {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("car_name")
            @Expose
            private String carName;
            @SerializedName("isactive")
            @Expose
            private Integer isactive;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getCarName() {
                return carName;
            }

            public void setCarName(String carName) {
                this.carName = carName;
            }

            public Integer getIsactive() {
                return isactive;
            }

            public void setIsactive(Integer isactive) {
                this.isactive = isactive;
            }

        }

    }
}
