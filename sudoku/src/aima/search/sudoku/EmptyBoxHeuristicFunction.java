package aima.search.sudoku;

import aima.search.framework.HeuristicFunction;

public class EmptyBoxHeuristicFunction implements HeuristicFunction {
	public double getHeuristicValue(Object state) {
		SudokuBoard board = (SudokuBoard) state;

		return h(board);

	}

	private double h(SudokuBoard board) {
		double valorH = 0;
		double emptySlots = 0;
		
		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard().length; j++) {
				if (board.getBoard()[i][j] == 0){
					emptySlots++;
					for(int k = 0; k < board.getBoard().length; k++){
						if(board.ubicarNumero(i, j, k)){
							valorH++;
						}
					}
					
				}
			}
		}

		return valorH + emptySlots;
	}

}
