package aima.search.demos;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.sudoku.EmptyBoxHeuristicFunction;
import aima.search.sudoku.SudokuBoard;
import aima.search.sudoku.SudokuGoalTest;
import aima.search.sudoku.SudokuSuccessorFunction;
import aima.search.uninformed.DepthFirstSearch;

public class SudokuDemo {

	static SudokuBoard tableroComplejo = new SudokuBoard(new int[][] {
			{ 0, 0, 3, 4, 5, 6, 7, 8, 0 },
			{ 4, 5, 0, 7,8,9,1,2,3 },
			{ 7, 0, 0, 1,2,0,4,5,6},
			{ 0, 0, 0,3,6,5,8,0,7 },
			{ 3, 0, 5,8,9,7,0,1,4 },
			{ 8, 0, 7,0,1,4,0,6,5},
			{ 5, 0, 1,0,4,2,0,7,8},
			{ 6, 0, 2,0,7,0,0,0,1 },
			{ 9, 0, 8,0,3,1,0,4,2 } });


	
	
	static SudokuBoard tableroSencillo = new SudokuBoard(new int[][] {
			{ 0, 0, 6, 0, 9, 8, 4, 0, 0 }, 
			{ 8, 9, 0, 4, 0, 1, 0, 0, 0 },
			{ 0, 1, 5, 0, 3, 0, 9, 0, 0 },
			{ 9, 5, 0, 0, 2, 0, 3, 0, 1 },
			{ 0, 7, 0, 0, 0, 0, 0, 8, 0 },
			{ 2, 0, 1, 0, 8, 0, 0, 5, 6 },
			{ 0, 0, 4, 0, 7, 0, 6, 9, 0 },
			{ 0, 0, 0, 6, 0, 4, 0, 2, 3 },
			{ 0, 0, 2, 3, 1, 0, 8, 0, 0 } });

	public static void main(String[] args) {

		newSudokuDemo();
	}

	private static void newSudokuDemo() {
		//sudokuWithDepthFirstSearch();
		sudokuAStarDemo();
		// if(tableroComplejo)

		// printBoard(tableroComplejo);

	}

	private static void sudokuWithDepthFirstSearch() {
		System.out.println("\nSudokuDemo DFS -->");
		try {
			SudokuBoard newBoard = new SudokuBoard(tableroComplejo.getBoard());

			Problem problem = new Problem(newBoard,
					new SudokuSuccessorFunction(), new SudokuGoalTest());
			Search search = new DepthFirstSearch(new GraphSearch());
			SearchAgent agent = new SearchAgent(problem, search);

			// printActions(agent.getActions());
			// printBoard(newBoard);

			// printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void sudokuAStarDemo() {
		System.out.println("\nSudokuDemo AStar -->");
		try {
			SudokuBoard newBoard = new SudokuBoard(tableroComplejo.getBoard());
			Problem problem = new Problem(newBoard,
					new SudokuSuccessorFunction(), new SudokuGoalTest(),
					new EmptyBoxHeuristicFunction());

			Search search = new AStarSearch(new GraphSearch());
			SearchAgent agent = new SearchAgent(problem, search);
			// printActions(agent.getActions());
			// printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
