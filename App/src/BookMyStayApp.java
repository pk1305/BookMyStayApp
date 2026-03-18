import java.util.*;

// Reservation Class (Represents a booking)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Price: ₹" + price);
    }
}


// Booking History Manager
class BookingHistoryManager {

    private List<Reservation> bookingHistory;

    public BookingHistoryManager() {
        bookingHistory = new ArrayList<>();
    }

    // Add booking to history
    public void addReservation(Reservation reservation) {
        bookingHistory.add(reservation);
    }

    // Display all bookings
    public void displayAllBookings() {
        System.out.println("\n📜 Booking History:");
        for (Reservation r : bookingHistory) {
            r.display();
        }
    }

    // Get bookings by guest name
    public void getBookingsByGuest(String guestName) {
        System.out.println("\n🔍 Bookings for Guest: " + guestName);
        for (Reservation r : bookingHistory) {
            if (r.getGuestName().equalsIgnoreCase(guestName)) {
                r.display();
            }
        }
    }

    // Generate report (Total bookings & revenue)
    public void generateReport() {
        int totalBookings = bookingHistory.size();
        double totalRevenue = 0;

        for (Reservation r : bookingHistory) {
            totalRevenue += r.getPrice();
        }

        System.out.println("\n📊 Booking Report:");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}


// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingHistoryManager manager = new BookingHistoryManager();

        // Sample reservations
        Reservation r1 = new Reservation("R101", "Pooja", "Deluxe", 3000);
        Reservation r2 = new Reservation("R102", "Rahul", "Suite", 5000);
        Reservation r3 = new Reservation("R103", "Pooja", "Standard", 2000);

        // Add to history
        manager.addReservation(r1);
        manager.addReservation(r2);
        manager.addReservation(r3);

        // Display all bookings
        manager.displayAllBookings();

        // Search bookings by guest
        manager.getBookingsByGuest("Pooja");

        // Generate report
        manager.generateReport();
    }
}