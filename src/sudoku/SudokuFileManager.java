package sudoku;

import java.io.*;

public class SudokuFileManager {
    FileInputStream fis;
    FileOutputStream fos;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Grid gridToSave;
    String user;
    
    /**
     * 
     * Constructor which sets the instance variable gridToSave to the user's
     * grid.
     * 
     * @param data 
     */
    public SudokuFileManager (SudokuData data) {
        this.gridToSave = data.user_grid;
    }
    
    /**
     * 
     * Saves the grid to a file (Overwrites it if already exists).
     * 
     * @param filePath 
     */
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
    
    /**
     * 
     * Loads the grid data from file.
     * 
     * @param filePath to file.
     * @return grid data.
     */
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
    
    /**
     * 
     * Deletes the save file.
     * 
     * @param filePath 
     */
    public void deleteSave(String filePath) {
        try {
            File f = new File(filePath);
            f.delete();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
   
}
