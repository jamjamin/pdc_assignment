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
    /**
     * 
     * Creates data base component and tables (if it does not exist).
     * 
     */
    public void dbsetup() {
        try {
            this.statement = conn.createStatement();
            if (!checkTableExisting("Users")) {
                statement.executeUpdate("CREATE TABLE Users"
                        + " (userid INT, username VARCHAR(15), password VARCHAR(15))");
            }
            if (!checkTableExisting("Saves")) {
                statement.executeUpdate("CREATE TABLE Saves"
                        + " (saveid INT, savepath VARCHAR(100))");
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * Method to grab user from database for login (so long as their password is correct).
     * 
     * @param un - username
     * @param pw - password
     * @return data - stores data required for the GUI.
     */
    public SudokuData loginUser(String un, String pw) {
        SudokuData data = new SudokuData();
        data.registerFlag = false;
        try {
            this.statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userid, username, password FROM USERS "
                    + "where username = '" + un + "'");
            if (rs.next()) {
                System.out.println("User exist");
                int user_id = rs.getInt("userid");
                String pass = rs.getString("password");
                if (pw.compareTo(pass) == 0) {
                    this.loadFilePath(user_id, data);
                    data.loginFlag = true;
                } else {
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
    /**
     * 
     * Registers user into data base if its a new user.
     * 
     * @param un - username
     * @param pw - password
     * @return data - Data required for the GUI.
     */
    public SudokuData registerUser(String un, String pw) {
        SudokuData data = new SudokuData();
        int numRecords = getNumUserRecords();
        try {
            this.statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userid, username, password FROM Users "
                    + "where username = '" + un + "'");
            if (rs.next()) {
                System.out.println("User exist");
                data.newUser = false;
                data.registerFlag = true;
            } else {
                System.out.println("No user exist");
                this.addUserRecord(un, pw, numRecords);
                this.addSaveRecord(un, numRecords);
                this.loadFilePath(numRecords + 1, data);
                System.out.println("User registered");
                data.newUser = true;
                data.registerFlag = true;
            }
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(SudokuDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    /**
     * 
     * Method to set the user's file path from database.
     * 
     * @param user_id - User's ID which will be used to grab save id.
     * @param data - Data instance parameter, to change the filePath inside.
     */
    public void loadFilePath(int user_id, SudokuData data) {
        String int_string = String.valueOf(user_id);
        try {
            this.statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT saveid, savepath FROM Saves " 
                    + "where saveid = " + int_string + "");
            if (rs.next()) {
                System.out.println("Save file exist");
                data.filePath = rs.getString("savepath");
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    /**
     * 
     * Adds a user into user table.
     * 
     * @param un - username
     * @param pw - password
     * @param numRecords - for the user id.
     */
    public void addUserRecord(String un, String pw, int numRecords) {
        int user_id = numRecords + 1;
        try {
            this.statement = conn.createStatement();

            this.statement.addBatch("INSERT INTO Users VALUES (" + user_id + ",'" + un + "', '" + pw + "')");
            this.statement.executeBatch();
            System.out.println("Data inserted");
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * Deletes a user record.
     * 
     * @param un - username to delete.
     */
    public void deleteUserRecord(String un) {
        try {
            this.statement = conn.createStatement();
            this.statement.executeUpdate("DELETE FROM Users WHERE username = '" + un + "'");
            this.statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * Adds a save record.
     * 
     * @param un - username, for grid file name.
     * @param numRecords - for save id. 
     */
    public void addSaveRecord(String un, int numRecords) {
        int save_id = numRecords + 1;
        try {
            this.statement = conn.createStatement();
            this.statement.addBatch("INSERT INTO SAVES VALUES (" + save_id + ",'./resources/saves/" + un + ".ser')");
            this.statement.executeBatch();
            this.statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * Deletes a save record.
     * 
     * @param un - user's save name to delete.
     */
    public void deleteSaveRecord(String un) {
        try {
            this.statement = conn.createStatement();
            this.statement.executeUpdate("DELETE FROM Saves WHERE savepath = './resources/saves/" + un + ".ser'");
            this.statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * Method to get number of records
     * 
     * @return number of records.
     */
    public int getNumUserRecords() {
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
    
    /**
     * 
     * Checks if a table exists.
     * 
     * @param newTableName to check if it exists
     * @return whether it exists or not.
     */
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
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
}
