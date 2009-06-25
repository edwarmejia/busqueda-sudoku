package aima.search.sudoku;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import aima.search.framework.Successor;


public class SudokuBoard {

	/*
	 * public static String LEFT = "Left";
	 * 
	 * public static String RIGHT = "Right";
	 * 
	 * public static String UP = "Up";
	 * 
	 * public static String DOWN = "Down";
	 */

	public int[][] getBoard() {
		return board;
	}

	int[][] board;
	
	static int[][][] posibles;

	public SudokuBoard() {
		board = new int[][] { { 5, 4, 0, 6, 1, 8, 7, 3, 2 },
				{ 5, 4, 0, 6, 1, 8, 7, 3, 2 }, { 5, 4, 0, 6, 1, 8, 7, 3, 2 },
				{ 5, 4, 0, 6, 1, 8, 7, 3, 2 }, { 5, 4, 0, 6, 1, 8, 7, 3, 2 },
				{ 5, 4, 0, 6, 1, 8, 7, 3, 2 }, { 5, 4, 0, 6, 1, 8, 7, 3, 2 },
				{ 5, 4, 0, 6, 1, 8, 7, 3, 2 }, { 5, 4, 0, 6, 1, 8, 7, 3, 2 } };
	}

	public SudokuBoard(int[][] aBoard) {
		board = aBoard;
		posibles = new int[9][9][10];
		
		/*En el frente del cubo tenemos el tablero*/
		for(int i = 0; i < aBoard.length; i++){
			for(int j = 0; j < aBoard.length; j++){
				posibles[i][j][0] = board[i][j];
			}
		}
		
		/*Y detras del frente tenemos los numeros posibles a colocar*/
		for(int i = 0; i < aBoard.length; i++){
			for(int j = 0; j < aBoard.length; j++){
				for(int k = 1; k < 10; k++){
					if(chequearRegion(i, j, k)){
						posibles[i][j][k] = k;
					}
				}
			}
		}
		
	}

	private boolean hayposibilidad(){
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				for(int k = 1; k < 10; k++){
					if(posibles[i][j][k] != 0){
						return true;
					}
				}
			}
		}
		return false;
	}
	public Set<String> initPopulation(SudokuBoard board){

		Set<String> population = new HashSet<String>();
		Random randomNumbers = new Random();
		int [][] newBoard = new int[9][9];
		
		do{
			/*Reseteamos la matriz con los valores del tablero inicial*/
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
					newBoard[i][j] = board.getBoard()[i][j];
				}
			}
			/*Recorremos toda la matriz buscando valores posibles para llenar la region*/
			for(int i = 0; i < board.getBoard().length; i++){
				for(int j = 0; j < board.getBoard().length; j++){
					if(board.getBoard()[i][j] == 0){
							boolean coloco = false;
							/*Ubicamos randomicamente el nuevo valor*/
							do{
								int randomValue = randomNumbers.nextInt(10);
								if(posibles[i][j][randomValue] != 0 && chequearRegion(newBoard, i, j, randomValue)){
									newBoard[i][j] = randomValue;
									coloco = true;
								}
							}while(!coloco);
					}
				}
			}
			
			String string = boardToSting(newBoard);
			/*Adherimod el nuevo tablero a la poblacion*/
			if(!string.isEmpty())
				population.add(string);
				
		}while(population.size() < 500);
		
		return population;
	}
	
	private String boardToSting(int [][] board){
		String string = new String();
		String aux = new String();
		int columna, fila;
		
		for (int i = 0 ; i < 9 ;  i=i+3)
			for (int j = 0 ; j < 9 ;  j=j+3)
				for (fila = i ; fila < i+3 ; fila++)
					for (columna = j ; columna < j+3 ; columna++){
						aux = (Integer.toString(board[fila][columna]));
					    string += aux;
						
					}
		
		return string;
	}
	
	/* Identifica la region a la que pertenecen dicha fila/columna */
	private int[] identificarRegion(int fila, int columna) {
		int[] retVal = null;

		if (fila <= 2) {
			fila = 0;
			if (columna <= 2) {
				columna = 0;
			} else if (columna > 2 && columna <= 5) {
				columna = 3;
			} else
				columna = 6;
		} else if (fila <= 5 && fila > 2) {
			fila = 3;
			if (columna <= 2) {
				columna = 0;
			} else if (columna > 2 && columna <= 5) {
				columna = 3;
			} else
				columna = 6;
		} else if (fila <= 8 && fila > 5) {
			fila = 6;
			if (columna <= 2) {
				columna = 0;
			} else if (columna > 2 && columna <= 5) {
				columna = 3;
			} else
				columna = 6;
		}
		retVal = new int[] { fila, columna };

		return retVal;
	}

	/* Chequea que no haya repetidos en la fila/columna */
	private boolean chequearFilaColumna(int fila, int columna, int k) {

		for (int i = 0; i < board.length; i++) {
			if (k == board[fila][i] && (columna != i)) {/* Chequea la fila */
				return false;
			}
			if (k == board[i][columna] && (fila != i)) {/* Chequea la columna */
				return false;
			}
		}

		return true;
	}

	/* Chequea que no haya repetidos en la region */
	private boolean chequearRegion(int fila, int columna, int k) {
		int[] posic = null;
		/* Guardamos la fila y la columna de la region */
		posic = identificarRegion(fila, columna);

		for (int i = posic[0]; i < posic[0] + 3; i++) { /* Chequea la region */
			for (int j = posic[1]; j < posic[1] + 3; j++) {
				if (board[i][j] == k) {
					return false;
				}
			}
		}
		return true;
	}

	
	
	/*ESTO HAY QUE ARREGLAR, NO TIENE SENTIDO QUE HAYA DOS FUNCIONES CASI IGUALES*/
	/* Chequea que no haya repetidos en la region */
	private boolean chequearRegion(int [][] board, int fila, int columna, int k) {
		int[] posic = null;
		/* Guardamos la fila y la columna de la region */
		posic = identificarRegion(fila, columna);

		for (int i = posic[0]; i < posic[0] + 3; i++) { /* Chequea la region */
			for (int j = posic[1]; j < posic[1] + 3; j++) {
				if (board[i][j] == k) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	/* Recibe fila/columna y un numero k y chequea si es o no posible ubicar */
	public boolean ubicarNumero(int fila, int columna, int k) {
		if (this.getBoard()[fila][columna] == 0) {
			if (chequearFilaColumna(fila, columna, k)
					&& chequearRegion(fila, columna, k))
				return true;
		}
		return false;

	}

	private int absoluteCoordinatesFromXYCoordinates(int x, int y) {
		return x * 3 + y;
	}

	// private int getValueAt(int x, int y) {
	// refactor this use either case or a div/mod soln
	// return board[absoluteCoordinatesFromXYCoordinates(x, y)];
	// }

	/*
	 * private int getGapPosition() {
	 * 
	 * return getPositionOf(0); }
	 */

	/*
	 * private int getPositionOf(int val) { int retVal = -1; for (int i = 0; i <
	 * 9; i++) { if (board[i] == val) { retVal = i; } } return retVal; }
	 */

	/*
	 * public XYLocation getLocationOf(int val) { int abspos =
	 * getPositionOf(val); int xpos =
	 * xycoordinatesFromAbsoluteCoordinate(abspos)[0]; int ypos =
	 * xycoordinatesFromAbsoluteCoordinate(abspos)[1]; return new
	 * XYLocation(xpos, ypos); }
	 */
	/*private void set(int fila, int columna, int valor) {
		board[fila][columna] = valor;
	}*/

	public void setValue(int fila, int columna, int valor) {
		this.board[fila][columna] = valor;
	}

	public void setBoard(SudokuBoard board, int [][] tablero) {
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				board.setValue(i, j, tablero[i][j]);
			}
		}

	}

	/*
	 * private void setValue(int xPos, int yPos, int val) { int abscoord =
	 * absoluteCoordinatesFromXYCoordinates(xPos, yPos); board[abscoord] = val; }
	 */

	/*
	 * public int getValueAt(XYLocation loc) { return
	 * getValueAt(loc.getXCoOrdinate(), loc.getYCoOrdinate()); }
	 */

	/*
	 * public void moveGapRight() { int gapPosition = getGapPosition(); int xpos =
	 * xycoordinatesFromAbsoluteCoordinate(gapPosition)[0]; int ypos =
	 * xycoordinatesFromAbsoluteCoordinate(gapPosition)[1]; if (!(ypos == 2)) {
	 * int valueOnRight = getValueAt(xpos, ypos + 1); setValue(xpos, ypos,
	 * valueOnRight); setValue(xpos, ypos + 1, 0); } }
	 */

	/*
	 * public void moveGapLeft() { int gapPosition = getGapPosition(); int xpos =
	 * xycoordinatesFromAbsoluteCoordinate(gapPosition)[0]; int ypos =
	 * xycoordinatesFromAbsoluteCoordinate(getGapPosition())[1]; if (!(ypos ==
	 * 0)) { int valueOnLeft = getValueAt(xpos, ypos - 1); setValue(xpos, ypos,
	 * valueOnLeft); setValue(xpos, ypos - 1, 0); } }
	 */

	/*
	 * public void moveGapDown() { int gapPosition = getGapPosition(); int xpos =
	 * xycoordinatesFromAbsoluteCoordinate(gapPosition)[0]; int ypos =
	 * xycoordinatesFromAbsoluteCoordinate(gapPosition)[1]; if (!(xpos == 2)) {
	 * int valueOnBottom = getValueAt(xpos + 1, ypos); setValue(xpos, ypos,
	 * valueOnBottom); setValue(xpos + 1, ypos, 0); } }
	 */

	/*
	 * public void moveGapUp() { int gapPosition = getGapPosition(); int xpos =
	 * xycoordinatesFromAbsoluteCoordinate(gapPosition)[0]; int ypos =
	 * xycoordinatesFromAbsoluteCoordinate(gapPosition)[1]; if (!(xpos == 0)) {
	 * int valueOnTop = getValueAt(xpos - 1, ypos); setValue(xpos, ypos,
	 * valueOnTop); setValue(xpos - 1, ypos, 0); } }
	 */

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		// EightPuzzleBoard aBoard = (EightPuzzleBoard) o;

		for (int i = 0; i < 8; i++) {
			// if (this.getPositionOf(i) != aBoard.getPositionOf(i)) {
			// return false;
			// }
		}
		return true;
	}

	// @Override
	/*
	 * public int hashCode() { int result = 17; for (int i = 0; i < 8; i++) {
	 * int position = this.getPositionOf(i); result = 37 * result + position; }
	 * return result; }
	 */
	/*
	 * public List<XYLocation> getPositions() { ArrayList<XYLocation> retVal =
	 * new ArrayList<XYLocation>(); for (int i = 0; i < 9; i++) { int[] res =
	 * xycoordinatesFromAbsoluteCoordinate(getPositionOf(i)); XYLocation loc =
	 * new XYLocation(res[0], res[1]); retVal.add(loc); } return retVal; }
	 */
	/*
	 * public void setBoard(List<XYLocation> locs) {
	 * 
	 * int count = 0;
	 * 
	 * for (int i = 0; i < locs.size(); i++) { XYLocation loc = locs.get(i);
	 * this.setValue(loc.getXCoOrdinate(), loc.getYCoOrdinate(), count); count =
	 * count + 1; } }
	 */
	/*
	 * public boolean canMoveGap(String where) { boolean retVal = true; int
	 * absPos = getPositionOf(0); if (where.equals(LEFT)) { if ((absPos == 0) ||
	 * (absPos == 3) || (absPos == 6)) { retVal = false; } } if
	 * (where.equals(RIGHT)) { if ((absPos == 2) || (absPos == 5) || (absPos ==
	 * 8)) { retVal = false; } } if (where.equals(UP)) { if ((absPos == 0) ||
	 * (absPos == 1) || (absPos == 2)) { retVal = false; } } if
	 * (where.equals(DOWN)) { if ((absPos == 6) || (absPos == 7) || (absPos ==
	 * 8)) { retVal = false; } }
	 * 
	 * return retVal; }
	 */
	/*
	 * @Override public String toString() { String retVal = board[0] + " " +
	 * board[1] + " " + board[2] + "\n" + board[3] + " " + board[4] + " " +
	 * board[5] + " " + "\n" + board[6] + " " + board[7] + " " + board[8];
	 * return retVal; }
	 */

	/*
	 * @Override public String toString() { String retVal = null;
	 * 
	 * for (int i = 0; i < getBoard().length; i++) { if (i % 8 == 0) retVal =
	 * retVal.concat(getBoard()[i]);
	 * 
	 * retVal = retVal.concat(getBoard()[i]); }
	 * 
	 * return retVal; }
	 */

	/* chequea que la matriz este llena */
	public boolean estaLLena() {

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board.length; j++)
				if (board[i][j] == 0)
					return false;
		return true;
	}

	/*
	 * verifica que una fila tenga todos los numeros diferentes, si una casilla
	 * tiene 0 se devuelva false
	 */
	public boolean filaCorrecta(int fila) {

		int vector_comparacion[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		for (int i = 0; i < board.length; i++) {
			if (board[fila][i] == 0)
				return false;

			if (board[fila][i] != vector_comparacion[(board[fila][i]) - 1])
				return false;
			else
				vector_comparacion[(board[fila][i]) - 1] = 0;
		}
		return true;
	}

	/*
	 * verifica que una columna tenga todos los numeros diferentes, si una
	 * casilla tiene 0 se devuelva false
	 */
	public boolean columnaCorrecta(int columna) {

		int vector_comparacion[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		for (int i = 0; i < board.length; i++) {
			if (board[i][columna] == 0)
				return false;

			if (board[i][columna] != vector_comparacion[(board[i][columna]) - 1])
				return false;
			else
				vector_comparacion[(board[i][columna]) - 1] = 0;
		}
		return true;
	}

	/*
	 * verifica que una columna tenga todos los numeros diferentes, si una
	 * casilla tiene 0 se devuelva false
	 */
	public boolean regionCorrecta(int fila, int columna) {

		int vector_comparacion[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int fila_columna[];

		fila_columna = new int[2];

		fila_columna = this.identificarRegion(fila, columna);

		// recorre la region especificada por fila_columna
		for (int i = fila_columna[0]; i < (fila_columna[0] + 3); i++)
			for (int j = fila_columna[1]; (j < fila_columna[1] + 3); j++) {
				if (board[i][j] == 0)
					return false;
				if (board[i][j] != vector_comparacion[(board[i][j]) - 1])
					return false;
				else
					vector_comparacion[(board[i][j]) - 1] = 0;
			}

		return true;

	}

	// private static void

	public void printBoard() {
		System.out.println("\n ----------------- ");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(' ');
				System.out.print(this.getBoard()[i][j]);
			}
			System.out.print('\n');
		}

	}
	
	
	private SudokuBoard copyOf(int [][] board) {
		SudokuBoard newBoard = new SudokuBoard();
		newBoard.setBoard(newBoard, board);
		return newBoard;
	}

	
	
	

}