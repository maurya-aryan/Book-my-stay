import java.util.*;

/**
 * Book My Stay Application - Use Case 11
 * Demonstrates concurrent booking simulation with thread safety.
 *
 * @author YourName
 * @version 11.0
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

// ---------- Thread-Safe Inventory ----------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
    }

    // synchronized method (critical section)
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// ---------- Shared Booking Queue ----------
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    // synchronized add
    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    // synchronized remove
    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// ---------- Booking Processor (Thread) ----------
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // critical section for queue access
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            System.out.println(Thread.currentThread().getName() +
                    " processing " + r.getGuestName());

            // synchronized allocation
            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println("Booking Confirmed for " + r.getGuestName());
            } else {
                System.out.println("Booking Failed for " + r.getGuestName());
            }
        }
    }
}

// ---------- Main ----------
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v11.0        ");
        System.out.println("===================================\n");

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // Simulate multiple requests
        queue.addRequest(new Reservation("Amit", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Sneha", "Single Room")); // extra

        // Create multiple threads
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely!");
    }
}