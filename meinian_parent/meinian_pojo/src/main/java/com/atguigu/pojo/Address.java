package com.atguigu.pojo;

import java.io.Serializable;

/**
 * Address
 *
 * @Author: 马伟奇
 * @CreateTime: 2021-07-08
 * @Description:
 */

public class Address implements Serializable {
    private Integer id;
    private String addressName;
    private String lng;
    private String lat;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressName='" + addressName + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}