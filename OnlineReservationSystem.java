import java.util.*;

class User {
    String userId;
    String password;

    User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}

class Reservation {
    String userId;
    String trainNumber;
    String trainName;
    String classType;
    String dateOfJourney;
    String from;
    String to;
    String pnr;

    Reservation(String userId, String trainNumber, String trainName, String classType, String dateOfJourney, String from, String to, String pnr) {
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
        this.pnr = pnr;
    }
}

public class OnlineReservationSystem {
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Reservation> reservations = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeUsers();
        
        if (login()) {
            boolean exit = false;
            while (!exit) {
                System.out.println("\n1. Make a Reservation");
                System.out.println("\n2. Cancel a Reservation");
                System.out.println("\n3. Exit");
                System.out.print("\nEnter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();  // consume newline

                switch (choice) {
                    case 1:
                        makeReservation();
                        break;
                    case 2:
                        cancelReservation();
                        break;
                    case 3:
                        exit = true;
                        System.out.println("Thank you for using the Online Reservation System.");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } else {
            System.out.println("Login failed! Exiting system.");
        }
    }

    private static void initializeUsers() {
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));
        // Add more users as needed
    }

    private static boolean login() {
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User user = users.get(userId);
        if (user != null && user.password.equals(password)) {
            System.out.println("Login successful!");
            return true;
        }
        return false;
    }

    private static void makeReservation() {
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter Train Number: ");
        String trainNumber = sc.nextLine();
        System.out.print("Enter Train Name: ");
        String trainName = sc.nextLine();
        System.out.print("Enter Class Type: ");
        String classType = sc.nextLine();
        System.out.print("Enter Date of Journey: ");
        String dateOfJourney = sc.nextLine();
        System.out.print("Enter From: ");
        String from = sc.nextLine();
        System.out.print("Enter To: ");
        String to = sc.nextLine();

        String pnr = UUID.randomUUID().toString();  // Generate unique PNR
        Reservation reservation = new Reservation(userId, trainNumber, trainName, classType, dateOfJourney, from, to, pnr);
        reservations.put(pnr, reservation);
        
        System.out.println("Reservation successful! Your PNR is " + pnr);
    }

    private static void cancelReservation() {
        System.out.print("Enter PNR Number: ");
        String pnr = sc.nextLine();

        Reservation reservation = reservations.get(pnr);
        if (reservation != null) {
            System.out.println("Reservation found: ");
            System.out.println("User ID: " + reservation.userId);
            System.out.println("Train Number: " + reservation.trainNumber);
            System.out.println("Train Name: " + reservation.trainName);
            System.out.println("Class Type: " + reservation.classType);
            System.out.println("Date of Journey: " + reservation.dateOfJourney);
            System.out.println("From: " + reservation.from);
            System.out.println("To: " + reservation.to);

            System.out.print("Confirm cancellation (yes/no): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                reservations.remove(pnr);
                System.out.println("Cancellation successful.");
            } else {
                System.out.println("Cancellation aborted.");
            }
        } else {
            System.out.println("No reservation found with PNR " + pnr);
        }
    }
}
