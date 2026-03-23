import java.util.*;

/**
 * Book My Stay Application - Use Case 7
 * Demonstrates add-on service selection without modifying core booking logic.
 *
 * @author YourName
 * @version 7.0
 */

// ---------- Add-On Service ----------
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " - ₹" + cost);
    }
}

// ---------- Add-On Service Manager ----------
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.displayService();
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService s : services) {
            total += s.getCost();
        }

        return total;
    }
}

// ---------- Main ----------
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v7.0         ");
        System.out.println("===================================\n");

        // Example reservation IDs (from previous use case)
        String reservation1 = "SR-101";
        String reservation2 = "DR-202";

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService spa = new AddOnService("Spa Access", 1500);

        // Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservation1, breakfast);
        manager.addService(reservation1, wifi);
        manager.addService(reservation2, spa);

        // Display services
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);

        // Calculate total cost
        System.out.println("\nTotal Add-On Cost for " + reservation1 + ": ₹" +
                manager.calculateTotalCost(reservation1));

        System.out.println("Total Add-On Cost for " + reservation2 + ": ₹" +
                manager.calculateTotalCost(reservation2));

        System.out.println("\nAdd-on services processed successfully!");
    }
}