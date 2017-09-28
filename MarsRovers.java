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

// Classe de execucao 
 
 public class MarsRovers {
	// Controlador do planalto.
    public static PlateauController plateauController = new PlateauController();

	
	// Main
    public static void main(String[] args) throws IOException {
        try{
			// Leitura do arquivo passado por parametro
            System.out.println("Reading file: " + args[0]);
            plateauController.readInput(new FileReader(args[0]));

			// Exibindo na tela a posicao antes e depois dos rovers andarem
            plateauController.showRovers();
            plateauController.moveRovers();
            plateauController.showRovers();
			
			// Salvando a posicao final dos rovers no arquivo 'output.txt'
            plateauController.saveRovers(new FileWriter("output.txt"));
            
            
        }catch(ArrayIndexOutOfBoundsException a){
            System.out.println("Invalid input parameter. Write the filename correctly.");
        }catch(FileNotFoundException f){
            System.out.println("File not found.");
        }        
    }

}
