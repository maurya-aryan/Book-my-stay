import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay Application - Use Case 5
 * Demonstrates booking request handling using Queue (FIFO).
 *
 * @author YourName
 * @version 5.0
 */

// ---------- Reservation Class ----------
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// ---------- Booking Queue ----------
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request Added: " + reservation.getGuestName());
    }

    // Display all requests
    public void displayQueue() {
        System.out.println("\n----- Booking Request Queue (FIFO) -----");

        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

// ---------- Main Class ----------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v5.0         ");
        System.out.println("===================================\n");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate booking requests
        Reservation r1 = new Reservation("Amit", "Single Room");
        Reservation r2 = new Reservation("Rahul", "Double Room");
        Reservation r3 = new Reservation("Sneha", "Suite Room");

        // Add requests (FIFO order)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored in FIFO order.");
        System.out.println("No inventory changes made at this stage.");
    }
}