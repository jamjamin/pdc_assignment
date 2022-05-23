/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class SudokuGUI extends JFrame {
    
    // Main Panels
    private JPanel loginScreen = new JPanel(new BorderLayout());
    private JPanel gameScreen = new JPanel();
        
    // Login Panel Setup
    private JPanel titlePanel = new JPanel();
    private JPanel loginPanel = new JPanel();
    
    private JLabel title = new JLabel("Welcome to Sudoku!");
    
    private JLabel uNameLabel = new JLabel("Username: ");
    private JLabel pWordLabel = new JLabel("Password: ");
    
    private JTextField inputUserName = new JTextField(15);
    private JTextField inputPassword = new JTextField(15);
   
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    
    // Sudoku Game Panel Setup
    
    public SudokuGUI () {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        
        add(loginScreen);
        
        loginScreen.add(titlePanel, BorderLayout.NORTH);
        titlePanel.add(title);
        
        loginScreen.add(loginPanel, BorderLayout.CENTER);
        
        loginPanel.add(uNameLabel);   
        loginPanel.add(inputUserName);
        loginPanel.add(pWordLabel);
        loginPanel.add(inputPassword);
        loginPanel.add(loginButton);
    }
    
    public static void main(String[] args) {
        System.out.println("GUI Test running");
        
        SudokuGUI gui = new SudokuGUI();
        gui.setVisible(true);
    }
}
