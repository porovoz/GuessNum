package Guess;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class GuessNum {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        Random random = new Random();
        String userName;
        do {
            System.out.println("What is your name?");
            userName = scanner.next();
            String name;
            if (args.length == 0) {
                name = userName;
            } else {
                name = args[0];
            }
            System.out.printf("Hello, %s!\n", name);


            int myNum = random.nextInt(100) + 1;
            System.out.println("I'm thinking of number from 1 to  100, try to guess it!");
            boolean userWin = false;


            for (int attempt = 1; attempt <= 10; attempt++) {
                System.out.printf("Attempt #%d\n", attempt);
                int userNum = askNumber("Enter your guess", 1, 100);
                if (myNum > userNum) {
                    System.out.println("Your number is too low");

                } else if (myNum < userNum) {
                    System.out.println("Your number is too high");
                } else if (myNum == userNum) {
                    System.out.printf("You won! %d attempts were used. \n", attempt);

                    userWin = true;
                    break;
                }
            }
            if (!userWin) {
                System.out.printf("You lost! My number was: %d\n", myNum);
            }
            System.out.println("Do you want to play again? (y/n)");
            String answer = askYesOrNo();

        } while (!scanner.next().equals("n"));

        System.out.println("Goodbye, " + userName);
    }



    public static int askNumber(String msg, int min, int max) {

        while (true) {
            System.out.print(msg + ": ");
            try {
                int result = scanner.nextInt();


                if (result < min) {
                    System.out.println("Number should not be less than " + min);
                    continue;
                }

                if (result > max) {
                    System.out.println("Number should not be greater than " + max);
                    continue;
                }


                return result;
            } catch (InputMismatchException e) {
                String input = scanner.next();

                System.out.println(input + " is not a number");

            }
        }
    }
    public static String askYesOrNo() {
        String answer;
        do {
            answer = scanner.next();
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Do you want to play again? (y/n)");
            } else {
                return answer;
            }
        } while (true);
        }
    }
