package minesweeper;

import java.util.Random;

public class RandomLevel extends Minesweeper {
    public static final int RANDOM_MIN_WIDTH = 1;
    public static final int RANDOM_MAX_WIDTH = 30;

    public static final int RANDOM_MIN_HEIGHT = 1;
    public static final int RANDOM_MAX_HEIGHT = 16;

    public RandomLevel() {
        Random random = new Random();
        int randWidth = random.nextInt(RANDOM_MIN_WIDTH, RANDOM_MAX_WIDTH + 1);
        int randHeight = random.nextInt(RANDOM_MIN_HEIGHT, RANDOM_MAX_HEIGHT + 1);
        int randMineCount = random.nextInt(0, randWidth * randHeight - 1);

        super(randWidth, randHeight, randMineCount);
    }
}
