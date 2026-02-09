package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.Space;

public class Main {
    static Pattern commandPattern = Pattern.compile("(flag|flip) (\\d+) (\\d+)");
    static boolean playing = true;

    static final String INPUT_PROMPT = "flag|flip> ";

    public static void main(String[] args) {
        clearConsole();
        Minesweeper minesweeper = new Minesweeper(10, 10, 3);

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

    public static String renderGrid(Minesweeper minesweeper, boolean showMine) {
        StringBuilder builder = new StringBuilder();

        int leftMine = minesweeper.getMineCount() - minesweeper.getFlagCount();
        builder.append("Mine Left: " + leftMine + "\n");

        Space[][] grid = minesweeper.getGrid();

        // Grid X Mark
        builder.append("   |");
        for (int i = 0; i < grid[0].length; i++)
            builder.append(String.format("%2d", i));
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
                builder.append(space.toString() + (showMine ? (space.isMine() ? "<" : " ") : " "));
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    public static void clearConsole() {
        IO.print("\033[H\033[2J");
    }
}
