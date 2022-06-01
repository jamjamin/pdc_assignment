package sudoku;

import org.junit.Test;
import static org.junit.Assert.*;


public class SudokuGridCheckerTest {
    SudokuGame testGame;
    
    @Test
    public void GridWithErrorsTest() {
        testGame = new SudokuGame();
        testGame.data = testGame.sdb.loginUser("testuser2", "user2pass");
        testGame.data.filePath = "./resources/saves/testuser2.ser";
        testGame.loadGrid();
        
        assertFalse(testGame.checkGrid(testGame.data.user_grid));
    }        
    
    @Test
    public void IncompleteGridTest() {
        testGame = new SudokuGame();
        testGame.data = testGame.sdb.loginUser("testuser3", "user3pass");
        testGame.data.filePath = "./resources/saves/testuser3.ser";
        testGame.loadGrid();
        
        assertFalse(testGame.checkGrid(testGame.data.user_grid));
    }
    
    @Test
    public void CompleteGridTest () {
        testGame = new SudokuGame();
        testGame.data = testGame.sdb.loginUser("testuser4", "user4pass");
        testGame.data.filePath = "./resources/saves/testuser4.ser";
        testGame.loadGrid();
        
        assertTrue(testGame.checkGrid(testGame.data.user_grid));
    }
}
