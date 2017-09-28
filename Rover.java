/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marsrovers;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Neto Travizan
 */
public class Rover {
    
    private int x;
    private int y;
    private char facing;
    private ArrayList<Character> movements;
    
    public Rover(int x, int y, char facing){
        this.x = x;
        this.y = y;
        this.facing = facing;
        movements = new ArrayList<Character>();
    }
    
    public void addMovement(char movement){
        movements.add(movement);
    }
    
    public char getNextMovement(){
        char nextMovement = movements.get(0);
        movements.remove(0);
        return nextMovement;
    }
    
    public boolean hasMovements(){
        if (movements.size() != 0)
            return true;
        else
            return false;
    }
    
    public void showYourself(){
        System.out.print("My position: " + x + " , " + y + " , " + facing + "         My movements: ");
        for(int i=0; i<movements.size(); i++)
            System.out.print(movements.get(i));
        System.out.println("");
    }
    
    public void saveYourself(FileWriter fileWriter){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt", true));
            PrintWriter out = new PrintWriter(bw);

            out.println(x + " " + y + " " + facing);
            
            out.close();
            
        }catch (IOException ex) {
            System.out.println("Error writing to file output.");
        }
//        
//        System.out.print("My position: " + x + " , " + y + " , " + facing + "         My movements: ");
//        for(int i=0; i<movements.size(); i++)
//            System.out.print(movements.get(i));
//        System.out.println("");
    }

    public void spinLeft(){
        switch(facing){
            case 'E':
                facing = 'N';
                break;
            case 'N':
                facing = 'W';
                break;
            case 'W':
                facing = 'S';
                break;
            case 'S':
                facing = 'E';
                break;
        }
    }
    
    public void spinRight(){
        switch(facing){
            case 'E':
                facing = 'S';
                break;
            case 'S':
                facing = 'W';
                break;
            case 'W':
                facing = 'N';
                break;
            case 'N':
                facing = 'E';
                break;
        }
    }
    
    public void move(){
        switch(facing){
            case 'E':
                x++;
                break;
            case 'S':
                y--;
                break;
            case 'W':
                x--;
                break;
            case 'N':
                y++;
                break;
        }
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getFacing() {
        return facing;
    }

    public void setFacing(char facing) {
        this.facing = facing;
    }

    
}
