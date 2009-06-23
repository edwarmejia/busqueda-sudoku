package aima.search.sudoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class SudokuSuccessorFunction implements SuccessorFunction {
	
	static int contSucesores = 0;
	
	public List getSuccessors(Object state) {
		
		
		SudokuBoard board = (SudokuBoard) state;

		List<Successor> successors = new ArrayList<Successor>();

		for (int fila = 0; fila < board.getBoard().length; fila++) {
			for (int columna = 0; columna < board.getBoard().length; columna++) {
				if (board.getBoard()[fila][columna] == 0) {
					for (int k = 1; k <= 9; k++) {
						if (board.ubicarNumero(fila, columna, k)) {
							SudokuBoard newBoard = copyOf(board.getBoard());
							newBoard.setValue(fila, columna, k);
							successors.add(new Successor(newBoard));
							//newBoard.printBoard();
						}
					}
				}
			}

		}
		//if(contSucesores == 0){
		/*contSucesores += successors.size();
		System.out.printf("lista = %d\n", contSucesores);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//}//
		//board.printBoard();
		return successors;
	}

	/*
	 * if (board.canMoveGap(EightPuzzleBoard.UP)) { EightPuzzleBoard newBoard =
	 * copyOf(board); newBoard.moveGapUp(); successors.add(new
	 * Successor(EightPuzzleBoard.UP, newBoard)); }
	 */

	private SudokuBoard copyOf(int [][] board) {
		SudokuBoard newBoard = new SudokuBoard();
		newBoard.setBoard(newBoard, board);
		return newBoard;
	}

}