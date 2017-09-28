/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marsrovers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import jdk.jfr.events.FileWriteEvent;

/**
 *
 * @author Neto Travizan
 */
public class PlateauController {
    private int sizeX, sizeY;
    private ArrayList<Rover> rovers;
    
    public PlateauController(){
        rovers = new ArrayList<Rover>();
    }

    public void addRover(Rover rover){
        if (hasRover(rover.getX(), rover.getY()) || rover.getX() >= sizeX || rover.getY() >= sizeY)
            System.out.println("Position unavailable.");
        else
            rovers.add(rover);
    }
    public void removeRover(Rover rover){
        for(int i=0; i<rovers.size(); i++){
            if (rovers.get(i).getX() == rover.getX() && rovers.get(i).getY() == rover.getY())
                rovers.remove(i);
                
        }
    }
    
    public boolean hasRover(int x, int y){
        int i=0;
        while(i < rovers.size()){
            if (rovers.get(i).getX() == x && rovers.get(i).getY() == y)
                return true;
            i++;
        }
        return false;
    }
    
    public void showRovers(){
        int i=0;
        while(i<rovers.size()){
            rovers.get(i).showYourself();
            i++;
        }
    }
    
    public void saveRovers(FileWriter fileWriter) throws IOException{
        int i=0;
        while(i<rovers.size()){
            rovers.get(i).saveYourself(fileWriter);
            i++;
        }
        fileWriter.close();
        System.out.println("Rovers positions saved at 'output.txt'");
    }
    
    public void readInput(FileReader fileInput) throws IOException{
        
        BufferedReader buffer = new BufferedReader(fileInput);
        String line = buffer.readLine();

        try{
            sizeX = Integer.parseInt(line.split(" ")[0]) + 1;
            sizeY = Integer.parseInt(line.split(" ")[1]) + 1;
        }catch(NumberFormatException ex){
            System.out.println("File input error: " + ex);
        }catch(NullPointerException np){
            System.err.println("Empty file input: " + np);
        }catch(ArrayIndexOutOfBoundsException a){
            System.out.println("File input error: " + a);
        }
        
        line = buffer.readLine();
        while(line != null){
            Rover rover = null;
            try{
                String charThree = line.split(" ")[2];
                if (charThree.length() != 1 || (charThree.charAt(0) != 'N' && charThree.charAt(0) != 'W' && charThree.charAt(0) != 'S' && charThree.charAt(0) != 'E') ){
                    System.out.println("File input error. Follow the model.");
                    line = buffer.readLine();
                }
                else
                    rover = new Rover(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]), charThree.charAt(0));
                
            }catch(NumberFormatException e){
                System.out.println("File input error: " + e);
            }catch(NullPointerException np){
                System.err.println("Empty file input: " + np);
            }catch(ArrayIndexOutOfBoundsException a){
                System.out.println("File input error: " + a);
            }
            
            line = buffer.readLine();
            try{
                int i=0;
                while(i < line.length()){
                    if (line.charAt(i) != 'L' && line.charAt(i) != 'M' && line.charAt(i) != 'R'){
                        System.out.println("File input error: Invalid movement. Follow the model.");
                    }
                    else
                        rover.addMovement(line.charAt(i));
                    i++;
                }
            
            }catch(NullPointerException np){
                System.err.println("Error in file input: " + np);
            }
            if (rover != null)
                addRover(rover);
            
            line = buffer.readLine();
        }
        
    }
    
    public void moveRovers(){
        int i=0;
        while(i<rovers.size()){
            
            while(rovers.get(i).hasMovements()){
                switch(rovers.get(i).getNextMovement()){
                    case 'L':
                        rovers.get(i).spinLeft();
                        break;
                    case 'R':
                        rovers.get(i).spinRight();
                        break;
                    case 'M':
                        switch(rovers.get(i).getFacing()){
                            case 'E':
                                if ( (rovers.get(i).getX() + 1 < sizeX) && !hasRover(rovers.get(i).getX() + 1, rovers.get(i).getY()) )
                                    rovers.get(i).move();
                                break;
                            case 'S':
                                if ( (rovers.get(i).getY() - 1 >= 0) && !hasRover(rovers.get(i).getX(), rovers.get(i).getY() - 1) )
                                    rovers.get(i).move();
                                break;
                            case 'W':
                                if ( (rovers.get(i).getX() - 1 >= 0) && !hasRover(rovers.get(i).getX() - 1, rovers.get(i).getY()) )
                                    rovers.get(i).move();
                                break;
                            case 'N':
                                if ( (rovers.get(i).getY() + 1 < sizeY) && !hasRover(rovers.get(i).getX(), rovers.get(i).getY() + 1) )
                                    rovers.get(i).move();
                                break;
                        }
                        break;
                }
//                System.out.println("-- " + rovers.get(i).getX() + " " + rovers.get(i).getY() + " " + rovers.get(i).getFacing());
            }
            
//            System.out.println("-----------------------");
            i++;
        }
        System.out.println("Moved Rovers.");
    }
    

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
    
    
    
}
