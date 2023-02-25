package rockpaperscissors;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String name = usernamePrompt();
        int score = identifyScore(name);
        String[] options = optionsPrompt();

        System.out.println("Okay, let's start");
        while (true) {
            String input = scanner.next();
            if ("!exit".equals(input)) {
                System.out.println("Bye!");
                break;
            } else if ("!rating".equals(input)) {
                System.out.printf("Your rating: %d\n", score);
            } else if (Arrays.asList(options).contains(input)) {
                score = runGame(input, options, score);
            } else {
                System.out.println("Invalid input\n");
            }
        }
    }

    static String usernamePrompt() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.printf("Hello, %s\n", name);
        return name;
    }

    static int identifyScore(String name) {
        int score = 0;
        File file = new File("rating.txt");
//        File file = new File("Rock-Paper-Scissors\\task\\src\\rockpaperscissors\\rating.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("^" + name + ".*")) {
                    score = Integer.parseInt(line.split(" ")[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }

    static String[] optionsPrompt() {
        String optionsInput = scanner.nextLine();
        if (optionsInput.isBlank()) {
            optionsInput = "rock,paper,scissors";
        }

        return optionsInput.split(",");

    }

    static int runGame(String input, String[] options, int score) {
        //Problem:
        /* The user's list of options is rock,paper,scissors,lizard,spock. You want to know what options are weaker than lizard.
        By looking at the list spock,rock,paper,scissors you realize that spock and rock beat lizard. Paper and scissors are defeated by it.
        For spock, it'll be almost the same, but it'll get beaten by rock and paper, and prevail over scissors and lizard. */
        ArrayList<String> stronger = new ArrayList<>();
        int optionIndex = Arrays.asList(options).indexOf(input);
        int startingIndex = optionIndex != options.length - 1 ? ++optionIndex : 0;

        while (stronger.size() != options.length / 2) {
            if (startingIndex > options.length - 1) {
                startingIndex = 0;
            }
            stronger.add(options[startingIndex]);
            startingIndex++;
        }

        int optionsCount = options.length;
        int random = (int) Math.floor(Math.random() * optionsCount);
        String computerPick = options[random];

        if (input.equals(computerPick)) {
            score += draw(input);
        } else if (stronger.contains(computerPick)) {
            lose(computerPick);
        } else {
            score += win(computerPick);
        }

        return score;
    }

    static int draw(String input) {
        System.out.printf("There is a draw (%s)\n", input);
        return 50;
    }

    static int win(String computerPick) {
        System.out.printf("Well done. The computer chose %s and failed\n", computerPick);
        return 100;
    }

    static void lose(String computerPick) {
        System.out.printf("Sorry, but the computer chose %s\n", computerPick);
    }

}




