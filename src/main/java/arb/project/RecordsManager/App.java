package arb.project.RecordsManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App 
{
	private static Logger logger = LogManager.getLogger(App.class);
	private static final String INPUT_FILE = "scores.txt"; // Puntuaciones archivo
	private static final String OUTPUT_FILE = "records.txt"; // Records archivo
	private static final int MIN_NAME_LENGTH = 6;
	private static final int MIN_SCORE = 1000;
	
	private static Random r = new Random();
	
    public static void main( String[] args ) {
    	// Map para guardar los datos leidos
    	//  Nombre, puntuacion
    	Map<String, Integer> players = new HashMap<>();
    	
    	// (1) Leer fichero de entrada
    	try (Scanner s = new Scanner(new File(INPUT_FILE))) {
        	// Mientras tenga mas lineas
        	while(s.hasNextLine()) {
        		
        		// Cogemos la siguiente linea
        		String line = s.nextLine();
        		
        		// la troceamos por los espacios
        		String[] data = line.split(" ");
        		
        		// El primer fragmento sera el nombre del jugador
        		String name = data[0];
        		
        		// El segundo fragmento su puntiacion
        		int score = Integer.parseInt(data[1]);
        		
        		// Validamos el nombre
        		try {
        			validateName(name);
        		} catch (PlayerNameTooShortException pntse) {
        			logger.warn(pntse.getMessage());
        			name = generateNewName(name);
        			System.out.println("El nuevo nombre del usuario es: " + name);
        		}
        		
        		// Validamos la puntuacion despues de haber arreglado el nombre si se
        		// hubiese dado el caso
        		try {
        			validateScore(name, score);
        		} catch (ScoreTooLowException stle) {
        			logger.error(stle.getMessage());
        			continue; // saltamos este jugador y vamos a la siguiente linea
        		}
        		
        		// Añadimos el jugador al diccionario
        		players.put(name, score);
        		
        		logger.info("Línea tratada correctamente: " + name + " - " + score);
        		
           	}
    	} catch (FileNotFoundException fnfe) {
    		logger.error("No podemos ejecutar el programa porque no se encuentra "
    				+ "el fichero de entrada esperado " + INPUT_FILE);
    	}
    	

   
    	// (2) Mostrar por consola los datos guardados
    	System.out.println("Datos procesados:");
    	for (String name : players.keySet()) {
    		System.out.println(name + ":\t" + players.get(name));
    	}
    	
    	// (3) Pedir confirmacion al usuario
    	System.out.println("¿Son correctos los datos? [S]i / [N]o");
    	
    	String answer;
    	
    	// Leemos la respuesta del usuario por entrada estandar
    	try (Scanner sConsole = new Scanner(System.in)) {
        	answer = sConsole.next();
    	};
    	// Comprobamos si es afirmativa
    	boolean confirmed = answer.equalsIgnoreCase("S");
    	
    	// Escribimos al fichero
    	if (confirmed) {
    		System.out.println("Procedamos al volcado de datos al fichero...");
    		// Abrimos el fuchero de salida para escribir en el
    		try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE)) {
        		// Por cada uno de los jugadores que hay en el diccionario
        		for (String name : players.keySet()) {
        			// Escribimos una linea en el fichero de salida
        			fos.write((name + " " + players.get(name) + "\n").getBytes());
        		}
    		} catch (IOException ioe) {
    			logger.error("No hemos podido escribir los resultados en el " + 
    						"fichero porque algo ha fallado: " + ioe.getMessage());
    		}

    		
    	}
    	
    	
    }

	protected static void validateName(String name) throws PlayerNameTooShortException {

		// Validamos la longitud del nombre
		if (name.length() > MIN_NAME_LENGTH) {
			throw new PlayerNameTooShortException(name);
		}
	
	}

	protected static void validateScore(String name, int score) throws ScoreTooLowException {

		// Validamos la puntuacion minima
		 
		if (score < MIN_SCORE) {
			throw new ScoreTooLowException(name, score);			
		}	
	}
	

	protected static String generateNewName(String name) {

		int randomSize = MIN_NAME_LENGTH - name.length();
		
		for (int i = 0; i < randomSize; i++) {
			int randomNum = r.nextInt(10);
			name += randomNum; // añadimos un numero mas al final
		}
		
		return name;
	}

}
