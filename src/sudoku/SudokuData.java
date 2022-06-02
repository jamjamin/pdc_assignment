package sudoku;

public class SudokuData {
    
    // User Login + Registeration Flags and Data
    boolean loginFlag = false;
    boolean registerFlag = false;
    boolean logoutFlag = false;
    boolean newUser = false;
    int save_id;
    
    // Grid Data + Game Flags
    boolean newGrid = false;
    String filePath = "";
    Grid user_grid;
    
}
