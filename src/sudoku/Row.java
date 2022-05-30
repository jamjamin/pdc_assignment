package sudoku;

interface Row {
    boolean checkRow(Cell[] row, int num);
    Cell[] getRow(Grid grid, int y);
}
