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
                    this.loadUserGrid(user_id, data);
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
                this.loadUserGrid(numRecords + 1, data);
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
    
    public void loadUserGrid(int user_id, SudokuData data) {
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

    public static void main(String[] args) {
        SudokuDB test = new SudokuDB();

        test.dbsetup();
        //test.testdb();
        System.out.println("Num of records " + test.getNumUserRecords());
    }
}
