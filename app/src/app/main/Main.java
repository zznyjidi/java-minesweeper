package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import color.ANSIColors;
import minesweeper.BeginnerLevel;
import minesweeper.ExpertLevel;
import minesweeper.IntermediateLevel;
import minesweeper.Minesweeper;
import minesweeper.RandomLevel;
import minesweeper.Space;

public class Main {
    static Pattern commandPattern = Pattern.compile("(flag|flip) (\\d+) (\\d+)");
    static Pattern levelSelectPattern = Pattern
            .compile("(b(?:eginner)?|i(?:ntermediate)?|e(?:xpert)?|c(?:ustom)?|r(?:andom)?)(?: (\\d*) (\\d*) (\\d*))?");
    static boolean playing = true;
    static boolean running = true;

    static final String INPUT_PROMPT = "flag|flip> ";

    public static void main(String[] args) {

        while (running) {
            String levelSelect = IO.readln("beginner | intermediate | expert | custom | random > ");
            Matcher levelMatcher = levelSelectPattern.matcher(levelSelect);
            if (!levelMatcher.find()) {
                System.err.println("INVALID COMMAND. ");
                continue;
            }
            Minesweeper minesweeper = null;
            try {
                minesweeper = switch (levelMatcher.group(1).charAt(0)) {
                    case 'b' -> new BeginnerLevel();
                    case 'i' -> new IntermediateLevel();
                    case 'e' -> new ExpertLevel();
                    case 'c' -> {
                        int width = Integer.parseInt(levelMatcher.group(2));
                        int height = Integer.parseInt(levelMatcher.group(3));
                        int mineCount = Integer.parseInt(levelMatcher.group(4));
                        yield new Minesweeper(width, height, mineCount);
                    }
                    case 'r' -> new RandomLevel();
                    default -> throw new IllegalArgumentException();
                };
            } catch (NumberFormatException e) {
                System.err.println("INVALID COMMAND FORMAT. ");
                System.err.println("custom <width> <height> <numberOfMine>");
                continue;
            }
            clearConsole();

            String command = "";
            while (playing) {
                IO.print(renderGrid(minesweeper, false));
                command = IO.readln(INPUT_PROMPT);

                clearConsole();
                IO.println(INPUT_PROMPT + command);

                Matcher matcher = commandPattern.matcher(command);

                if (!matcher.find()) {
                    System.err.println("INVALID COMMAND. ");
                    continue;
                }

                String cmd = matcher.group(1);
                int x = Integer.parseInt(matcher.group(2));
                int y = Integer.parseInt(matcher.group(3));
                if (x >= minesweeper.getWidth() || y >= minesweeper.getHeight()) {
                    System.err.println("BLOCK IS OUTSIDE THE GRID. ");
                    continue;
                }

                switch (cmd) {
                    case "flag" -> minesweeper.flag(x, y);
                    case "flip" -> {
                        switch (minesweeper.flip(x, y)) {
                            case Minesweeper.FLIP_BOMB -> {
                                IO.print(renderGrid(minesweeper, true));
                                IO.println("YOU LOSE! ");
                                playing = false;
                            }
                            case Minesweeper.FLIP_WIN -> {
                                IO.print(renderGrid(minesweeper, true));
                                IO.println("YOU WIN! ");
                                playing = false;
                            }
                            case Minesweeper.FLIP_ALREADY_FLIPPED -> {
                                System.err.println("BLOCK ALREADY FLIPPED. ");
                            }
                            case Minesweeper.FLIP_FLAGGED -> {
                                System.err.println("BLOCK FLAGGED. ");
                            }
                        }
                    }
                }
            }
        }
    }

    public static String renderGrid(Minesweeper minesweeper, boolean showMine) {
        StringBuilder builder = new StringBuilder();

        int leftMine = minesweeper.getMineCount() - minesweeper.getFlagCount();
        builder.append("Mine Left: " + leftMine + "\n");

        Space[][] grid = minesweeper.getGrid();

        // Grid X Mark
        builder.append("   |");
        for (int i = 0; i < grid[0].length; i++) {
            builder.append(i % 2 == 0
                    ? (ANSIColors.GREY_DARK_BACKGROUND)
                    : (ANSIColors.GREY_LIGHT_BACKGROUND));
            builder.append(String.format("%2d", i));
            builder.append(ANSIColors.RESET);
        }
        builder.append("\n");

        // Line
        builder.append("---+-" + "--".repeat(grid[0].length) + "\n");

        // Grid
        for (int iy = 0; iy < grid.length; iy++) {
            // Grid Y Mark
            builder.append(String.format("%2d | ", iy));

            // Grid Row
            for (int ix = 0; ix < grid[0].length; ix++) {
                Space space = grid[iy][ix];
                builder.append((showMine ? (space.isMine() ? ANSIColors.PURPLE_BACKGROUND : "") : ""));
                builder.append(space.toStringWithColor());
                builder.append((showMine ? (space.isMine() ? "<" : " ") : " "));
                builder.append(ANSIColors.RESET);
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    public static void clearConsole() {
        IO.print("\033[H\033[2J");
    }
}
