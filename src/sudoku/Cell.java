package sudoku;

import java.io.Serializable;

public class Cell  implements Serializable {

    private int num;
    private boolean editable;

    /**
     * Constructor for a cell inside a grid.
     *
     * @param num stored inside the cell
     */
    public Cell (int num) {
        this.num = num;
        this.editable = false;
    }

    /**
     * Gets the number inside the cell
     *
     * @return number inside cell.
     */
    public int getNum() {
        return num;
    }

    /**
     * Sets the number inside the cell
     *
     * @param num to set this cell's number.
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     *
     * Method for checking whether the cell is modifiable
     *
     * @return cell is editable.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     *
     * Changes the state in which the cell could be modified.
     *
     * @param b
     */
    public void setEditable(boolean b) {
        this.editable = b;
    }
}
