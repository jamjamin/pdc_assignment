package sudoku;

public class GridChecker extends GridUtilities {
    private final Grid grid_check;

    /**
     *
     * Constructor for checking if grid is valid
     *
     * @param grid_check for validating grid.
     */
    public GridChecker(Grid grid_check) {
        this.grid_check = grid_check;
    }

    /**
     *
     * Method for verifying grid
     *
     * @return whether the grid is solved or not.
     */
    public boolean solveGrid() {
        int num_check;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    num_check = this.grid_check.getGrid()[i][j].getNum();
                    if (checkRow(getRow(this.grid_check, j), num_check) ||
                            checkColumn(getColumn(this.grid_check, i), num_check) ||
                            checkBox(getBox(this.grid_check, i - i % 3, j - j % 3), num_check) ||
                            num_check == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
