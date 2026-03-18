import java.util.*;

// Reservation class
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory class
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 0);
    }

    public boolean isAvailable(String roomType) {
        return inventory.containsKey(roomType) && inventory.get(roomType) > 0;
    }

    public void bookRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

// Booking System with Validation
class BookingSystem {
    private RoomInventory inventory;

    public BookingSystem(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation reservation) {

        // Validation checks
        if (reservation.guestName == null || reservation.guestName.isEmpty()) {
            System.out.println("Error: Guest name cannot be empty");
            return;
        }

        if (reservation.roomType == null || reservation.roomType.isEmpty()) {
            System.out.println("Error: Room type cannot be empty");
            return;
        }

        if (!inventory.isAvailable(reservation.roomType)) {
            System.out.println("Error: Room not available or invalid type");
            return;
        }

        // If all valid → proceed
        inventory.bookRoom(reservation.roomType);
        System.out.println("Booking successful for " + reservation.guestName);
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingSystem system = new BookingSystem(inventory);

        inventory.displayInventory();

        // Test cases
        Reservation r1 = new Reservation("Pooja", "Single");
        Reservation r2 = new Reservation("", "Double");        // Invalid name
        Reservation r3 = new Reservation("Rahul", "Suite");    // Not available

        system.processBooking(r1);
        system.processBooking(r2);
        system.processBooking(r3);

        inventory.displayInventory();
    }
}