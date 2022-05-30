package sudoku;

interface Box {
    boolean checkBox(Cell[] box, int num);
    Cell[] getBox(Grid grid, int x, int y);
}
