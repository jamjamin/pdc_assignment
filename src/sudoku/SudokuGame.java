/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sudoku;

import java.util.Observable;

public class SudokuGame extends Observable {
    public SudokuDB sdb;
    public SudokuGUI sgui;
    public SudokuData data;
    public String username;
    
    public SudokuGame() {
        sdb = new SudokuDB();
        sdb.dbsetup();
    }
    
    public void loginUser(String un, String pw) {
        this.username = un;
        
        this.data = this.sdb.loginUser(un, pw);
        
        if (data.loginFlag) {
            System.out.println("Login Sucessful");
        }
        this.setChanged();
        this.notifyObservers(this.data);
    }
    
    public void registerUser(String un, String pw) {
        this.username = un;
        
        this.data = this.sdb.registerUser(un, pw);
        
        
    }
   
}
