package sudoku;

import java.io.*;

public class SudokuFileManager {
    FileInputStream fis;
    FileOutputStream fos;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Grid gridToSave;
    String user;
    
    public SudokuFileManager (SudokuData data) {
        this.gridToSave = data.user_grid;
    }
    
    public void saveGrid(String filePath) {
        try {
            fos = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this.gridToSave);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public Grid loadGrid(String filePath) {
        Grid input_grid = null;
        try {
            fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            input_grid = (Grid) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (ClassNotFoundException ex) {
            System.out.println("Grid not found");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return input_grid;
    }
   
}
