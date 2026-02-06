package minesweeper;

import java.util.Random;

public class Minesweeper {
    public static final int FLIP_SUCCESS = 0;
    public static final int FLIP_BOMB = 1;
    public static final int FLIP_WIN = 2;
    public static final int FLIP_ALREADY_FLIPPED = 3;
    public static final int FLIP_FLAGGED = 4;

    Space[][] grid;
    boolean started;

    int width;
    int height;
    int numberOfMine;

    int clickLeft;

    public Minesweeper(int width, int height, int numberOfMine) {
        if (numberOfMine > width * height - 1)
            throw new IllegalArgumentException("Number of Mines should be less than Grid Size - 1: " + numberOfMine);
        started = false;
        this.width = width;
        this.height = height;
        this.numberOfMine = numberOfMine;
        this.clickLeft = width * height - numberOfMine - 1;

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
        started = true;
    }

    public int getSurroundingMineCount(int x, int y) {
        int[] count = { 0 };

        runWithBlockAround(x, y, (ix, iy, block) -> {
            count[0] += block.isMine() ? 1 : 0;
            return;
        });

        return count[0];
    }

    public Space[][] getGrid() {
        return grid;
    }

    public int flip(int x, int y) {
        if (!started)
            generateGrid(x, y);
        if (grid[y][x].isMine())
            return FLIP_BOMB;

        return flipIgnoreBomb(x, y);
    }

    private int flipIgnoreBomb(int x, int y) {
        Space space = grid[y][x];
        if (space.isFlipped())
            return FLIP_ALREADY_FLIPPED;
        if (space.isFlagged())
            return FLIP_FLAGGED;
        int minesAround = getSurroundingMineCount(x, y);
        space.flip(minesAround);

        if (minesAround == 0)
            runWithBlockAround(x, y, (ix, iy, block) -> flipIgnoreBomb(ix, iy));

        this.clickLeft--;
        if (clickLeft < 1)
            return FLIP_WIN;
        return FLIP_SUCCESS;
    }

    private void runWithBlockAround(int x, int y, BlockAction action) {
        boolean hasLeft = x > 0;
        boolean hasRight = x < grid[0].length - 1;
        boolean hasTop = y > 0;
        boolean hasBottom = y < grid.length - 1;

        for (int ix = (hasLeft ? -1 : 0); ix < (hasRight ? 2 : 1); ix++)
            for (int iy = (hasTop ? -1 : 0); iy < (hasBottom ? 2 : 1); iy++)
                if (!(x + ix == x && y + iy == y))
                    action.run(x + ix, y + iy, grid[y + iy][x + ix]);
    }

    private interface BlockAction {
        void run(int x, int y, Space space);
    }
}
