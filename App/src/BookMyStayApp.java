
import java.util.*;

public class BookMyStayApp {

    // ===============================
    // Reservation Class
    // ===============================
    static class Reservation {

        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // ===============================
    // Booking Request Queue (FIFO)
    // ===============================
    static class BookingRequestQueue {

        private Queue<Reservation> requestQueue;

        public BookingRequestQueue() {
            requestQueue = new LinkedList<>();
        }

        public void addRequest(Reservation reservation) {
            requestQueue.offer(reservation);
        }

        public Reservation getNextRequest() {
            return requestQueue.poll();
        }

        public boolean hasPendingRequests() {
            return !requestQueue.isEmpty();
        }
    }

    // ===============================
    // Room Inventory
    // ===============================
    static class RoomInventory {

        private Map<String, Integer> availability;

        public RoomInventory() {
            availability = new HashMap<>();
            availability.put("Single", 5);
            availability.put("Double", 3);
            availability.put("Suite", 2);
        }

        public int getAvailableRooms(String roomType) {
            return availability.getOrDefault(roomType, 0);
        }

        public void decreaseRoom(String roomType) {
            availability.put(roomType, availability.get(roomType) - 1);
        }
    }

    // ===============================
    // Room Allocation Service
    // ===============================
    static class RoomAllocationService {

        private Set<String> allocatedRoomIds;
        private Map<String, Set<String>> assignedRoomsByType;
        private Map<String, Integer> roomCounters;

        public RoomAllocationService() {

            allocatedRoomIds = new HashSet<>();
            assignedRoomsByType = new HashMap<>();
            roomCounters = new HashMap<>();

            roomCounters.put("Single", 0);
            roomCounters.put("Double", 0);
            roomCounters.put("Suite", 0);
        }

        public void allocateRoom(Reservation reservation, RoomInventory inventory) {

            String roomType = reservation.getRoomType();

            if (inventory.getAvailableRooms(roomType) <= 0) {
                System.out.println("No available rooms for " + reservation.getGuestName());
                return;
            }

            String roomId = generateRoomId(roomType);

            allocatedRoomIds.add(roomId);

            assignedRoomsByType
                    .computeIfAbsent(roomType, k -> new HashSet<>())
                    .add(roomId);

            inventory.decreaseRoom(roomType);

            System.out.println(
                    "Booking confirmed for Guest: "
                            + reservation.getGuestName()
                            + ", Room ID: "
                            + roomId);
        }

        private String generateRoomId(String roomType) {

            int count = roomCounters.get(roomType) + 1;
            roomCounters.put(roomType, count);

            return roomType + "-" + count;
        }
    }

    // ===============================
    // MAIN METHOD
    // ===============================
    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        RoomInventory inventory = new RoomInventory();

        RoomAllocationService allocationService = new RoomAllocationService();

        while (bookingQueue.hasPendingRequests()) {

            Reservation request = bookingQueue.getNextRequest();

            allocationService.allocateRoom(request, inventory);
        }
    }
}
