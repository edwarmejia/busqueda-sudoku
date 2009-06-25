package aima.search.sudoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Node;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class SudokuSuccessorFunction implements SuccessorFunction {
	
	static int contSucesores = 0;
	
	
	public List<Successor> getSuccessors(Object state) {
		
		
		SudokuBoard board = (SudokuBoard) state;

		List<Successor> successors = new ArrayList<Successor>();
		boolean coloco = false;
		
		for (int fila = 0; fila < board.getBoard().length; fila++) {
			for (int columna = 0; columna < board.getBoard().length; columna++) {
				if (board.getBoard()[fila][columna] == 0) {
					coloco = false;
					for (int k = 1; k <= 9; k++) {
						if (board.ubicarNumero(fila, columna, k)) {
							coloco = true;
							SudokuBoard newBoard = copyOf(board.getBoard());
							newBoard.setValue(fila, columna, k);
							//if(!listaCerrada.contains(newBoard.getBoard()))
							successors.add(new Successor(newBoard));
							
							//newBoard.printBoard();
						}else{
							if(k == 9 && !coloco){
								successors.removeAll(successors);
								return new ArrayList<Successor>();
							}
						}
					}
				}
			}

		}
		//if(contSucesores == 0){
			//contSucesores += successors.size();
			//System.out.printf("lista = %d\n", successors.size());
			if(successors.size() == 0){
				System.out.println("paro\n");
				//board.printBoard();
				//listaCerrada.add(board.getBoard());
				/*try {
					System.in.read();
				} catch (IOException e) {
					// 	TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		//}
		//}//
		//board.printBoard();
		return successors;
	}

	/*
	 * if (board.canMoveGap(EightPuzzleBoard.UP)) { EightPuzzleBoard newBoard =
	 * copyOf(board); newBoard.moveGapUp(); successors.add(new
	 * Successor(EightPuzzleBoard.UP, newBoard)); }
	 */
	//ESTE HAY QUE SACAR DE ACA SI O SI, YA ESTA EN LA CLASE SUDOKUBOARD
	private SudokuBoard copyOf(int [][] board) {
		SudokuBoard newBoard = new SudokuBoard();
		newBoard.setBoard(newBoard, board);
		return newBoard;
	}

}