import java.util.*;

class Booking {
    String bookingId;
    String roomType;
    String roomId;
    boolean isCancelled;

    Booking(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

class HotelSystem {

    // Inventory of rooms (roomType -> available count)
    private Map<String, Integer> inventory = new HashMap<>();

    // Booking records (bookingId -> Booking object)
    private Map<String, Booking> bookings = new HashMap<>();

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public HotelSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
    }

    // Book a room
    public void bookRoom(String bookingId, String roomType) {
        if (!inventory.containsKey(roomType) || inventory.get(roomType) == 0) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        String roomId = roomType + "-" + inventory.get(roomType);

        Booking booking = new Booking(bookingId, roomType, roomId);
        bookings.put(bookingId, booking);

        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("Booking successful: " + bookingId + " | Room: " + roomId);
    }

    // Cancel booking (Main Use Case 10 logic)
    public void cancelBooking(String bookingId) {

        // Step 1: Validate booking
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Invalid booking ID!");
            return;
        }

        Booking booking = bookings.get(bookingId);

        if (booking.isCancelled) {
            System.out.println("Booking already cancelled!");
            return;
        }

        // Step 2: Push room ID to rollback stack
        rollbackStack.push(booking.roomId);

        // Step 3: Restore inventory
        String roomType = booking.roomType;
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Step 4: Mark booking cancelled
        booking.isCancelled = true;

        // Step 5: Print status
        System.out.println("Booking cancelled successfully: " + bookingId);
        System.out.println("Room released: " + booking.roomId);
    }

    // Display inventory
    public void showInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms: " + inventory.get(type));
        }
    }

    // Display rollback stack
    public void showRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        HotelSystem system = new HotelSystem();

        // Book rooms
        system.bookRoom("B101", "Single");
        system.bookRoom("B102", "Double");

        system.showInventory();

        // Cancel booking
        system.cancelBooking("B101");

        system.showInventory();
        system.showRollbackStack();

        // Try invalid cases
        system.cancelBooking("B999"); // Invalid
        system.cancelBooking("B101"); // Already cancelled
    }
}