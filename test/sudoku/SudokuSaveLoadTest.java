package sudoku;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuSaveLoadTest {
    SudokuGame testGame;
    SudokuFileManager sfm;
       
    @Test
    public void registerUserSaveTest() {
        testGame = new SudokuGame();
        
        testGame.registerUser("regSave", "test");
        sfm = new SudokuFileManager(testGame.data);
        
        assertNotNull(testGame.sfm.loadGrid(testGame.data.filePath));
    }

    @After
    public void deleteRegisterSave() {
        testGame.data.filePath = "./resources/saves/regSave.ser";
        sfm = new SudokuFileManager(testGame.data);
        
        sfm.deleteSave(testGame.data.filePath);
        testGame.sdb.deleteSaveRecord("regSave");
        testGame.sdb.deleteUserRecord("regSave");
    }    
    
    @Test
    public void userLoadTest() {
        testGame = new SudokuGame();
        
        testGame.loginUser("testuser1", "user1pass");
        
        sfm = new SudokuFileManager(testGame.data);
        
        assertNotNull(testGame.data.user_grid);
    }
    
    
}
