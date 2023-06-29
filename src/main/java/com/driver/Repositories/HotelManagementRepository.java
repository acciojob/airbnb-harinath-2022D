package com.driver.Repositories;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementRepository {

    private Map<String,Hotel> hotelMap = new HashMap<>();
    private Map<String,User> userMap = new HashMap<>();
    private Map<String,Booking> bookingMap = new HashMap<>();
    private Map<Integer,Integer> personBookings = new HashMap<>();
    public void addHotel(Hotel hotel) {
        hotelMap.put(hotel.getHotelName(),hotel);
    }

    public void addUser(User user) {
        userMap.put(user.getName(), user);
    }

    public List<Hotel> getHotels() {
        Collection<Hotel> values = hotelMap.values();
        List<Hotel> hotelList = new ArrayList<>(values);
        return hotelList;
    }

    public Hotel findHotel(String hotelName) {
        for(String name : hotelMap.keySet()){
            if(name.equals(hotelName))
                return hotelMap.get(name);
        }
        return null;
    }

    public void saveBooking(String bookingId, Booking booking) {
        bookingMap.put(bookingId,booking);
    }

    public void savePersonBookings(int bookingadhaar) {
        personBookings.put(bookingadhaar,personBookings.getOrDefault(bookingadhaar,0)+1);
    }

    public int getBookings(Integer aadharCard) {
        return personBookings.get(aadharCard);
    }
}
