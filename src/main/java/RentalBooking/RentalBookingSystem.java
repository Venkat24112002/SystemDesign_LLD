package RentalBooking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum VehicleType {
    CAR,
    BIKE
}

enum Status {
    ACTIVE,
    INACTIVE
}

//class vehicle
class Vehicle{

    int vehicleId;
    int vehicleNUmber;
    VehicleType vehicleType;
    String companyname;
    String modelName;
    int kmDriven;
    Date manuFacturingDate;
    int average;
    int cc;
    int dailyRentalCost;
    int hourlyRentalCost;
    int noOfSeat;
    Status status;

    //getters and setters

}

class Car extends Vehicle{
}

class Bike extends Vehicle {
}

//Location
class Location {
    String address;
    int pincode;
    String city;
    String state;
    String country;

    Location(int pincode, String city, String state, String country) {
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.country = country;

    }
}

class User {
    int userId;
    int userName;
    int drivingLicense;

    public User(int userId,int userName, int drivingLicense){
        this.userId = userId;
        this.userName = userName;
        this.drivingLicense = drivingLicense;
    }
    //getters and setters
}

class InventoryManagement {
    List<Vehicle> vehicles ;

     public InventoryManagement(List<Vehicle> vehicles){
         this.vehicles = vehicles;
     }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}

enum ReservationStatus {
    SCHEDULED,
    INPROGRESS,
    COMPLETED,
    CANCELLED;
}

enum ReservationType {
    HOURLY,
    DAILY;
}


class Reservation {
    int reservationId;
    User user;
    Vehicle vehicle;
    Date dateBookedFrom;
    Date dateBookedTo;
    Long fromTimeStamp;
    Long toTimeStamp;
    Location pickUpLocation;
    Location dropLocation;
    ReservationType reservationType;
    ReservationStatus reservationStatus;

    public Reservation(User user, Vehicle vehicle){
        // add random id;
        reservationId = 1223;
        this.user = user;
        this.vehicle = vehicle;
        reservationType = ReservationType.DAILY;
        reservationStatus = reservationStatus.SCHEDULED;
    }

    //CRUD Operations;
}



class Store {
    int storeId;
    InventoryManagement vehicleInventoryManagement;
    Location storeLocation;
    List<Reservation> reservations = new ArrayList<>();

    public List<Vehicle> getVehicle(VehicleType vehicleType){
        return vehicleInventoryManagement.getVehicles();
    }

    //addVehicles, update vehicles, use inventory management to update those.
    public void setVehicles(List<Vehicle> vehicles) {
        vehicleInventoryManagement = new InventoryManagement(vehicles);
    }

    public Reservation createReservation(Vehicle vehicle, User user){
        Reservation reservation = new Reservation(user,vehicle);
        reservations.add(reservation);
        return  reservation;
    }

    public boolean completeReservation(int reservationID) {

        for(int i=0;i<reservations.size();i++){
            Reservation currentReservation = reservations.get(i);
            if(currentReservation.reservationId == reservationID) {
                //if bill thing was there this would come place
                Vehicle vehicle = currentReservation.vehicle;
                vehicle.status = Status.INACTIVE;
                currentReservation.reservationStatus = ReservationStatus.COMPLETED;
                break;
            }
        }
        //take out the reservation from the list and call complete the reservation method.
        return true;
    }

    //update reservation

}

//enum PaymentMode {
//    CASH,
//    ONLINE;
//}

//class PaymentDetails {
//    int paymentId;
//    int amountPaid;
//    Date dateOfPayment;
//    boolean isRefundable;
//    PaymentMode paymentMode;
//}
//if bills come into place then we need to add a list of bills in store
//class Bill{
//    Reservation reservation;
//    double totalBillAmount;
//    boolean isBillPaid;
//
//    public Bill(Reservation reservation){
//        this.reservation = reservation;
//        this.totalBillAmount = computeBillAmount();
//        this.isBillPaid = false;
//    }
//
//    private double computeBillAmount(){
//        return 100.0;
//    }
//}
//
//class Payment {
//    public void payBill(Bill bill) {
//        //do payment processing and update the bill status;
//    }
//}
//


public class RentalBookingSystem {
    List<Store> storeList;
    List<User> userList;

    public RentalBookingSystem(List<Store> stores, List<User> user){

    }
}
