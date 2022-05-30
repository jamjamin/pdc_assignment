package sudoku;

import java.io.Serializable;

public class Grid implements Serializable {
    private final Cell[][] cells;

    /**
     *
     * Initializes a 2D array of 81 total cells when grid object is created.
     *
     */
    public Grid() {
        this.cells = new Cell[9][9];
    }

    /**
     *
     * Get a specific cell
     *
     * @param x position of cell
     * @param y position of cell
     * @return cell object.
     */
    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    /**
     *
     * Does the same thing above but uses Position object.
     *
     * @param pos Cell's position
     *
     */
    public Cell getCell(Position pos) {
        int x = pos.getX() - 1;
        int y = pos.getY() - 1;

        return cells[y][x];
    }

    /**
     *
     * Sets cell's number at a specific position.
     *
     * @param pos
     * @param num
     */
    public void setCell(Position pos, int num) {
        int x = pos.getX() - 1;
        int y = pos.getY() - 1;

        cells[y][x].setNum(num);
    }


    /**
     *
     * Gets all the cells inside the grid.
     *
     * @return all cells.
     */
    public Cell[][] getGrid() {
        return cells;
    }

    /**
     *
     * Method for outputting the grid into the screen.
     *
     * @return
     */
    public String toString() {
        String str = "";
        char LETTER_OFFSET = 'A';

        System.out.println("   1  2  3  4  5  6  7  8  9");
        for (int i = 0; i < 9; i++){
            str += (char)(LETTER_OFFSET + i) + " ";
            for (int j = 0; j < 9; j++) {
                if (this.getCell(j, i) != null) {
                    if (this.getCell(j, i).getNum() != 0) {
                        str += "[" + this.getCell(j, i).getNum() + "]";
                    }
                    else {
                        str += "[" + " " + "]";
                    }

                }
            }
            str += "\n";
        }

        return str;
    }
    
}
