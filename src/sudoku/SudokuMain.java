/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

public class SudokuMain {

    public static void main(String[] args) {
        SudokuGUI gui = new SudokuGUI(); // View
        SudokuGame game = new SudokuGame(); // Model
        SudokuController controller = new SudokuController(gui, game); // Controller
        game.addObserver(gui);
    }
    
}
