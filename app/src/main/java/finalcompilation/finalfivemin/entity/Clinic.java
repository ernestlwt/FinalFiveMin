package finalcompilation.finalfivemin.entity;

import java.util.ArrayList;
import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Aiqing on 2/27/17.
 */

public class Clinic {
    private int ID;
    private String ClinicName;
    private String Address;
    private String Contact;
    private double Rating;
    private int NumOfRating;
    private LatLng Location;

    public Clinic(){}

    public Clinic (Clinic c){
        this.ClinicName = c.getClinicName();
        this.Address = c.getAddress();
        this.Contact = c.getContact();
        this.Rating = c.getRating();
        this.NumOfRating = c.getNumOfRating();
        this.Location = c.getLocation();
        this.ID = c.getID();
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }


    public int getNumOfRating() {
        return NumOfRating;
    }

    public void setNumOfRating(int numOfRating) {
        NumOfRating = numOfRating;
    }

    public LatLng getLocation() {
        return Location;
    }

    public void setLocation(LatLng location) {
        Location = location;
    }

    /*========= All get and set methods =========*/

}
