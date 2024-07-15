import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int min = 1;
        int max = 100;
        int maxAttempts = 10;
        int score = 0;
        int rounds = 3;

        System.out.println("Welcome to the Guess the Number Game!");

        for (int round = 1; round <= rounds; round++) {
            int numberToGuess = random.nextInt(max - min + 1) + min;
            int attempts = 0;
            boolean hasGuessed = false;

            System.out.println("Round " + round + " of " + rounds);
            System.out.println("I have selected a number between " + min + " and " + max + ". Can you guess it?");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("Higher!");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Lower!");
                } else {
                    System.out.println("Congratulations! You've guessed the number.");
                    hasGuessed = true;
                    score += (maxAttempts - attempts + 1);  // More points for fewer attempts
                    break;
                }
            }

            if (!hasGuessed) {
                System.out.println("Sorry, you've used all your attempts. The number was: " + numberToGuess);
            }

            System.out.println("Your current score is: " + score);
            System.out.println();
        }

        System.out.println("Game over! Your final score is: " + score);
        scanner.close();
    }
}
