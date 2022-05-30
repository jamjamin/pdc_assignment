package sudoku;

abstract class GridUtilities implements Row, Column, Box {

    public boolean checkRow(Cell[] row, int num) {
        for (int i = 0; i < 9; i++) {
            if (row[i].getNum() == num) {
                return false;
            }
        }
        return true;
    }

    public boolean checkColumn(Cell[] column, int num) {
        for (int i = 0; i < 9; i++) {
            if (column[i].getNum() == num){
                return false;
            }
        }
        return true;
    }

    public boolean checkBox(Cell[] box, int num) {
        for (int i = 0; i < 9; i++) {
            if (box[i].getNum() == num){
                return false;
            }
        }
        return true;
    }

    public Cell[] getRow(Grid grid, int y) {
        return grid.getGrid()[y];
    }

    public Cell[] getColumn(Grid grid, int x) {
        Cell[] column_cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            column_cells[i] = grid.getGrid()[i][x];
        }
        return column_cells;
    }

    public Cell[] getBox(Grid grid, int x, int y) {
        Cell[] box_cells = new Cell[9];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                box_cells[index] = grid.getGrid()[x+i][y+j];
                index += 1;
            }
        }
        return box_cells;
    }

}
