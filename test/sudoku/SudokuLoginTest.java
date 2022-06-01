package sudoku;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jamjamin
 */
public class SudokuLoginTest {

    @Test
    public void testUser1LoginSuccess() {
        SudokuGame testGame = new SudokuGame();
        testGame.loginUser("testuser1", "user1pass");
        
        assertTrue(testGame.data.loginFlag);
    }
    
    @Test
    public void testUser2LoginSuccess() {
        SudokuGame testGame = new SudokuGame();
        testGame.loginUser("testuser2", "user2pass");
        
        assertTrue(testGame.data.loginFlag);
    }
    
    @Test
    public void testUser1LoginFail() {
        SudokuGame testGame = new SudokuGame();
        testGame.loginUser("testuser1", "somepass");
        
        assertFalse(testGame.data.loginFlag);
    }
    
    @Test
    public void testUser2LoginFail() {
        SudokuGame testGame = new SudokuGame();
        testGame.loginUser("testuser2", "user1pass");
        
        assertFalse(testGame.data.loginFlag);
    }
    
    @Test
    public void testUserNotExist() {
        SudokuGame testGame = new SudokuGame();
        testGame.loginUser("someuser", "somepass");
        
        assertFalse(testGame.data.loginFlag);
    }            
}
