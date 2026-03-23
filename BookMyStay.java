import java.util.*;

/**
 * Book My Stay Application - Use Case 8
 * Demonstrates booking history tracking and reporting.
 *
 * @author YourName
 * @version 8.0
 */

// ---------- Reservation ----------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// ---------- Booking History ----------
class BookingHistory {

    // List maintains insertion order
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation r) {
        history.add(r);
        System.out.println("Added to history: " + r.getReservationId());
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// ---------- Reporting Service ----------
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> reservations) {

        System.out.println("\n----- Booking History -----");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n----- Booking Summary Report -----");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {
            String roomType = r.getRoomType();
            roomCount.put(roomType, roomCount.getOrDefault(roomType, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
        }
    }
}

// ---------- Main ----------
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v8.0         ");
        System.out.println("===================================\n");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("SR-101", "Amit", "Single Room"));
        history.addReservation(new Reservation("DR-202", "Rahul", "Double Room"));
        history.addReservation(new Reservation("SR-103", "Sneha", "Single Room"));
        history.addReservation(new Reservation("SU-301", "Priya", "Suite Room"));

        // Reporting
        BookingReportService reportService = new BookingReportService();

        reportService.showAllBookings(history.getAllReservations());
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nReporting Completed Successfully!");
    }
}