/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class SudokuGUI extends JFrame implements Observer {
    
    Container mainContainer;
    CardLayout crd = new CardLayout(10, 10);
    
    // Main Panels
    private JPanel loginScreen = new JPanel(new BorderLayout(5, 5));
    private JPanel gameScreen = new JPanel(new BorderLayout(5, 5));
        
    // Login Panel Setup
    private JPanel titlePanel = new JPanel();
    private JPanel loginPanel = new JPanel();
    private JLabel title = new JLabel("Welcome to Sudoku!");   
    private JLabel uNameLabel = new JLabel("Username: ");
    private JLabel pWordLabel = new JLabel("Password: ");    
    public JTextField inputUserName = new JTextField(15);
    public JTextField inputPassword = new JTextField(15);  
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    
    // Sudoku Game Panel Setup
    
    private boolean gameOn = false; // Indicates if the Sudoku game is on.
    
    int currentX; // Selected square at X
    int currentY; // Selected square at Y
    
    private JLabel[][] square = new JLabel[9][9];
    private JPanel boardPanel = new JPanel(new GridLayout(9, 9));
    private JPanel rightPanel = new JPanel(new GridLayout(2, 1, 1, 125));
    private JPanel numPad = new JPanel(new GridLayout(3, 3));
    private JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
    private JButton saveExit = new JButton("Save & Exit");
    private JButton check = new JButton("Check");
    private JButton newGame = new JButton("New Game");
    private JButton help = new JButton("Help");
    
    public SudokuGUI () {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 455);
        this.mainContainer = this.getContentPane();
        this.mainContainer.setLayout(crd);
        
        mainContainer.add("a", loginScreen);
        mainContainer.add("b", gameScreen);
        
        loginScreen.add(titlePanel, BorderLayout.NORTH);
        titlePanel.add(title);
        title.setFont(new Font("Serif", Font.PLAIN, 40));
        
        loginScreen.add(loginPanel, BorderLayout.CENTER);
        
        loginPanel.add(uNameLabel);
        loginPanel.add(inputUserName);
        loginPanel.add(pWordLabel);
        loginPanel.add(inputPassword);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);
        
        // Game Screen
        
        for(int i= 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                square[i][j] = new GridSquare(i, j);
                boardPanel.add(square[i][j]);
            }
        }
        gameScreen.add(boardPanel);
        gameScreen.add(rightPanel, BorderLayout.EAST);
        rightPanel.add(numPad);
        for (int i = 0; i < 9; i++) {
            numPad.add(new KeypadButton(i + 1));
        }
        
        buttonPanel.add(saveExit);
        buttonPanel.add(check);
        buttonPanel.add(newGame);
        buttonPanel.add(help);
        rightPanel.add(buttonPanel);
        
        this.setVisible(true);
    }
    
    private class GridSquare extends JLabel implements MouseListener {
        
        int gridX;
        int gridY;
        boolean editable;

        public GridSquare(int x, int y) {
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            this.setFont(new Font("Arial", Font.PLAIN, 20));
            this.setForeground(Color.BLACK);
            this.setBackground(Color.WHITE);
            this.setOpaque(true);
            this.setHorizontalAlignment(JTextField.CENTER);
            this.gridX = x;
            this.gridY = y;
            
            this.addMouseListener(this);
        }
        public GridSquare(int x, int y, String str) {
            this(x, y);
            this.setText(str);
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(this.gridX);
            System.out.println(this.gridY);
            currentX = this.gridX;
            currentY = this.gridY;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            currentX = this.gridX;
            currentY = this.gridY;
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    
    private class KeypadButton extends JButton implements ActionListener {
        
        int setNum;
        
        public KeypadButton (int i) {
            this.setNum = i;
            this.setText("" + i);
            this.addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            square[currentX][currentY].setText("" + setNum);
        }
        
    }
    
    public void addListeners(ActionListener listener) {
        this.loginButton.addActionListener(listener);
        this.registerButton.addActionListener(listener);
        this.saveExit.addActionListener(listener);
        this.check.addActionListener(listener);
        this.newGame.addActionListener(listener);
        this.help.addActionListener(listener);
    }
    
    public void switchToGameScreen() {
        crd.show(mainContainer, "b");
    }
    
    public void populateGrid(SudokuData data) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (data.user_grid.getCell(i, j).getNum() != 0) {
                    square[i][j].setText("" + data.user_grid.getCell(i, j).getNum());
                }
            }
        }
    }
    
    public Grid getSudokuGrid() {
        Grid grid = new Grid();
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
               
                //grid.setCell(pos, Integer.parseInt(square[i][j].getText()));
                grid.getGrid()[i][j] = new Cell(Integer.parseInt(square[i][j].getText()));
            }
        }
        
        return grid;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        SudokuData data = (SudokuData) arg;
        if (!data.newUser) {
            if (!data.loginFlag) {
                System.out.println("No switchy");
            }
            else if (!this.gameOn) {
                this.switchToGameScreen();
                this.gameOn = true;
            }
        }
        else {
            this.populateGrid(data);
            if (!this.gameOn) {
                this.switchToGameScreen();
                this.gameOn = true;
            }
        }
    }
}
