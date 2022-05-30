package sudoku;

public class Position {

    private final int x;
    private final int y;

    /**
     *
     * Constructor for cell position reference
     *
     * @param x position of cell
     * @param y position of cell
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * Get method for cell's X position
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * Get method for cell's Y position.
     *
     * @return
     */
    public int getY() {
        return y;
    }
}

