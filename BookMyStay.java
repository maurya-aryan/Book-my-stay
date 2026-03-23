import java.util.*;

/**
 * Book My Stay Application - Use Case 10
 * Demonstrates booking cancellation and inventory rollback using Stack.
 *
 * @author YourName
 * @version 10.0
 */

// ---------- Reservation ----------
class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ---------- Inventory ----------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// ---------- Booking History ----------
class BookingHistory {
    private Map<String, Reservation> confirmedBookings = new HashMap<>();

    public void addBooking(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getBooking(String id) {
        return confirmedBookings.get(id);
    }

    public void removeBooking(String id) {
        confirmedBookings.remove(id);
    }
}

// ---------- Cancellation Service ----------
class CancellationService {

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        Reservation r = history.getBooking(reservationId);

        // Validation
        if (r == null) {
            System.out.println("\nCancellation Failed: Invalid Reservation ID");
            return;
        }

        // Push to stack (track rollback)
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increaseAvailability(r.getRoomType());

        // Remove from history
        history.removeBooking(reservationId);

        System.out.println("\nBooking Cancelled Successfully!");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Room Released: " + r.getRoomType());
    }

    // Show rollback history
    public void showRollbackHistory() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// ---------- Main ----------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v10.0        ");
        System.out.println("===================================\n");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // Simulate confirmed bookings
        history.addBooking(new Reservation("SR-101", "Single Room"));
        history.addBooking(new Reservation("DR-202", "Double Room"));

        // Cancel booking
        cancelService.cancelBooking("SR-101", history, inventory);

        // Invalid cancellation
        cancelService.cancelBooking("XX-999", history, inventory);

        // Display inventory
        inventory.displayInventory();

        // Show rollback stack
        cancelService.showRollbackHistory();

        System.out.println("\nSystem state restored successfully!");
    }
}