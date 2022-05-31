package sudoku;

import java.util.Random;

public class GridGenerator extends GridUtilities {

    private final Grid new_grid;

    /**
     *
     * Constructor for generating numbers inside grid.
     *
     */
    public GridGenerator() {
        this.new_grid = new Grid();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                new_grid.getGrid()[i][j] = new Cell(0);
            }
        }
    }

    /**
     *
     * Method for generating grid. First generates numbers in 3x3 boxes diagonally then fills the rest.
     * Empty random cells for editing.
     *
     * @return generated grid.
     */
    public Grid generateGrid() {

        int gen_num; // Number to be generated

        for (int h = 0; h < 9; h += 3) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    gen_num = generateNum();
                    while (!checkBox(getBox(this.new_grid, h, h), gen_num)) {
                        gen_num = generateNum();
                    }
                    new_grid.getGrid()[i+h][j+h] = new Cell(gen_num);
                }
            }
        }

        fillRemaining(0, 3);
        removeDigits();

        return new_grid;
    }

    /**
     *
     * Fills the remaining empty cells.
     *
     */
    private boolean fillRemaining(int i, int j) {
        if (j >= 9 && i < 9 - 1)
        {
            i = i + 1;
            j = 0;
        }
        if (i >= 9 && j >= 9)
            return true;

        if (i < 3)
        {
            if (j < 3)
                j = 3;
        }
        else if (i < 9 - 3)
        {
            if (j == (i / 3) * 3)
                j = j + 3;
        }
        else
        {
            if (j == 9 - 3)
            {
                i = i + 1;
                j = 0;
                if (i >= 9)
                    return true;
            }
        }

        for (int num = 1; num <= 9; num++)
        {
            if (checkRow(getRow(this.new_grid, i), num) &&
                    checkColumn(getColumn(this.new_grid, j), num)  &&
                    checkBox(getBox(this.new_grid,i - i % 3, j - j % 3), num))
            {
                this.new_grid.getGrid()[i][j].setNum(num);
                if (fillRemaining(i, j+1))
                    return true;

                this.new_grid.getGrid()[i][j].setNum(0);
            }
        }
        return false;
    }

    /**
     *
     * Removes a number from a cell and makes it editable.
     *
     */
    private void removeDigits () {
        Random rand = new Random();
        boolean remove;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                 remove = rand.nextBoolean();
                 if (remove) {
                     this.new_grid.getGrid()[i][j].setNum(0);
                     this.new_grid.getGrid()[i][j].setEditable(true);
                 }
            }
        }
    }

    /**
     *
     * Method for number generation.
     *
     * @return a random number from 1 - 9.
     */
    private int generateNum() {
        Random rand = new Random();
        int num;
        num = rand.nextInt(9) + 1;
        return num;
    }

}
