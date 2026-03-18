import java.util.*;

// Booking Request class
class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Hotel System with shared resources
class HotelSystem {

    // Shared inventory
    private Map<String, Integer> inventory = new HashMap<>();

    // Shared booking queue
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();

    public HotelSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
    }

    // Add booking request (Producer)
    public synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println("Request added: " + request.guestName + " -> " + request.roomType);
    }

    // Process booking request (Consumer)
    public void processRequest() {

        BookingRequest request;

        // Synchronize only queue access
        synchronized (this) {
            if (bookingQueue.isEmpty()) {
                return;
            }
            request = bookingQueue.poll();
        }

        // Critical Section (inventory update)
        synchronized (this) {

            String roomType = request.roomType;

            if (inventory.getOrDefault(roomType, 0) > 0) {
                inventory.put(roomType, inventory.get(roomType) - 1);

                System.out.println(Thread.currentThread().getName()
                        + " BOOKED for " + request.guestName
                        + " | Room Type: " + roomType);
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " FAILED for " + request.guestName
                        + " | No rooms available");
            }
        }
    }

    public void showInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms: " + inventory.get(type));
        }
    }
}

// Thread class
class BookingProcessor extends Thread {

    private HotelSystem system;

    BookingProcessor(HotelSystem system, String name) {
        super(name);
        this.system = system;
    }

    @Override
    public void run() {
        // Each thread tries to process multiple requests
        for (int i = 0; i < 3; i++) {
            system.processRequest();

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        HotelSystem system = new HotelSystem();

        // Simulate multiple guest requests
        system.addRequest(new BookingRequest("Alice", "Single"));
        system.addRequest(new BookingRequest("Bob", "Single"));
        system.addRequest(new BookingRequest("Charlie", "Single"));
        system.addRequest(new BookingRequest("David", "Double"));
        system.addRequest(new BookingRequest("Eve", "Double"));

        // Create multiple threads (Concurrent users)
        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");
        BookingProcessor t3 = new BookingProcessor(system, "Thread-3");

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Show final inventory
        system.showInventory();
    }
}