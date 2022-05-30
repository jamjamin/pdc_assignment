
package sudoku;

import java.util.Observable;

public class SudokuGame extends Observable {
    public SudokuDB sdb;
    public SudokuGUI sgui;
    public SudokuData data;
    public String username;
    
    public Grid game_grid;
    public GridGenerator game_gen;
    public GridChecker grid_check;
    
    public SudokuGame() {
        sdb = new SudokuDB();
        sdb.dbsetup();
        game_gen = new GridGenerator();
    }
    
    public void loginUser(String un, String pw) {
        this.username = un;
        
        this.data = this.sdb.loginUser(un, pw);
        
        if (data.loginFlag) {
            System.out.println("Login Sucessful");
        }
        this.setChanged();
        this.notifyObservers(this.data);
    }
    
    public void registerUser(String un, String pw) {
        this.username = un;
        
        this.data = this.sdb.registerUser(un, pw);
        
        if (data.newUser) {
            System.out.println("A new user has been summoned");             
            data.user_grid = game_gen.generateGrid();
        }
        this.setChanged();
        this.notifyObservers(this.data);
    }
    
    public void checkGrid(Grid grid) {
        game_grid = grid;
        grid_check = new GridChecker(game_grid);
        
        if (grid_check.solveGrid()) {
            System.out.println("YAY");
        }
        else {
            System.out.println("Nop");
        }
    }
   
}
