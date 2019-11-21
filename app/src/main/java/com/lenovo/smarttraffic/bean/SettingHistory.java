package com.lenovo.smarttraffic.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.net.CookiePolicy;

@DatabaseTable(tableName = "SettingHistory")
public class SettingHistory {
    @DatabaseField(columnName = "id",generatedId = true)
    private int id;
    @DatabaseField(columnName = "carnumber")
    private String carnumber;
    @DatabaseField(columnName = "carId")
    private int carId;
    @DatabaseField(columnName = "money")
    private int money;
    @DatabaseField(columnName = "username")
    private String username;
    @DatabaseField(columnName = "time")
    private String time;

    public SettingHistory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
