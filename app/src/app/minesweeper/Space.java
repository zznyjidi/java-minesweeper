package minesweeper;

public class Space {
    boolean mine = false;
    boolean flagged = false;
    boolean flipped = false;
    int minesAround;

    public void setMine() {
        this.mine = true;
    }

    public boolean isMine() {
        return mine;
    }

    public void setFlag(boolean flag) {
        this.flagged = flag;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void flip(int minesAround) {
        this.flipped = true;
        this.minesAround = minesAround;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public String toString() {
        if (flipped)
            return String.valueOf(minesAround);
        if (flagged)
            return "F";
        return ".";
    }

    public static final String COLOR_ANSI_RESET = "\u001B[0m";
    public static final String COLOR_ANSI_BLACK = "\u001B[30m";
    public static final String COLOR_ANSI_RED = "\u001B[31m";
    public static final String COLOR_ANSI_BLUE = "\u001B[34m";
    public static final String COLOR_ANSI_GREEN = "\u001B[32m";
    public static final String COLOR_ANSI_CYAN = "\u001B[36m";

    public String toStringWithColor() {
        if (flipped)
            return switch (minesAround) {
                case 1 -> COLOR_ANSI_BLUE;
                case 2 -> COLOR_ANSI_GREEN;
                case 3 -> COLOR_ANSI_RED;
                case 4 -> COLOR_ANSI_BLUE;
                case 5 -> COLOR_ANSI_RED;
                case 6 -> COLOR_ANSI_CYAN;
                case 7 -> COLOR_ANSI_BLACK;
                default -> "";
            } + String.valueOf(minesAround) + COLOR_ANSI_RESET;
        if (flagged)
            return COLOR_ANSI_RED + "F" + COLOR_ANSI_RESET;
        return ".";
    }
}
