package aima.search.sudoku;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class SudokuSuccessorFunction implements SuccessorFunction {

	public List getSuccessors(Object state) {
		SudokuBoard board = (SudokuBoard) state;

		List<Successor> successors = new ArrayList<Successor>();

		for (int fila = 0; fila < board.getBoard().length; fila++) {
			for (int columna = 0; columna < board.getBoard().length; columna++) {
				if (board.getBoard()[fila][columna] == 0) {
					for (int k = 1; k <= 9; k++) {
						if (board.ubicarNumero(fila, columna, k)) {
							SudokuBoard newBoard = copyOf(board);
							newBoard.setValue(fila, columna, k);
							successors.add(new Successor(newBoard));
						}
					}
				}
			}

		}
		System.out.printf("lista = %d\n", successors.size());
		board.printBoard();
		return successors;
	}

	/*
	 * if (board.canMoveGap(EightPuzzleBoard.UP)) { EightPuzzleBoard newBoard =
	 * copyOf(board); newBoard.moveGapUp(); successors.add(new
	 * Successor(EightPuzzleBoard.UP, newBoard)); }
	 */

	private SudokuBoard copyOf(SudokuBoard board) {
		SudokuBoard newBoard = new SudokuBoard();
		newBoard.setBoard(board);
		return newBoard;
	}

}