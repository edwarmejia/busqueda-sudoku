package aima.search.sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SudokuFileParser {

	private static Scanner input;

	private static int [][] board;
	
	private static void setBoard() {
		board = new int [9][9];
	}

	public static int [][] getBoard() {

		Scanner input = new Scanner(System.in);

		setBoard();
		System.out.print("Ingresar el path al archivo de tableros: ");
		String path = input.nextLine();
		analyzePath(path);
		openFile(path);
		loadBoard();
		closeFile();
		
		return board;

	}

	private static void analyzePath(String path) {
		// create File object based on user input
		File name = new File(path);

		if (name.exists()) { // if name exists, output information about
			// it
			// display file (or directory) information
			System.out.printf("%s%s\n%s\n%s\n%s%s", name.getName(), " existe",
					(name.isFile() ? "y es un archivo"
							: "pero no es un archivo, "),
					(name.isDirectory() ? "es un directorio"
							: "no es un directorio"), "Absolute path: ", name
							.getAbsolutePath());

			if (name.isDirectory()) { /* listar archivos dentro del directorio */
				String directory[] = name.list();
				System.out.println("\n\nEl contenido del directorio es:\n");

				for (String directoryName : directory)
					System.out.printf("%s\n", directoryName);
			}
		} else { /* no existe el archivo o directorio, mensaje de error */
			System.out.printf("%s %s", path, "no existe.");
		}

	}

	// enable user to open file
	private static void openFile(String path) {
		try {
			input = new Scanner(new File(path));
		} // end try
		catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error opening file.");
			System.exit(1);
		} // end catch
	} // end method openFile

	// read record from file
	private static void loadBoard() {
		// object to be written to screen
		SudokuBoard tablero = new SudokuBoard();

		try // read records from file using Scanner object
		{
			//System.out.printf("\n%s\n", input.next());
			//System.out.printf("El nro de puzzle es: %s\n", input.next());
			int fila = 0;
			int columna = 0;
			while (input.hasNext()) {
				
				String string = input.next();
				

				for(int j = 0; j < 18; j++){
					char c = string.charAt(j);
					if(c != ',' && c != ';'){
						if(c != '_')
							board[fila][columna] = c - 48;
						else
							board[fila][columna] = 0;
						columna++;
					}
				}
				columna = 0;
				fila++;
			} // end while
		} // end try
		catch (NoSuchElementException elementException) {
			System.err.println("El archivo no tiene el formato correcto.");
			input.close();
			System.exit(1);
		} // end catch
		catch (IllegalStateException stateException) {
			System.err.println("Error al leer el archivo.");
			System.exit(1);
		} // end catch
	} // end method readRecords

	// close file and terminate application
	private static void closeFile() {
		if (input != null)
			input.close(); // close file
	} // end method closeFile

}
