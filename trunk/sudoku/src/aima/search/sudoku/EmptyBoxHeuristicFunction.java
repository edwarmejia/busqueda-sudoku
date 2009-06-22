package aima.search.sudoku;

import aima.search.framework.HeuristicFunction;

public class EmptyBoxHeuristicFunction implements HeuristicFunction {
	public double getHeuristicValue(Object state) {
		SudokuBoard board = (SudokuBoard) state;

		return getNumberOfEmptyBoxs(board);

	}

	private int getNumberOfEmptyBoxs(SudokuBoard board) {
		int numberOfEmptyBoxs = 0;

		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard().length; j++) {
				if (board.getBoard()[i][j] == 0)
					numberOfEmptyBoxs++;
			}
		}

		return numberOfEmptyBoxs;
	}

}
