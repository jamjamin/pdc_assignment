/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

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
                this.statement.addBatch("INSERT INTO Users VALUES (1, 'doodyah', 'yahdood')");
                this.statement.executeBatch();
                System.out.println("Data inserted");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("TLDR: Did not work :(");
        }
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
        test.testdb();
    }
}  
