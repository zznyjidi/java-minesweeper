package minesweeper;

import color.ANSIColors;

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

    public String toStringWithColor() {
        if (flipped)
            return switch (minesAround) {
                case 1 -> ANSIColors.BLUE;
                case 2 -> ANSIColors.GREEN;
                case 3 -> ANSIColors.RED;
                case 4 -> ANSIColors.BLUE;
                case 5 -> ANSIColors.RED;
                case 6 -> ANSIColors.CYAN;
                case 7 -> ANSIColors.BLACK;
                default -> "";
            } + String.valueOf(minesAround);
        if (flagged)
            return ANSIColors.RED + "F";
        return ".";
    }
}
