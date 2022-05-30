package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuController implements ActionListener {
    
    public SudokuGUI gui;
    public SudokuGame game;
    
    public SudokuController(SudokuGUI gui, SudokuGame game) {
        this.gui = gui;
        this.game = game;
        this.gui.addListeners(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String username = this.gui.inputUserName.getText();
        String password = this.gui.inputPassword.getText();
        switch (command) {
            case "Login":
                this.game.loginUser(username, password);
                break;
            case "Register":
                this.game.registerUser(username, password);
                break;
            default:
                break;
        }
    }
    
}
