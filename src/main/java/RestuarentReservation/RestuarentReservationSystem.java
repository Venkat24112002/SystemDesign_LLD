package RestuarentReservation;


import java.time.LocalDateTime;
import java.util.*;

class Restuarent {
    private String id;
    private String name;
    public double longitude;
    public double latitude;
    int tables;

    public Restuarent(String id,String name, double longitude, double latitude, int tables) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tables = tables;
    }

    public int getTables() {
        return tables;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }
}

class Reservation {
    public LocalDateTime localDateTime;
    public int partySize;
    public String restaurentId;
    public String customerId;
    public int tableId;
    public String reservationId;

    public Reservation(String reservationId,  String restaurentId,LocalDateTime localDateTime,int partySize, String customerId, int tableId) {
        this.localDateTime = localDateTime;
        this.partySize = partySize;
        this.restaurentId = restaurentId;
        this.customerId = customerId;
        this.tableId = tableId;
        this.reservationId = reservationId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}



public class RestuarentReservationSystem {
    Map<String, Restuarent> restuarents;
    List<Reservation> reservations ;

    public RestuarentReservationSystem(){
        restuarents = new HashMap<>();
        reservations = new ArrayList<>();
    }

    public void addRestuarent(Restuarent restuarent){
        restuarents.put(restuarent.getId(),restuarent);
    }

    public List<Restuarent> listAvailablity(double userLat,double userLong, LocalDateTime localDateTime, int partySize) {
        List<Restuarent> availableRestaurents = new ArrayList<>();

        for(Restuarent restuarent: restuarents.values()){
            if(isNearBy(userLat,userLong,restuarent) && checkAvailability(restuarent,localDateTime,partySize)){
                availableRestaurents.add(restuarent);
            }
        }
        return availableRestaurents;
    }

    public Reservation reserve(String restaurantId, String customerId, LocalDateTime dateTime, int partySize) {
        Restuarent restaurant = restuarents.get(restaurantId);
        if (restaurant == null) {
            return null;
        }

        int availableTableId = findAvailableTable(restaurant, dateTime, partySize);
        if (availableTableId == -1) {
            return null;
        }

        String reservationId = generateReservationId();
        Reservation reservation = new Reservation(reservationId, restaurantId,dateTime,  partySize, customerId,  availableTableId);
        reservations.add(reservation);
        return reservation;
    }

    private String generateReservationId() {
        return "RES-" + System.currentTimeMillis();
    }

    boolean checkAvailability(Restuarent restuarent, LocalDateTime localDateTime, int partySize){
        return findAvailableTable(restuarent,localDateTime,partySize) != -1;
    }

    private int findAvailableTable(Restuarent restuarent,LocalDateTime localDateTime, int partySize){
        Set<Integer> occupiedTables = new HashSet<>();
        for(Reservation reservation: reservations){
            if(reservation.restaurentId.equals(restuarent.getId()) && reservation.getLocalDateTime().equals(localDateTime)) {
                occupiedTables.add(reservation.tableId);
            }
        }

        for (int tableId = 1; tableId <= restuarent.getTables(); tableId++) {
            String tableKey = restuarent.getId() + "-" + tableId;
            if (!occupiedTables.contains(tableKey)) {
                return tableId; // Return available table ID
            }
        }
        return -1;
    }

    public boolean isNearBy(double userLat, double userLong, Restuarent restuarent){
        double distance = calculateDistance(userLat, userLong, restuarent.latitude, restuarent.longitude);
        return distance <= 10.0; // 10km radius
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        // Simple distance calculation (Haversine formula)
        double R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }



}
