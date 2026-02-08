package main;

import minesweeper.Minesweeper;
import minesweeper.Space;

public class Main {
    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper(10, 10, 3);

        int initX = 3;
        int initY = 5;

        minesweeper.flip(initX, initY);
        Space[][] grid = minesweeper.getGrid();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                IO.print((grid[y][x].isMine() ? "X" : "O") + (x == initX && y == initY ? "<" : " "));
            }
            IO.println();
        }

        while (true) {
            for (int iy = 0; iy < grid.length; iy++) {
                for (int ix = 0; ix < grid[0].length; ix++) {
                    IO.print(grid[iy][ix].toString() + " ");
                }
                IO.println();
            }

            int x = Integer.parseInt(IO.readln("x"));
            int y = Integer.parseInt(IO.readln("y"));

            IO.println(minesweeper.flip(x, y));
        }
    }
}