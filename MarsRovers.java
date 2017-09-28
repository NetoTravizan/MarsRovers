/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marsrovers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Neto Travizan
 */
public class MarsRovers {
    public static PlateauController plateauController = new PlateauController();

    public static void main(String[] args) throws IOException {
        
        try{
            System.out.println("Reading file: " + args[0]);
        
            plateauController.readInput(new FileReader(args[0]));

            plateauController.showRovers();
            plateauController.moveRovers();
            plateauController.showRovers();
            plateauController.saveRovers(new FileWriter("output.txt"));
            
            
        }catch(ArrayIndexOutOfBoundsException a){
            System.out.println("Invalid input parameter. Write the filename correctly.");
        }catch(FileNotFoundException f){
            System.out.println("File not found.");
        }        
    }

    
}
