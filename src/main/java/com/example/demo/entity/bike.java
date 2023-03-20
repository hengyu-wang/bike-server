package com.example.demo.entity;


import javax.persistence.*;
import lombok.Data;



@Entity //直接将表名和类名绑定
@Data   //自动生成get，set方法
public class bike {
    @Id     //指明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String bikeid;     //@Id 下面的第一个属性就是主键
    public String longitude;
    public String latitude;
    public String RFID;
    public String statu;
    public String speed;
    public String lasttime;
    public String voltage;

    public bike() {
    }

    public bike(String bikeid, String longitude, String latitude, String RFID, String statu, String speed, String lasttime, String voltage, String temperature) {
        this.bikeid = bikeid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.RFID = RFID;
        this.statu = statu;
        this.speed = speed;
        this.lasttime = lasttime;
        this.voltage = voltage;
        this.temperature = temperature;
    }

    public String temperature;


    public String getBikeid() {
        return bikeid;
    }

    public void setBikeid(String bikeid) {
        this.bikeid = bikeid;
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

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
