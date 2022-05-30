package sudoku;

interface Column {
    boolean checkColumn(Cell[] column, int num);
    Cell[] getColumn(Grid grid, int x);
}
