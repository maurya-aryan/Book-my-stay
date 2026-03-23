/**
 * Book My Stay Application - Use Case 2
 * This program demonstrates room types using abstraction, inheritance,
 * polymorphism, and static availability representation.
 *
 * @author YourName
 * @version 2.0
 */

// Abstract Class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    // Constructor
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Abstract method
    public abstract void displayDetails();
}

// Single Room Class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000.0);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

// Double Room Class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500.0);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

// Suite Room Class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000.0);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

// Main Class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("     Book My Stay App - v2.0       ");
        System.out.println("===================================\n");

        // Polymorphism (reference type = Room)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("---- Single Room ----");
        single.displayDetails();
        System.out.println("Available Rooms: " + singleAvailable);

        System.out.println("\n---- Double Room ----");
        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailable);

        System.out.println("\n---- Suite Room ----");
        suite.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailable);

        System.out.println("\nApplication Executed Successfully!");
    }
}