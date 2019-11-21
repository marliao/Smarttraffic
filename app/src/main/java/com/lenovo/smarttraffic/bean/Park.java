package com.lenovo.smarttraffic.bean;

import java.util.List;

public class Park {

    /**
     * img : /images/parkzone/parkzone_001.png
     * longitude : 116.385307
     * latitude : 39.941853
     * location : 什刹海
     * ROWS_DETAIL : [{"name":"丽豪园小区停车场","address":"北京市西城区大石桥胡同55号后门丽豪园小区\r\n","coordinate":"116.388144,39.947112","open":1,"remarks":"停车场收费标准由全市停车系统统一定价，最高44元/天。","EmptySpace":32,"AllSpace":68},{"name":"新街口东街地上停车场","address":"西城区新街口东街\r\n","coordinate":"116.374765,39.942186","open":1,"remarks":"停车场收费标准由全市停车系统统一定价，最高41元/天。","EmptySpace":32,"AllSpace":146},{"name":"北京航鑫园宾馆地上停车场","address":"北京市西城区德内大街羊房胡同9号北京航鑫园宾馆\r\n","coordinate":"116.383109,39.94125","open":0,"remarks":"停车场收费标准由全市停车系统统一定价，最高43元/天。","EmptySpace":8,"AllSpace":88},{"name":"中国林业出版社地上停车场","address":"北京市西城区刘海胡同7号中国林业出版社\r\n","coordinate":"116.381556,39.93872","open":0,"remarks":"停车场收费标准由全市停车系统统一定价，最高44元/天。","EmptySpace":43,"AllSpace":180},{"name":"柳荫街恭王府停车场","address":"北京市西城区柳荫街14号附近\r\n","coordinate":"116.38517,39.936864","open":1,"remarks":"停车场收费标准由全市停车系统统一定价，最高44元/天。","EmptySpace":0,"AllSpace":88},{"name":"恭王府花园停车场","address":"北京市西城区前海西街22号附近\r\n","coordinate":"116.38517,39.936864","open":1,"remarks":"停车场收费标准由全市停车系统统一定价，最高44元/天。","EmptySpace":121,"AllSpace":158}]
     * RESULT : S
     * ERRMSG : 成功
     */

    private String img;
    private String longitude;
    private String latitude;
    private String location;
    private String RESULT;
    private String ERRMSG;
    private List<ROWSDETAILBean> ROWS_DETAIL;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getERRMSG() {
        return ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }

    public List<ROWSDETAILBean> getROWS_DETAIL() {
        return ROWS_DETAIL;
    }

    public void setROWS_DETAIL(List<ROWSDETAILBean> ROWS_DETAIL) {
        this.ROWS_DETAIL = ROWS_DETAIL;
    }

    public static class ROWSDETAILBean {
        /**
         * name : 丽豪园小区停车场
         * address : 北京市西城区大石桥胡同55号后门丽豪园小区

         * coordinate : 116.388144,39.947112
         * open : 1
         * remarks : 停车场收费标准由全市停车系统统一定价，最高44元/天。
         * EmptySpace : 32
         * AllSpace : 68
         */

        private String name;
        private String address;
        private String coordinate;
        private int open;
        private String remarks;
        private int EmptySpace;
        private int AllSpace;
        private float distance;

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

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

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getEmptySpace() {
            return EmptySpace;
        }

        public void setEmptySpace(int EmptySpace) {
            this.EmptySpace = EmptySpace;
        }

        public int getAllSpace() {
            return AllSpace;
        }

        public void setAllSpace(int AllSpace) {
            this.AllSpace = AllSpace;
        }
    }
}
