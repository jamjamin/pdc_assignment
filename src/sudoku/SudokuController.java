package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class SudokuController extends WindowAdapter implements ActionListener {
    
    public SudokuGUI gui;
    public SudokuGame game;
    
    public SudokuController(SudokuGUI gui, SudokuGame game) {
        this.gui = gui;
        this.game = game;
        this.gui.addListeners(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane helpBox;
        String command = e.getActionCommand();
        String username = this.gui.inputUserName.getText();
        char[] pass_string = this.gui.inputPassword.getPassword();
        String password = String.copyValueOf(pass_string);       
        String[] yn_options = {"Yes", "No"};
        String message;
        int option;  
        switch (command) {
            // Login Cases
            case "Login":
                this.game.loginUser(username, password);
                break;
            case "Register":
                this.game.registerUser(username, password);
                break;    
            case "Exit":
                System.exit(0);
                break;
            // Main Game Cases
            case "Check":
                if(!this.game.checkGrid(gui.getSudokuGrid())) {
                    message = "Grid is not completed or has errors";
                    JOptionPane.showMessageDialog(null, message, "Grid Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    message = "You have completed the grid! Would you like to start a new game?";
                    option = optionDialogBox(message, "Grid Complete!", yn_options); 
                    if (option == 0) {
                        System.out.println("New Grid");
                        this.gui.resetGrid();
                        this.game.newGrid();
                    } else {
                        System.out.println("Go to Login");
                        this.game.logout();
                    }
                }
                break;
            case "Save & Exit":
                message = "Are you sure? Progress will be saved";
                option = optionDialogBox(message, "Save & Exit", yn_options);  
                if (option == 0) {
                    System.out.println("Exit");  
                    this.game.saveGrid(this.gui.getSudokuGrid());
                    System.exit(0);
                }
                break;
            case "Save & Logout":
                message = "Are you sure? Progress will be saved";
                option =  optionDialogBox(message, "Save & Logout", yn_options); 
                if (option == 0) {
                    System.out.println("Logout");
                    this.game.saveGrid(this.gui.getSudokuGrid());
                    this.game.logout();
                }
                break;
            case "New Game":
                message = "Are you sure?";
                option = optionDialogBox(message, "New Game", yn_options);  
                if (option == 0) {
                    System.out.println("New grid");
                    this.gui.resetGrid();
                    this.game.newGrid();
                }
                break;
            case "Help":
                message = "Your goal is to fill the 9x9 Grid with numbers 1 - 9 with the following conditions:\n"
                        + "  -  Vertical and Horizontal Rows can't have repeat numbers\n"
                        + "  -  3x3 boxes can't have repeat numbers as well\n"
                        + "How to edit grid:\n"
                        + "  -  Left Click a square on the grid then select a number on the top right\n"
                        + "  -  Squares already filled with a number will be red when selected and cannot"
                        + " be changed.";
                System.out.println("Help Box");
                helpBox = new JOptionPane();
                helpBox.setMessage(message);
                helpBox.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                JDialog dialog = helpBox.createDialog(null, "Help Box");
                dialog.setVisible(true);
                break;
            default:
                break;
        }
    }
    
    public int optionDialogBox(String message, String boxTitle, String[] options) {
        int option;
        
        option = JOptionPane.showOptionDialog(null, message, boxTitle, JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
        
        return option;
    }
}
