package sudoku;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SudokuDB {
    private final SudokuDBManager sdbm;
    private final Connection conn;
    private Statement statement;
    
    public SudokuDB() {
        sdbm = new SudokuDBManager();
        conn = sdbm.getConnection();
    }
    
    public void dbsetup() {
        try {
            this.statement = conn.createStatement();
            String tableName = "Users";
            if (!checkTableExisting(tableName)) {
                statement.executeUpdate("CREATE TABLE " + tableName 
                        + " (userid INT, username VARCHAR(12), password VARCHAR(12))");
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void testdb() {
        try {
            this.statement = conn.createStatement();
            if (checkTableExisting("Users")) {
                this.statement.addBatch("INSERT INTO Users VALUES (1, 'testuser1', 'user1pass')");
                this.statement.executeBatch();
                System.out.println("Data inserted");
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("TLDR: Did not work :(");
        }
    }
    
    public SudokuData loginUser(String un, String pw) {
        SudokuData data = new SudokuData();
        try {
            this.statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userid, username, password FROM USERS "
                                                + "where username = '" + un + "'");
            if (rs.next()) {
                System.out.println("User exist");
                String pass = rs.getString("password");
                if (pw.compareTo(pass) == 0) {
                    data.loginFlag = true;
                }
                else {
                    data.loginFlag = false;
                }
            } else {
                System.out.println("No user exist");
                data.loginFlag = false;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SudokuDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public SudokuData registerUser(String un, String pw) {
        SudokuData data = new SudokuData();
        int numRecords = getNumRecords();
        try {
            this.statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userid, username, password FROM Users "
                                                + "where username = '" + un + "'");
            if (rs.next()) {
                System.out.println("User exist");
                data.newUser = false;
            } 
            else {
                System.out.println("No user exist"); 
                this.addUserRecord(un, pw, numRecords);
                System.out.println("User registered");
                data.newUser = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SudokuDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public void addUserRecord(String un, String pw, int numRecords) {
        int user_id = numRecords + 1;
        try {
            this.statement = conn.createStatement();
            
            this.statement.addBatch("INSERT INTO Users VALUES (" + user_id  + ",'" + un + "', '" + pw + "')");
            this.statement.executeBatch();
            System.out.println("Data inserted");
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public int getNumRecords() {
        int numRecords = 0;
        try {
            this.statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM USERS");
            while (rs.next()) {
                numRecords = rs.getInt("1");
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return numRecords;
    }
    
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement=null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  is there");
                    flag = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
        }
        return flag;
    }
    
    
    public static void main(String[] args) {
        SudokuDB test = new SudokuDB();
        
        test.dbsetup();
        //test.testdb();
        System.out.println("Num of records " + test.getNumRecords());
    }
}  
