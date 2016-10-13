package com.tt.test.bean;

import java.util.List;

/**
 * Created by hoperun on 9/22/16.
 */

public class Weather {
    /**
     * status : OK
     * weather : [{"city_name":"北京","city_id":"CHBJ000000","last_update":"2014-01-26T12:52:57+08:00","future":[{"date":"2014-01-26","day":"周日","text":"多云","code1":"4","code2":"4","high":"4","low":"-5","cop":"0%","wind":"微风小于3级"},{"date":"2014-01-27","day":"周一","text":"多云/晴","code1":"4","code2":"1","high":"10","low":"-6","cop":"0%","wind":"微风小于3级"}]}]
     */

    private String status;
    /**
     * city_name : 北京
     * city_id : CHBJ000000
     * last_update : 2014-01-26T12:52:57+08:00
     * future : [{"date":"2014-01-26","day":"周日","text":"多云","code1":"4","code2":"4","high":"4","low":"-5","cop":"0%","wind":"微风小于3级"},{"date":"2014-01-27","day":"周一","text":"多云/晴","code1":"4","code2":"1","high":"10","low":"-6","cop":"0%","wind":"微风小于3级"}]
     */

    private List<WeatherBean> weather;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WeatherBean> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBean> weather) {
        this.weather = weather;
    }

    public static class WeatherBean {
        private String city_name;
        private String city_id;
        private String last_update;
        /**
         * date : 2014-01-26
         * day : 周日
         * text : 多云
         * code1 : 4
         * code2 : 4
         * high : 4
         * low : -5
         * cop : 0%
         * wind : 微风小于3级
         */

        private List<FutureBean> future;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public void setFuture(List<FutureBean> future) {
            this.future = future;
        }

        public static class FutureBean {
            private String date;
            private String day;
            private String text;
            private String code1;
            private String code2;
            private String high;
            private String low;
            private String cop;
            private String wind;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode1() {
                return code1;
            }

            public void setCode1(String code1) {
                this.code1 = code1;
            }

            public String getCode2() {
                return code2;
            }

            public void setCode2(String code2) {
                this.code2 = code2;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getCop() {
                return cop;
            }

            public void setCop(String cop) {
                this.cop = cop;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }
        }
    }
}
