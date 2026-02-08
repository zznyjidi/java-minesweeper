package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.Space;

public class Main {
    static Pattern commandPattern = Pattern.compile("(flag|flip) (\\d+) (\\d+)");
    static boolean playing = true;

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper(10, 10, 3);

        while (playing) {
            printGrid(minesweeper, false);

            String command = IO.readln("flag|flip> ");
            Matcher matcher = commandPattern.matcher(command);

            if (!matcher.find()) {
                IO.println("INVALID COMMAND. ");
                continue;
            }
            String cmd = matcher.group(1);
            int x = Integer.parseInt(matcher.group(2));
            int y = Integer.parseInt(matcher.group(3));

            switch (cmd) {
                case "flag" -> minesweeper.flag(x, y);
                case "flip" -> {
                    switch (minesweeper.flip(x, y)) {
                        case Minesweeper.FLIP_BOMB -> {
                            printGrid(minesweeper, true);
                            IO.println("YOU LOSE! ");
                            playing = false;
                        }
                        case Minesweeper.FLIP_WIN -> {
                            printGrid(minesweeper, true);
                            IO.println("YOU WIN! ");
                            playing = false;
                        }
                        case Minesweeper.FLIP_ALREADY_FLIPPED -> {
                            IO.println("BLOCK ALREADY FLIPPED. ");
                        }
                        case Minesweeper.FLIP_FLAGGED -> {
                            IO.println("BLOCK FLAGGED. ");
                        }
                    }
                }
            }
        }
    }

    public static void printGrid(Minesweeper minesweeper, boolean showMine) {
        Space[][] grid = minesweeper.getGrid();

        // Grid X Mark
        IO.print("   |");
        for (int i = 0; i < grid[0].length; i++)
            IO.print(String.format("%2d", i));
        IO.println();

        // Line
        IO.println("---+-" + "--".repeat(grid[0].length));

        // Grid
        for (int iy = 0; iy < grid.length; iy++) {
            // Grid Y Mark
            IO.print(String.format("%2d | ", iy));

            // Grid Row
            for (int ix = 0; ix < grid[0].length; ix++) {
                Space space = grid[iy][ix];
                IO.print(space.toString() + (showMine ? (space.isMine() ? "<" : " ") : " "));
            }
            IO.println();
        }
    }
}
