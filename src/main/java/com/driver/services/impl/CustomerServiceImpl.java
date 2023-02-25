package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer adminId) {
		// Delete customer without using deleteById function
		if(customerRepository2.findById(adminId).isPresent()){
			customerRepository2.deleteById(adminId);
		}
	}

	@Override
	public TripBooking bookTrip(int adminId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		TripBooking tripBooking = new TripBooking();
		Driver driver = null;
		List<Driver> listOfDrivers = driverRepository2.findAll();
	
		for(Driver drivers:listOfDrivers){
			if(drivers.getCab().getAvailable()==Boolean.TRUE){
				if(driver==null || driver.getDriverId()>drivers.getDriverId())
					driver = drivers;
			}
		}

		if(driver==null){
			throw new Exception("No cab available!");
		}
		else{
			//setting tripBooking
			tripBooking.setFromLocation(fromLocation);
			tripBooking.setToLocation(toLocation);
			tripBooking.setDistanceInKm(distanceInKm);
			tripBooking.setStatus(TripStatus.CONFIRMED);
			tripBooking.setDriver(driver);	

			Customer customer = customerRepository2.findById(adminId).get();
			tripBooking.setCustomer(customer);
			customer.getTripBookingList().add(tripBooking);
			driver.getTripBookingList().add(tripBooking);
			driver.getCab().setAvailable(Boolean.FALSE);
			tripBooking.setBill(distanceInKm*driver.getCab().getPerKmRate());

			customerRepository2.save(customer);
			driverRepository2.save(driver);
		}
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		if(tripBooking!=null){
			tripBooking.setStatus(TripStatus.CANCELED);
			tripBooking.setBill(0);
			tripBooking.getDriver().getCab().setAvailable(true);
			tripBookingRepository2.save(tripBooking);
		}
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		if(tripBooking!=null){
			tripBooking.setStatus(TripStatus.COMPLETED);
			tripBooking.getDriver().getCab().setAvailable(true);
			tripBookingRepository2.save(tripBooking);
		}
	}
}
