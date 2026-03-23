import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application - Use Case 3
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author YourName
 * @version 3.0
 */

// Inventory Class (Single Source of Truth)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a specific room
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (increase/decrease)
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, inventory.get(roomType) + count);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("----- Current Room Inventory -----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v3.0         ");
        System.out.println("===================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Simulate booking (reduce availability)
        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability("Single Room", -1);

        // Simulate cancellation (increase availability)
        System.out.println("Cancelling 1 Suite Room...");
        inventory.updateAvailability("Suite Room", 1);

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication Executed Successfully!");
    }
}