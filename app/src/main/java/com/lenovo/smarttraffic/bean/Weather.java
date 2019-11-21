package com.lenovo.smarttraffic.bean;

public class Weather {
    private String weather_zh;
    private String weather_en;
    private int maxtemp;
    private int mintemp;

    public String getWeather_zh() {
        return weather_zh;
    }

    public void setWeather_zh(String weather_zh) {
        this.weather_zh = weather_zh;
    }

    public String getWeather_en() {
        return weather_en;
    }

    public void setWeather_en(String weather_en) {
        this.weather_en = weather_en;
    }

    public int getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(int maxtemp) {
        this.maxtemp = maxtemp;
    }

    public int getMintemp() {
        return mintemp;
    }

    public void setMintemp(int mintemp) {
        this.mintemp = mintemp;
    }
}
