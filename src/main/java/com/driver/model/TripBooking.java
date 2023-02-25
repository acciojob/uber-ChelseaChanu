package com.driver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table

public class TripBooking{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripBookingId;
    private String fromLocation;
    private String toLocation;
    private int distanceInkm;
    private TripStatus status;
    private int bill;

    //maped with driver
    @ManyToOne
    @JoinColumn
    private Driver driver;

    //mapped with customer
    @ManyToOne
    @JoinColumn
    private Customer customer;

    public TripBooking() {

    }

    public TripBooking(String fromLocation, String toLocation, int distanceInkm, TripStatus status, int bill) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInkm = distanceInkm;
        this.status = status;
        this.bill = bill;
    }

    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInkm() {
        return distanceInkm;
    }

    public void setDistanceInkm(int distanceInkm) {
        this.distanceInkm = distanceInkm;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    } 
}