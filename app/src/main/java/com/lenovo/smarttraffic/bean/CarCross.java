package com.lenovo.smarttraffic.bean;

public class CarCross {
    private String time;
    private int number;

    public CarCross(String time, int number) {
        this.time = time;
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
