package minesweeper;

import java.util.Random;

public class Minesweeper {
    Space[][] grid;
    boolean started;

    int width;
    int height;
    int numberOfMine;

    public Minesweeper(int width, int height, int numberOfMine) {
        if (numberOfMine > width * height - 1)
            throw new IllegalArgumentException("Number of Mines should be less than Grid Size - 1: " + numberOfMine);
        started = false;
        this.width = width;
        this.height = height;
        this.numberOfMine = numberOfMine;

        grid = new Space[height][width];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                grid[y][x] = new Space();
            }
        }
    }

    public void generateGrid(int initX, int initY) {
        int generatedMine = 0;
        Random random = new Random();

        while (generatedMine < numberOfMine) {
            int randX = random.nextInt(width);
            int randY = random.nextInt(height);
            if (!(randX == initX && randY == initY) && !grid[randY][randX].isMine()) {
                grid[randY][randX].setMine();
                generatedMine++;
            }
        }
    }

    public int getSurroundingMineCount(int x, int y) {
        int count = 0;
        boolean hasLeft = x > 0;
        boolean hasRight = x < grid[0].length - 1;
        boolean hasTop = y > 0;
        boolean hasBottom = y < grid.length - 1;

        for (int ix = (hasLeft ? -1 : 0); ix < (hasRight ? 2 : 1); ix++)
            for (int iy = (hasTop ? -1 : 0); iy < (hasBottom ? 2 : 1); iy++)
                count += grid[y + iy][x + ix].isMine() ? 1 : 0;

        return count;
    }

    public Space[][] getGrid() {
        return grid;
    }
}
