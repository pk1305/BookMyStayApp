import java.util.*;

// Service class (Add-On Service)
class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Manager class to handle Add-On Services
class AddOnServiceManager {
    // Map<ReservationID, List of Services>
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (Service s : getServices(reservationId)) {
            total += s.getPrice();
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation ID: " + reservationId);
        for (Service s : services) {
            System.out.println("- " + s.getName() + " : ₹" + s.getPrice());
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample Reservation ID
        String reservationId = "RES101";

        // Create some services
        Service breakfast = new Service("Breakfast", 500);
        Service wifi = new Service("Premium WiFi", 300);
        Service spa = new Service("Spa Access", 1500);

        // Add services to reservation
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, wifi);
        manager.addService(reservationId, spa);

        // Display selected services
        manager.displayServices(reservationId);
    }
}