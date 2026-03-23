import java.util.*;

/**
 * Book My Stay Application - Use Case 9
 * Demonstrates error handling and validation using custom exceptions.
 *
 * @author YourName
 * @version 9.0
 */

// ---------- Custom Exception ----------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// ---------- Inventory ----------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) throws InvalidBookingException {
        int current = getAvailability(roomType);

        if (current <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        inventory.put(roomType, current - 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }
}

// ---------- Validator ----------
class BookingValidator {

    public void validate(Reservation r, RoomInventory inventory) throws InvalidBookingException {

        // Validate guest name
        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.getRoomType());
        }
    }
}

// ---------- Booking Service ----------
class BookingService {

    public void confirmBooking(Reservation r, RoomInventory inventory) {

        BookingValidator validator = new BookingValidator();

        try {
            // Validation (Fail Fast)
            validator.validate(r, inventory);

            // If valid → allocate
            inventory.reduceAvailability(r.getRoomType());

            System.out.println("\nBooking Confirmed!");
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room: " + r.getRoomType());

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("\nBooking Failed: " + e.getMessage());
        }
    }
}

// ---------- Main ----------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("   Book My Stay App - v9.0         ");
        System.out.println("===================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Test cases

        // Valid booking
        service.confirmBooking(new Reservation("Amit", "Single Room"), inventory);

        // Invalid room type
        service.confirmBooking(new Reservation("Rahul", "Luxury Room"), inventory);

        // No availability
        service.confirmBooking(new Reservation("Sneha", "Suite Room"), inventory);

        // Empty name
        service.confirmBooking(new Reservation("", "Double Room"), inventory);

        System.out.println("\nSystem running safely after errors.");
    }
}