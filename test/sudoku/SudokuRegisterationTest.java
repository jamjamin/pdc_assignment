package sudoku;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuRegisterationTest {
    SudokuGame testGame;
    
    @After
    public void deleteUsers () {
        testGame.sdb.deleteUserRecord("testuser5");
        testGame.sdb.deleteSaveRecord("testuser5");
        testGame.sdb.deleteUserRecord("abcdefghijklmno");
        testGame.sdb.deleteSaveRecord("abcdefghijklmno");
        testGame.sdb.deleteUserRecord("!@#$");
        testGame.sdb.deleteSaveRecord("!@#$");
        testGame.sdb.deleteUserRecord("[{()}]");
        testGame.sdb.deleteSaveRecord("[{()}]");
    }
    
    @Test
    public void testUserAlreadyExists() {
        testGame = new SudokuGame();
        testGame.data = testGame.sdb.registerUser("testuser1", "user1pass");
        
        assertFalse(testGame.data.newUser);
    }
    
    @Test
    public void registerUser4Test() {
        testGame = new SudokuGame();
        testGame.data = testGame.sdb.registerUser("testuser5", "user5pass");
        
        assertTrue(testGame.data.newUser);
    }
    
    @Test
    public void maxCharRegisteration() {
        testGame = new SudokuGame();
        testGame.data = testGame.sdb.registerUser("abcdefghijklmno", "abcdefghijklmno");
        
        assertTrue(testGame.data.newUser);
    }
    
    @Test
    public void registerWithUnexpectedInput1() {
        testGame = new SudokuGame();
        testGame.data = testGame.sdb.registerUser("!@#$", "%^&*");
        
        assertTrue(testGame.data.newUser);
    }
    
    @Test
    public void registerWithUnexpectedInput2() {
        testGame = new SudokuGame();
        testGame.data = testGame.sdb.registerUser("[{()}]", "brackets");
        
        assertTrue(testGame.data.newUser);
    }
}
