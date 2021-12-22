package com.ship99_official.ship99_driver.Pojo;

public class InfoModel {
    String photo,name,phone,zoneLocation,email;

    public InfoModel(String email,String photo, String name,String phone, String zoneLocation, String zonezoneLocation) {
        this.photo = photo;
        this.email =email;
        this.phone  = phone;
        this.name = name;
        this.zoneLocation = zoneLocation;

    }

    public InfoModel() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getzoneLocation() {
        return zoneLocation;
    }


    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setzoneLocation(String zoneLocation) {
        this.zoneLocation = zoneLocation;
    }
}
