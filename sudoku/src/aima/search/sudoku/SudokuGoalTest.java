package aima.search.sudoku;

import aima.search.framework.GoalTest;

public class SudokuGoalTest implements GoalTest {

	SudokuBoard board; // Tablero que se recibe

	public boolean isGoalState(Object state) {
		board = (SudokuBoard) state;

		return (tableroSolucion());

	}

	private boolean tableroSolucion() {

		// verifica que el tablero este lleno
		if (!(board.estaLLena()))
			return false;

		// verifica que cada fila y columna que este correcta
		for (int i = 0; i < 9; i++) {
			if ((!(board.filaCorrecta(i))) || ((!(board.columnaCorrecta(i)))))
				return false;
		}

		// verifica cada region
		for (int i = 0; i < 9; i += 3)
			for (int j = 0; j < 9; j += 3) {
				if (!(board.regionCorrecta(i, j)))
					return false;
			}

		// si llega hasta aca el tablero es solucion y retorna true
		System.out.println("\nLa solucion es:");
		board.printBoard();
		return true;
	}
}
