import java.util.*;

/**
 * Book My Stay Application - Use Case 6
 * Demonstrates reservation confirmation and safe room allocation.
 *
 * @author YourName
 * @version 6.0
 */

// ---------- Reservation ----------
class Reservation {
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

// ---------- Booking Queue ----------
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ---------- Inventory ----------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void reduceAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// ---------- Booking Service ----------
class BookingService {

    // Track allocated room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type → allocated IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String roomType = r.getRoomType();

            System.out.println("\nProcessing booking for: " + r.getGuestName());

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                // Store in set
                allocatedRoomIds.add(roomId);

                // Map room type → room IDs
                roomAllocations.putIfAbsent(roomType, new HashSet<>());
                roomAllocations.get(roomType).add(roomId);

                // Update inventory
                inventory.reduceAvailability(roomType);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Allocated Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed! No rooms available for " + roomType);
            }
        }
    }

    // Generate room ID
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 1000);
    }
}

// ---------- Main ----------
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v6.0         ");
        System.out.println("===================================\n");

        // Initialize components
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Add booking requests
        queue.addRequest(new Reservation("Amit", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Sneha", "Single Room")); // may fail
        queue.addRequest(new Reservation("Priya", "Suite Room"));

        // Process bookings
        service.processBookings(queue, inventory);

        System.out.println("\nAll bookings processed.");
    }
}