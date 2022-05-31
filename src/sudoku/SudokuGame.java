
package sudoku;

import java.util.Observable;

public class SudokuGame extends Observable {
    public SudokuDB sdb;
    public SudokuFileManager sfm;
    public SudokuData data;
    public String username;
    
    public Grid game_grid;
    public GridGenerator game_gen;
    public GridChecker grid_check;
    
    public SudokuGame() {
        sdb = new SudokuDB();
        sdb.dbsetup();
    }
    
    public void loginUser(String un, String pw) {
        this.username = un;
        
        this.data = this.sdb.loginUser(un, pw);
        
        if (data.loginFlag) {
            System.out.println("Login Sucessful");
            this.loadGrid();
        }
        this.setChanged();
        this.notifyObservers(this.data);
    }
    
    public void registerUser(String un, String pw) {
        this.game_gen = new GridGenerator();
        this.username = un;

        this.data = this.sdb.registerUser(un, pw);
        System.out.println(data.filePath);
        if (data.newUser) {
            System.out.println("A new user has been summoned");   
            data.user_grid = game_gen.generateGrid();
        }
        this.setChanged();
        this.notifyObservers(this.data);
    }
    
    public void logout() {
        this.data.logoutFlag = true;
        this.setChanged();
        this.notifyObservers(this.data);
    }
    
    public void newGrid() {
        this.data.newGrid = true;
        this.game_gen = new GridGenerator();
        this.data.user_grid = game_gen.generateGrid();
        this.setChanged();
        this.notifyObservers(this.data);
        this.data.newGrid = false;
    }
    
    public void saveGrid(Grid grid) {
        data.user_grid = grid;
        sfm = new SudokuFileManager(data);
        sfm.saveGrid(data.filePath);
    }
    
    public void loadGrid () {
        sfm = new SudokuFileManager(data);
        data.user_grid = sfm.loadGrid(data.filePath);
    }
    
    public boolean checkGrid(Grid grid) {
        game_grid = grid;
        grid_check = new GridChecker(game_grid);
        
        if (grid_check.solveGrid()) {
            System.out.println("YAY");
            return true;
        }
        else {
            System.out.println("Nop");
            return false;
        }
    }
}
