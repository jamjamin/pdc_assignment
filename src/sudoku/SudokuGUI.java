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
import javax.swing.border.EmptyBorder;

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
    public JPasswordField inputPassword = new JPasswordField(15);  
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private JButton exitButton = new JButton("Exit");
    
    // Sudoku Game Panel Setup
    
    private boolean gameOn = false; // Indicates if the Sudoku game is on.
    
    int currentX; // Selected square at X
    int currentY; // Selected square at Y
    
    private GridSquare[][] square = new GridSquare[9][9];
    private JPanel boardPanel = new JPanel(new GridLayout(9, 9));
    private JPanel rightPanel = new JPanel(new GridLayout(2, 1, 1, 125));
    private JPanel numPad = new JPanel(new GridLayout(3, 3));
    private JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
    private JButton saveExit = new JButton("Save & Exit");
    private JButton saveLogout = new JButton("Save & Logout");
    private JButton check = new JButton("Check");
    private JButton newGame = new JButton("New Game");
    private JButton help = new JButton("Help");
    
    public SudokuGUI () {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 455);
        this.setResizable(false);
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
        loginPanel.add(exitButton);
        
        uNameLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
        pWordLabel.setBorder(new EmptyBorder(0, 20, 0, 5));
        
        inputPassword.setEchoChar('*');
        
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
        buttonPanel.add(saveLogout);
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
        boolean selected;

        public GridSquare(int x, int y) {
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            this.setFont(new Font("Arial", Font.PLAIN, 20));
            this.setForeground(Color.BLACK);
            this.setBackground(Color.WHITE);
            this.setOpaque(true);
            this.setHorizontalAlignment(JTextField.CENTER);
            this.gridX = x;
            this.gridY = y;
            this.editable = true;
            this.selected = false;
            this.addMouseListener(this);
        }
        public GridSquare(int x, int y, int num) {
            this(x, y);
            this.setText("" + num);
            this.editable = false;
        }
        
        public void resetSelection() {
            square[currentX][currentY].setBackground(Color.WHITE);
            square[currentX][currentY].setSelected(false);
        }
        
        public void setSelected(boolean bool) {
            this.selected = bool;
        }
        
        public void setEditable(boolean bool) {
            this.editable = bool;
        }
        
        public boolean isEditable() {
            return this.editable;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!selected) {
                resetSelection();
                this.selected = true;
                currentX = this.gridX;
                currentY = this.gridY;
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            this.setBackground(Color.DARK_GRAY);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (editable) {
                this.setBackground(Color.GRAY);
            }
            else {
                this.setBackground(Color.RED);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!selected) {
                this.setBackground(Color.LIGHT_GRAY);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!selected) {
                this.setBackground(Color.WHITE);
            }
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
            if (square[currentX][currentY].isEditable()) {
                square[currentX][currentY].setText("" + setNum);
            }            
        }
    }
    
    public void addListeners(ActionListener listener) {
        this.loginButton.addActionListener(listener);
        this.registerButton.addActionListener(listener);
        this.saveExit.addActionListener(listener);
        this.saveLogout.addActionListener(listener);
        this.check.addActionListener(listener);
        this.newGame.addActionListener(listener);
        this.help.addActionListener(listener);
        this.exitButton.addActionListener(listener);
    }
    
    public void switchToGameScreen() {
        crd.show(mainContainer, "b");
    }
    
    public void switchToLoginScreen() {
        crd.show(mainContainer, "a");
    }
    
    public void resetGrid() {
        System.out.println("resetGrid");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                square[i][j].setText("");
                square[i][j].setEditable(true);
                square[i][j].setSelected(false);
                square[i][j].setBackground(Color.WHITE);
            }
        }
    }
    
    public void populateGrid(SudokuData data) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (data.user_grid.getCell(j, i).getNum() != 0) {
                    square[i][j].setText("" + data.user_grid.getCell(j, i).getNum());
                    square[i][j].setEditable(data.user_grid.getCell(j, i).isEditable());            
                }
            }
        }
    }
    
    public Grid getSudokuGrid() {
        Grid grid = new Grid();
        int num;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ("".equals(square[i][j].getText())) {
                    num = 0;
                } else {
                    num = Integer.parseInt(square[i][j].getText());
                }               
                
                grid.getGrid()[i][j] = new Cell(num);
                grid.getGrid()[i][j].setEditable(square[i][j].isEditable());
            }
        }
        
        return grid;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        SudokuData data = (SudokuData) arg;
        if (!this.gameOn) {
            if (!data.newUser) {                
                if (!data.loginFlag) {
                    System.out.println("No Switchy");
                    if (data.registerFlag) {
                        JOptionPane.showMessageDialog(null, "User already exists", 
                          "Registeration Error", JOptionPane.ERROR_MESSAGE); 
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "User/Password incorrect", 
                                        "Login Error", JOptionPane.ERROR_MESSAGE);  
                    }
                }
                else {
                    this.populateGrid(data);
                    this.switchToGameScreen();
                    this.gameOn = true;
                }
            }
            else {
                this.populateGrid(data);
                this.switchToGameScreen();
                this.gameOn = true;
            }
        }
        else {
            if (data.logoutFlag) {
                this.switchToLoginScreen();
                this.resetGrid();
                this.gameOn = false;
            }
            else if (data.newGrid) {
                this.populateGrid(data);
            }
        }
    }
}
