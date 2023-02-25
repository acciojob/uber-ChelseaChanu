package com.driver.services;

import com.driver.model.Customer;
import com.driver.model.TripBooking;


public interface CustomerService {

	public void register(Customer customer);

	public void deleteCustomer(Integer adminId);
	
	public TripBooking bookTrip(int adminId, String fromLocation, String toLocation, int distanceInKm) throws Exception;
	
	public void cancelTrip(Integer tripId);

	public void completeTrip(Integer tripId);
	
}
