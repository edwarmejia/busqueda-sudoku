package aima.search.sudoku;

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

	public SudokuBoard() {
		board = new int[][] { { 5, 4, 0, 6, 1, 8, 7, 3, 2 },
				{ 5, 4, 0, 6, 1, 8, 7, 3, 2 }, { 5, 4, 0, 6, 1, 8, 7, 3, 2 },
				{ 5, 4, 0, 6, 1, 8, 7, 3, 2 }, { 5, 4, 0, 6, 1, 8, 7, 3, 2 },
				{ 5, 4, 0, 6, 1, 8, 7, 3, 2 }, { 5, 4, 0, 6, 1, 8, 7, 3, 2 },
				{ 5, 4, 0, 6, 1, 8, 7, 3, 2 }, { 5, 4, 0, 6, 1, 8, 7, 3, 2 } };
	}

	public SudokuBoard(int[][] aBoard) {
		board = aBoard;
	}

	private int[] xycoordinatesFromAbsoluteCoordinate(int x) {
		int[] retVal = null;
		switch (x) {
		case 0:
			retVal = new int[] { 0, 0 };
			break;
		case 1:
			retVal = new int[] { 0, 1 };
			break;
		case 2:
			retVal = new int[] { 0, 2 };
			break;
		case 3:
			retVal = new int[] { 1, 0 };
			break;
		case 4:
			retVal = new int[] { 1, 1 };
			break;
		case 5:
			retVal = new int[] { 1, 2 };
			break;
		case 6:
			retVal = new int[] { 2, 0 };
			break;
		case 7:
			retVal = new int[] { 2, 1 };
			break;
		case 8:
			retVal = new int[] { 2, 2 };
			break;

		}
		return retVal;
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
	private void set(int fila, int columna, int valor) {
		board[fila][columna] = valor;
	}

	public void setValue(int fila, int columna, int valor) {
		set(fila, columna, valor);
	}

	public void setBoard(SudokuBoard board) {
		this.board = board.getBoard();
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

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(' ');
				System.out.print(this.getBoard()[i][j]);
			}
			System.out.print('\n');
		}

	}

}