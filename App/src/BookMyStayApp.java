import java.io.*;
import java.util.*;

// Booking class (Serializable)
class Booking implements Serializable {
    String bookingId;
    String guestName;
    String roomType;

    Booking(String bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Wrapper class to persist full system state
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Booking> bookings;

    SystemState(Map<String, Integer> inventory, List<Booking> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // SAVE (Serialization)
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("✅ Data saved successfully!");

        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // LOAD (Deserialization)
    public static SystemState load() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("⚠ No saved data found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("✅ Data loaded successfully!");
            return (SystemState) ois.readObject();

        } catch (Exception e) {
            System.out.println("❌ Corrupted data. Starting fresh.");
            return null;
        }
    }
}

// Hotel System
class HotelSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private List<Booking> bookings = new ArrayList<>();

    public HotelSystem() {

        // Try to restore state
        SystemState state = PersistenceService.load();

        if (state != null) {
            this.inventory = state.inventory;
            this.bookings = state.bookings;
        } else {
            // Default data
            inventory.put("Single", 2);
            inventory.put("Double", 2);
        }
    }

    // Booking
    public void bookRoom(String bookingId, String guestName, String roomType) {

        if (inventory.getOrDefault(roomType, 0) > 0) {

            inventory.put(roomType, inventory.get(roomType) - 1);
            bookings.add(new Booking(bookingId, guestName, roomType));

            System.out.println("Booking SUCCESS: " + guestName);

        } else {
            System.out.println("Booking FAILED: No rooms available");
        }
    }

    // Save system state
    public void saveState() {
        SystemState state = new SystemState(inventory, bookings);
        PersistenceService.save(state);
    }

    public void showData() {

        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms: " + inventory.get(type));
        }

        System.out.println("\nBookings:");
        for (Booking b : bookings) {
            System.out.println(b.bookingId + " | " + b.guestName + " | " + b.roomType);
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        HotelSystem system = new HotelSystem();

        // Simulate operations
        system.bookRoom("B201", "Alice", "Single");
        system.bookRoom("B202", "Bob", "Double");

        system.showData();

        // Save before shutdown
        system.saveState();

        System.out.println("\n--- Restart Application to See Recovery ---");
    }
}