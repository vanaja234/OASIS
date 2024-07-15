import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class BankAccount {
    private String name; 
    private String userName;
    private String password;
    private String accountNo;
    private float balance = 10000f;
    private int pin;
    private int failedLoginAttempts = 0;
    private String transactionHistory = "";
    private int transactions = 0;

    public BankAccount() {
    }

    public String getName() {
        return name;
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("****** Registration ******");
        System.out.print("Enter Your Name: ");
        this.name = scanner.nextLine(); // Set the user's name
        System.out.print("Enter Your Username: ");
        this.userName = scanner.nextLine();
        System.out.print("Enter Your Password: ");
        this.password = scanner.nextLine();
        System.out.print("Enter Your Account Number: ");
        this.accountNo = scanner.nextLine();
        System.out.print("Set Your 4-Digit PIN: ");
        this.pin = scanner.nextInt();
        System.out.println("Registration completed. Please login.");
        saveCredentials();
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n****** Login ******");
        while (failedLoginAttempts < 3) {
            System.out.print("Enter Your Username: ");
            String inputUsername = scanner.nextLine();
            System.out.print("Enter Your Password: ");
            String inputPassword = scanner.nextLine();
            if (checkCredentials(inputUsername, inputPassword)) {
                System.out.println("Login successful!");
                failedLoginAttempts = 0;
                return true;
            } else {
                failedLoginAttempts++;
                if (failedLoginAttempts < 3) {
                    System.out.println("Incorrect Username or Password.");
                }
            }
        }
        System.out.println("Too many failed login attempts. Returning to main screen.");
        failedLoginAttempts = 0;
        return false;
    }

    private void saveCredentials() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("credentials.txt", true))) {
            writer.println(userName + "," + password + "," + pin); // Save pin along with username and password
        } catch (IOException e) {
            System.out.println("Error saving credentials.");
        }
    }

    private boolean checkCredentials(String inputUsername, String inputPassword) {
        try (Scanner scanner = new Scanner(new File("credentials.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(inputUsername) && parts[1].equals(inputPassword)) {
                    this.pin = Integer.parseInt(parts[2]); // Assign the pin from file
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading credentials.");
        }
        return false;
    }

    public void withdraw() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to withdraw: ");
        float amount = scanner.nextFloat();
        System.out.print("Enter your 4-digit PIN: ");
        int enteredPin = scanner.nextInt();

        if (enteredPin == pin) {
            if (amount > 0 && amount <= balance) {
                transactions++;
                balance -= amount;
                System.out.println("Withdrawal of $" + amount + " successful.");
                String str = amount + " Rs Withdrawn\n";
                transactionHistory = transactionHistory.concat(str);
                System.out.println("Remaining balance: $" + balance); // Display remaining balance
            } else {
                System.out.println("Error: Insufficient funds or invalid amount.");
            }
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    public void deposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to deposit: ");
        float amount = scanner.nextFloat();
        System.out.print("Enter your 4-digit PIN: ");
        int enteredPin = scanner.nextInt();

        if (enteredPin == pin) {
            if (amount > 0 && amount <= 100000f) {
                transactions++;
                balance += amount;
                System.out.println("Deposit of $" + amount + " successful.");
                String str = amount + " Rs deposited\n";
                transactionHistory = transactionHistory.concat(str);
            } else {
                System.out.println("Error: Invalid amount or maximum deposit limit exceeded.");
            }
        } else {
            System.out.println("Incorrect PIN.");
        }
    }

    public void checkBalance() {
        System.out.println("Your current balance is: $" + balance);
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History:\n" + transactionHistory);
    }
}

public class AtmInterface {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n**********WELCOME TO BANK ATM SYSTEM**********\n");

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Exit");
            System.out.print("Enter Your Choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    account.register();
                    break;
                case 2:
                    if (account.login()) {
                        System.out.println("\n**********WELCOME BACK, " + account.getName() + " **********\n");
                        boolean isFinished = false;
                        while (!isFinished) {
                            System.out.println("Banking Options:");
                            System.out.println("1. Withdraw\n2. Deposit\n3. Check Balance\n4. View Transaction History\n5. Exit");
                            System.out.print("Enter Your Choice: ");
                            int option = scanner.nextInt();
                            switch (option) {
                                case 1:
                                    account.withdraw();
                                    break;
                                case 2:
                                    account.deposit();
                                    break;
                                case 3:
                                    account.checkBalance();
                                    break;
                                case 4:
                                    account.viewTransactionHistory();
                                    break;
                                case 5:
                                    isFinished = true;
                                    System.out.println("Exiting the ATM. Goodbye!");
                                    System.exit(0); 
                                    break;
                                default:
                                    System.out.println("Invalid option. Please try again.");
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println("Exiting the ATM. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
