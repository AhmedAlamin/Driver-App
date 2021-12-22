package com.ship99_official.ship99_driver.Pojo;

import java.io.Serializable;


public class PickupModel implements Serializable{
    //still not added to firebase automatically
    private String PickupAddress;
    private String weight;
    private String nearest_sign_note;
    private String notes_of_shipping;
    private String photoOfProduct;




    public PickupModel() {
    }

    public PickupModel( String pickupAddress, String weight, String nearest_sign_note, String notes_of_shipping, String photoOfProduct) {

        this.PickupAddress = pickupAddress;
        this.weight = weight;
        this.nearest_sign_note = nearest_sign_note;
        this.notes_of_shipping = notes_of_shipping;
        this.photoOfProduct = photoOfProduct;


    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPickupAddress(String pickupAddress) {
        PickupAddress = pickupAddress;
    }


    public void setNearest_sign_note(String nearest_sign_note) {
        this.nearest_sign_note = nearest_sign_note;
    }

    public void setNotes_of_shipping(String notes_of_shipping) {
        this.notes_of_shipping = notes_of_shipping;
    }

    public void setPhotoOfProduct(String photoOfProduct) {
        this.photoOfProduct = photoOfProduct;
    }



    public String getPickupAddress() {
        return PickupAddress;
    }


    public String getNearest_sign_note() {
        return nearest_sign_note;
    }

    public String getNotes_of_shipping() {
        return notes_of_shipping;
    }

    public String getPhotoOfProduct() {
        return photoOfProduct;
    }
}
