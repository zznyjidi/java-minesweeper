package minesweeper;

public class Minesweeper {
    Space[][] grid;
    boolean started;

    public Minesweeper(int width, int height, int numberOfMine) {
        if (numberOfMine > width * height - 1)
            throw new IllegalArgumentException("Number of Mines should be less than Grid Size - 1: " + numberOfMine);
        started = false;
        grid = new Space[height][width];
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
}
