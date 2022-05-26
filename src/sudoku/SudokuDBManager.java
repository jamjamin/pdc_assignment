package sudoku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SudokuDBManager {
    
    private static final String USER_NAME = "pdc"; // db username
    private static final String PASSWORD = "pdc"; // db password
    private static final String URL = "jdbc:derby:SudokuDB; create=true";  //url of the DB host
    
    Connection conn;
    
    public SudokuDBManager () {
        establishConnection();
    }
    
    public Connection getConnection() {
        return this.conn;
    }
    
    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                System.out.println(URL + " Get Connected Successfully ....");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        SudokuDBManager dbManager = new SudokuDBManager();
        System.out.println(dbManager.getConnection());
    }
}
