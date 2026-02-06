package main;

import minesweeper.Minesweeper;
import minesweeper.Space;

public class Main {
    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper(10, 10, 99);

        int initX = 0;
        int initY = 0;

        minesweeper.generateGrid(initX, initY);
        Space[][] grid = minesweeper.getGrid();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                IO.print((grid[y][x].isMine() ? "X" : "O") + (x == initX && y == initY ? "<" : " "));
            }
            IO.println();
        }
    }
}