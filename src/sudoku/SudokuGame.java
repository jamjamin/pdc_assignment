
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
    
    /**
     * 
     * Method to log user into the game.
     * 
     * @param un - username.
     * @param pw - password.
     */
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
    
    /**
     * 
     * Method to register user into the game.
     * 
     * @param un
     * @param pw 
     */
    public void registerUser(String un, String pw) {
        this.game_gen = new GridGenerator();
        this.username = un;

        this.data = this.sdb.registerUser(un, pw);
        if (data.newUser) {
            System.out.println("A new user has been summoned");   
            data.user_grid = game_gen.generateGrid();
            this.saveGrid(data.user_grid);
        }
        this.setChanged();
        this.notifyObservers(this.data);
    }
    
    /**
     * 
     * Method to logout user.
     * 
     */
    public void logout() {
        this.data.logoutFlag = true;
        this.setChanged();
        this.notifyObservers(this.data);
    }
    
    /**
     * 
     * Creates a new grid.
     * 
     */
    public void newGrid() {
        this.data.newGrid = true;
        this.game_gen = new GridGenerator();
        this.data.user_grid = game_gen.generateGrid();
        this.setChanged();
        this.notifyObservers(this.data);
        this.data.newGrid = false;
    }
    
    /**
     * 
     * Saves the user's grid into a file.
     * 
     */
    public void saveGrid(Grid grid) {
        data.user_grid = grid;
        sfm = new SudokuFileManager(data);
        sfm.saveGrid(data.filePath);
    }
    
    /**
     * 
     * Loads the user's grid from a file.
     * 
     */
    public void loadGrid () {
        sfm = new SudokuFileManager(data);
        sdb.loadFilePath(data.save_id, data);
        data.user_grid = sfm.loadGrid(data.filePath);
    }
    
    /**
     * 
     * Method to check if the grid is completed or not.
     * 
     */
    public boolean checkGrid(Grid grid) {
        game_grid = grid;
        grid_check = new GridChecker(game_grid);
        
        if (grid_check.solveGrid()) {
            return true;
        }
        else {
            return false;
        }
    }
}
