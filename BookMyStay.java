import java.util.HashMap;

/**
 * Book My Stay Application - Use Case 4
 * Demonstrates room search with read-only access to inventory.
 *
 * @author YourName
 * @version 4.0
 */

// ---------- Room Domain Model ----------
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public abstract void displayDetails();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000.0);
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500.0);
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000.0);
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

// ---------- Inventory (Read-only used here) ----------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // intentionally 0 to test filtering
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// ---------- Search Service ----------
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory) {

        System.out.println("----- Available Rooms -----\n");

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.roomType);

            // Filter unavailable rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println();
            }
        }
    }
}

// ---------- Main Class ----------
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v4.0         ");
        System.out.println("===================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Search service (read-only)
        RoomSearchService searchService = new RoomSearchService();

        // Perform search
        searchService.searchAvailableRooms(inventory);

        System.out.println("Search Completed Successfully!");
    }
}