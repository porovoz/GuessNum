package Guess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class GuessNum1 {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<GameResult> leaderboard = new ArrayList<>();
    private static File leaderboardFile = new File("leaderboard.txt");

    public static void main(String[] args) {
        loadLeaderboard();

        Random random = new Random();
        String userName;
        String answer;
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
            long startTime = System.currentTimeMillis();
            boolean userWin = false;
            for (int attempt = 1; attempt <= 10; attempt++) {
                System.out.printf("Attempt #%d\n", attempt);
                int userNum = askNumber("Enter your guess", 1, 100);
                if (myNum > userNum) {
                    System.out.println("Your number is too low");

                } else if (myNum < userNum) {
                    System.out.println("Your number is too high");
                } else {
                    long endTime = System.currentTimeMillis();
                    GameResult gr = new GameResult();
                    gr.setName(name);
                    gr.setAttempts(attempt);
                    gr.setDuration(endTime - startTime);
                    leaderboard.add(gr);


                    System.out.printf("You won! %d attempts were used. \n", attempt);
                    userWin = true;
                    break;
                }
            }
            if (!userWin) {
                System.out.printf("You lost! My number was: %d\n", myNum);
            }
            System.out.println("Do you want to play again? (y/n)");
            answer = askYesOrNo();
        } while (answer.equals("y"));


        saveLeaderboard();
        printLeaderboard2();
        userNameMaxLength();


        System.out.println("Goodbye, " + userName);
    }

    private static void loadLeaderboard() {
        try (var in = new Scanner(leaderboardFile)) {
            while (in.hasNext()) {
                var gr = new GameResult();
                gr.setName(in.next());
                gr.setAttempts(in.nextInt());
                gr.setDuration(in.nextLong());
                leaderboard.add(gr);
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("Cannot read leaderboard");
        }

    }


    private static void saveLeaderboard() {
        try (var out = new PrintWriter(leaderboardFile)) {
            for (var gr :
                    leaderboard) {
                out.printf("%s %d %d\n", gr.getName(), gr.getAttempts(), gr.getDuration());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save leaderboard");
        }
    }


    private static void printLeaderboard() {
        leaderboard.sort(
                Comparator
                        .comparing(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );

        for (GameResult gr : leaderboard) {
            System.out.printf("%s \t %d \t %.1f \n", gr.getName(), gr.getAttempts(), gr.getDuration() / 1000.0);
        }
    }

    private static void printLeaderboard2() {
        leaderboard.sort(
                Comparator
                        .comparing(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );

        var rows = 5;
        for (GameResult gr : leaderboard) {
            System.out.printf("%-11s \t %8d \t %5.1f \n", gr.getName(), gr.getAttempts(), gr.getDuration() / 1000.0);
            rows--;
            if (rows == 0) {
                break;
            }
        }
    }

    private static void printLeaderboard3() {
        leaderboard.sort(
                Comparator
                        .comparing(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );

        var num = Math.min(5, leaderboard.size());
        var sublist = leaderboard.subList(0, num);
        for (GameResult gr : sublist) {
            System.out.printf("%s \t %d \t %.1f \n", gr.getName(), gr.getAttempts(), gr.getDuration() / 1000.0);
        }
    }

    private static void printLeaderboard4() {
        leaderboard.sort(
                Comparator
                        .comparing(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );

        for (int i = 0; i < 5 && i < leaderboard.size(); i++) {
            var gr = leaderboard.get(i);
            System.out.printf("%s \t %d \t %.1f \n", gr.getName(), gr.getAttempts(), gr.getDuration() / 1000.0);
        }
    }

    public static void printLeaderboard5() {
        leaderboard.stream()
                .sorted(
                        Comparator
                                .comparingInt(GameResult::getAttempts)
                                .thenComparingLong(GameResult::getDuration)
                )
                .limit(5)
                .forEach(gr -> {
                    System.out.printf("%s \t %d \t %.1f \n", gr.getName(), gr.getAttempts(), gr.getDuration() / 1000.0);
                });
    }

    public static int userNameMaxLength() {
        int userNameMaxLength = 0;
        var format = "%-" + userNameMaxLength + "s %8 %5.1fs%n";
        for (GameResult gr : leaderboard) {
            var length = gr.getName().length();
            if (userNameMaxLength < length) {
                userNameMaxLength = length;
                System.out.printf(format, gr.getName(), gr.getAttempts(), gr.getDuration() / 1000.0);
                return userNameMaxLength;

            }
        }
        return userNameMaxLength;
    }
    public static String askYesOrNo() {
        String answer;
        do {
            answer = scanner.next();
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("You can enter only 'y' or 'n'!");
            } else {
                return answer;
            }
        }   while (true);
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
}
