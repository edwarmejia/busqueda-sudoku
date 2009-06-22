package aima.search.sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SudokuFileParser {

	private Scanner input;

	public int[][] getBoards() {

		return null;
	}

	public static void main(String args[]) {

		Scanner input = new Scanner(System.in);
		SudokuFileParser application = new SudokuFileParser();
		System.out.print("Ingresar el path al archivo de tableros: ");
		application.analyzePath(input.nextLine());
		application
				.openFile("C:\\Users\\jsanta\\Desktop\\puzzles\\puzzles\\puzzlenomas.txt");
		application.readBoard();
		application.closeFile();

	}

	public void analyzePath(String path) {
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
	public void openFile(String path) {
		try {
			input = new Scanner(new File(path));
		} // end try
		catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error opening file.");
			System.exit(1);
		} // end catch
	} // end method openFile

	// read record from file
	public void readBoard() {
		// object to be written to screen
		SudokuBoard tablero = new SudokuBoard();

		try // read records from file using Scanner object
		{
			System.out.printf("\n%s\n", input.next());
			System.out.printf("El nro de puzzle es: %s\n", input.next());

			while (input.hasNext()) {

				String tableeero = input.next();
				// input.next()
				char tab[] = tableeero.toCharArray();

				// System.out.printf("%s\n", tableeero);

				for (int fila = 0; fila < 9; fila++) {
					for (int columna = 0; columna < 9; columna++) {
						System.out.printf("%c", tab[(fila * 9 + columna)]);

					}
				}

				/*
				 * if (tableeeero[fila][columna] == '_')
				 * System.out.printf("%s\n", 0);
				 */

				// if(input.nextByte() == '')
				// if (input.nextByte() == '_') {
				/*
				 * tablero.setValue(fila, columna, 0); System.out.printf("%d\n",
				 * 0);
				 */
				// } else {
				/*
				 * tablero.setValue(fila, columna, input.nextInt()); System.out
				 * .printf("%d\n", tablero.getBoard()[fila][columna]);
				 */
				// }
				// input.nextByte();
				// System.out.printf("%s%\n", input.next());
				/*
				 * record.setAccount(input.nextInt()); // read account // number
				 * record.setFirstName(input.next()); // read first name
				 * record.setLastName(input.next()); // read last name
				 * record.setBalance(input.nextDouble()); // read balance //
				 * display record contents
				 * System.out.printf("%-10d%-12s%-12s%10.2f\n", record
				 * .getAccount(), record.getFirstName(), record .getLastName(),
				 * record.getBalance());
				 */
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
	public void closeFile() {
		if (input != null)
			input.close(); // close file
	} // end method closeFile

}
