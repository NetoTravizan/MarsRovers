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

 // Classe para controlar todo o ambiente, ou plateau
 
public class PlateauController {
    // Tamanho do plateau
	private int sizeX, sizeY;
	// Lista dos rovers. Esta classe tem acesso e manipula todos os rovers que estao no plateau.
    private ArrayList<Rover> rovers;
    
	
	// Inicializacao da lista de rovers
    public PlateauController(){
        rovers = new ArrayList<Rover>();
    }

	// Funcao para adicionar um rover na lista do plateau
    public void addRover(Rover rover){
        // Verificacao se nao ha rovers na posicao, e se a posicao e valida
		if (hasRover(rover.getX(), rover.getY()) || rover.getX() >= sizeX || rover.getY() >= sizeY)
            System.out.println("Position unavailable.");
        else
            rovers.add(rover);
    }
	
	// Funcao para remover um rover da lista do plateau
    public void removeRover(Rover rover){
		
		// Buscando na lista o rover que possui a mesma posicao do passado por parametro
        for(int i=0; i<rovers.size(); i++){
            if (rovers.get(i).getX() == rover.getX() && rovers.get(i).getY() == rover.getY())
                rovers.remove(i);
                
        }
    }
    
	// Funcao que retorna se possui um rover na posicao passada por parametro
    public boolean hasRover(int x, int y){
        int i=0;
        while(i < rovers.size()){
            if (rovers.get(i).getX() == x && rovers.get(i).getY() == y)
                return true;
            i++;
        }
        return false;
    }

	// Funcao para mostrar todas as posicoes dos rovers e sua lista de movimentos
    public void showRovers(){
        int i=0;
        while(i<rovers.size()){
            rovers.get(i).showYourself();
            i++;
        }
    }
    
	// Funcao para escrever no arquivo as posicoes de todos os rovers
    public void saveRovers(FileWriter fileWriter) throws IOException{
        int i=0;
        while(i<rovers.size()){
            rovers.get(i).saveYourself(fileWriter);
            i++;
        }
        fileWriter.close();
        System.out.println("Rovers positions saved at 'output.txt'");
    }

	// Funcao para fazer a leitura do arquivo de input
    public void readInput(FileReader fileInput) throws IOException{
        
        BufferedReader buffer = new BufferedReader(fileInput);
        String line = buffer.readLine();

		// Formatacao da primeira linha:X Y
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
        
		// Loop para iterar cada conjunto de linhas:
		// 1a linha do rover: X Y Facing
		// 2a linha do rover: <movimentos>
        line = buffer.readLine();
        while(line != null){
            Rover rover = null;
			// 1a linha
            try{
				// Comparando se o caracter passado e invalido
                String charThree = line.split(" ")[2];
                if (charThree.length() != 1 || (charThree.charAt(0) != 'N' && charThree.charAt(0) != 'W' && charThree.charAt(0) != 'S' && charThree.charAt(0) != 'E') ){
                    System.out.println("File input error. Follow the model.");
                    line = buffer.readLine();
                }
				// Se for valido, cria um novo rover
                else
                    rover = new Rover(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]), charThree.charAt(0));
                
            }catch(NumberFormatException e){
                System.out.println("File input error: " + e);
            }catch(NullPointerException np){
                System.err.println("Empty file input: " + np);
            }catch(ArrayIndexOutOfBoundsException a){
                System.out.println("File input error: " + a);
            }
            
			// 2a linha
            line = buffer.readLine();
            try{
				// Adicionando os movimentos validos ao rover criado anteriormente
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
			
			// Adicionando o rover valido na lista do plateau
            if (rover != null)
                addRover(rover);
            
			// Proximo conjunto de linhas
            line = buffer.readLine();
        }
        
    }
    
	
	// Funcao que movimentara os rovers, um por um
    public void moveRovers(){
        int i=0;
		
		// Iteracao de cada rover
        while(i<rovers.size()){
			// Iteracao de cada movimento do rover
            while(rovers.get(i).hasMovements()){
				// Fazendo os diferentes tipos de movimento
                switch(rovers.get(i).getNextMovement()){
                    // Virar para a esquerda
					case 'L':
                        rovers.get(i).spinLeft();
                        break;
                    // Virar para a direita
					case 'R':
                        rovers.get(i).spinRight();
                        break;
                    // Mover
					case 'M':
						// Comparando se e possivel mover o rover, de acordo com sua posicao e facing
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
            }
            
            i++;
        }
        System.out.println("Moved Rovers.");
    }
    

	// Getters e Setters
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
