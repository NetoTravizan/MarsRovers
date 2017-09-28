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

 // Classe que define os rovers
 
 public class Rover {
    // Posicao
    private int x;
    private int y;
    // Qual o sentido o rover esta olhando
	private char facing;
	// Lista de movimentos
    private ArrayList<Character> movements;
    
	
	// Construtor, inicializando lista de movimentos
    public Rover(int x, int y, char facing){
        this.x = x;
        this.y = y;
        this.facing = facing;
        movements = new ArrayList<Character>();
    }

	// Funcao para adicionar o movimento na lista
    public void addMovement(char movement){
        movements.add(movement);
    }

	// Funcao para pegar o proximo movimento da lista e remove-lo.
    public char getNextMovement(){
        char nextMovement = movements.get(0);
        movements.remove(0);
        return nextMovement;
    }
    
	// Funcao para verificar se o rover ainda possui movimentos a fazer
    public boolean hasMovements(){
        if (movements.size() != 0)
            return true;
        else
            return false;
    }
    
	// Funcao para exibir na tela a posicao do rover e seus movimentos
    public void showYourself(){
        System.out.print("My position: " + x + " , " + y + " , " + facing);
		if (hasMovements()){
			System.out.print("         My movements: ");
			for(int i=0; i<movements.size(); i++)
				System.out.print(movements.get(i));
		}
        System.out.println("");
    }
    
	// Funcao para salvar no arquivo output.txt a posicao do rover
    public void saveYourself(FileWriter fileWriter){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt", true));
            PrintWriter out = new PrintWriter(bw);

            out.println(x + " " + y + " " + facing);
            
            out.close();
            
        }catch (IOException ex) {
            System.out.println("Error writing to file output.");
        }
    }

	// Funcao para virar o rover 90 graus a esquerda
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
    
	
	// Funcao para virar o rover 90 graus a direita
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
    
	// Funcao para mover o rover de acordo com seu facing
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
    
	
	// Getters e Setters
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
