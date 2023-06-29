package com.driver.Services;

import com.driver.Repositories.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.List;
import java.util.UUID;

public class HotelManagementService {

    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();
    public String addHotel(Hotel hotel) {

        String hotelName = hotel.getHotelName();
        if(hotelName == null || hotel == null){ return "FAILURE";}
        if(findDuplicateHotel(hotel)) return  "FAILURE";
        hotelManagementRepository.addHotel(hotel);
        return "SUCCESS";
    }

    private boolean findDuplicateHotel(Hotel hotel) {
        List<Hotel> hotelList = hotelManagementRepository.getHotels();
        for(Hotel hotel1 : hotelList){
            String name = hotel1.getHotelName();
            if(name.equals(hotel.getHotelName())){
                return true;
            }
        }
        return false;
    }


    public Integer addUser(User user) {
        hotelManagementRepository.addUser(user);

        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        String hotelName = "";
        List<Hotel> hotelList = hotelManagementRepository.getHotels();
        String name = "";
        int maxF = 0;
        for(Hotel hotel : hotelList) {
            if(maxF < hotel.getFacilities().size()) {
                maxF = hotel.getFacilities().size();
                name = hotel.getHotelName();
            } else if (maxF == hotel.getFacilities().size()) {
                if(name.compareTo(hotel.getHotelName()) > 0) {
                    name = hotel.getHotelName();
                }
            }
        }
        return name;
    }

    public int bookARoom(Booking booking) {
        int totalPrice = 0;
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        String hotelName = booking.getHotelName();
        Hotel hotel = hotelManagementRepository.findHotel(hotelName);
        int availableRooms = hotel.getAvailableRooms();
        int requiredRooms = booking.getNoOfRooms();
        if(requiredRooms > availableRooms) return -1;
        totalPrice = requiredRooms * hotel.getPricePerNight();
         hotel.setAvailableRooms(availableRooms-requiredRooms);
         booking.setAmountToBePaid(totalPrice);
         hotelManagementRepository.saveBooking(bookingId,booking);
         hotelManagementRepository.addHotel(hotel);
         int bookingadhaar = booking.getBookingAadharCard();
         hotelManagementRepository.savePersonBookings(bookingadhaar);
        return totalPrice;
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacility(List<Facility> newFacilities, String hotelName) {
        Hotel hotel =hotelManagementRepository.findHotel(hotelName);
        List<Facility> hotelFacilities = hotel.getFacilities();
        for(Facility facility : newFacilities){
            if(hotelFacilities.contains(facility)){
                continue;
            }else{
                hotelFacilities.add(facility);
            }
        }
        hotelManagementRepository.addHotel(hotel);
        return hotel;
    }
}
