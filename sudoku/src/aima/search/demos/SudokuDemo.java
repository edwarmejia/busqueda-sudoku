package aima.search.demos;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import aima.search.framework.GraphSearch;
import aima.search.framework.TreeSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.ga.GeneticAlgorithm;
import aima.search.sudoku.EmptyBoxHeuristicFunction;
import aima.search.sudoku.SudokuBoard;
import aima.search.sudoku.SudokuFitnessFunction;
import aima.search.sudoku.SudokuGoalTest;
import aima.search.sudoku.SudokuSuccessorFunction;
import aima.search.sudoku.SudokuFileParser;
import aima.search.uninformed.DepthFirstSearch;
import aima.search.uninformed.IterativeDeepeningSearch;
import aima.search.informed.ga.FitnessFunction;



/*
 * 	static SudokuBoard tableroComplejo = new SudokuBoard(new int [][]{
		{0,0,0,0,0,6,7,8,9 }, 
		{4,5,6,0,0,9,1,2,3 },
		{0,8,9,1,2,3,4,5,6 }, 
		{0,0,4,3,6,5,8,9,7 },
		{0,6,5,8,9,7,2,1,4 },
		{8,9,0,2,1,4,3,6,5 },
		{5,0,1,6,4,2,9,7,8 },
		{6,4,2,9,7,8,5,3,1 },//aca poner un 7
		{9,0,8,5,0,1,6,4,2 } } );

		
new int [][]{
			{0,0,0,0,0,6,7,8,9 },
			{4,5,6,0,0,9,1,2,3 },
			{0,8,9,1,2,3,4,5,6 },
			{0,0,4,3,6,5,8,9,7 },
			{0,6,5,8,9,7,2,1,4 },
			{8,9,0,2,1,4,3,6,5 },
			{5,0,1,6,4,2,9,7,8 },
			{6,4,2,9,7,8,5,3,1 },//aca poner un 7
			{9,0,0,0,0,1,6,4,2 } } );
					



//TABLERO UNO QUE ENTREGO EL PROF
{0,0,6,0,9,8,4,0,0},
{8,9,0,4,0,1,0,0,0},
{0,1,5,0,3,0,9,0,0},
{9,5,0,0,2,0,3,0,1},
{0,7,0,0,0,0,0,8,0},
{2,0,1,0,8,0,0,5,6},
{0,0,4,0,7,0,6,9,0},
{0,0,0,6,0,4,0,2,3},
{0,0,2,3,1,0,8,0,0} ;






resuelto

			{1,2,3,4,5,6,7,8,9 },
			{4,5,6,7,8,9,1,2,3 },
			{7,8,9,1,2,3,4,5,6 },
			{2,3,1,6,7,4,8,9,5 },
			{8,7,5,9,1,2,3,6,4 },
			{6,9,4,5,3,8,2,1,7 },
			{3,1,7,2,6,5,9,4,8 },
			{5,4,2,8,9,7,6,3,1 },//aca poner un 7
			{9,6,8,3,4,1,5,7,2 } } );


muuuuchos ceroos ya - resuelve
new int [][]{
			{0,0,0,0,0,6,7,8,0 },
			{0,0,6,0,0,0,1,2,0 },
			{0,0,9,0,2,3,0,5,0 },
			{0,0,4,0,6,0,8,9,0 },
			{0,6,5,8,9,0,2,1,0 },
			{8,0,0,2,1,4,3,6,0 },
			{0,0,0,0,4,2,9,7,0 },
			{0,0,0,0,0,8,5,3,0 },//aca poner un 7
			{0,0,0,0,0,1,0,0,0 } } );
			
			
			
			
ESTE TIENE QUE RESOLVER SI O SI
new int [][]{
			{0,0,0,3,6,0,5,0,0},
			{3,0,5,8,0,0,0,0,4},
			{1,0,4,7,0,5,2,0,0},
			{4,1,0,0,2,8,7,0,0},
			{0,0,0,4,3,0,0,8,9},
			{0,3,0,0,5,0,0,0,0},
			{6,0,8,0,0,0,3,0,2},
			{9,2,0,6,4,3,8,0,7},
			{7,5,0,0,8,0,6,0,0}  });			
					
					
		
 */


public class SudokuDemo {

	static SudokuBoard tableroComplejo = new SudokuBoard(new int [][]{
			{0,0,0,0,0,6,7,8,0 },
			{0,0,6,0,0,0,1,2,0 },
			{0,0,9,0,2,3,0,5,0 },
			{0,0,4,0,6,0,8,9,0 },
			{0,6,5,8,9,0,2,1,0 },
			{8,0,0,2,1,4,3,6,0 },
			{0,0,0,0,4,2,9,7,0 },
			{0,0,0,0,7,8,5,3,0 },//aca poner un 7
			{0,0,0,0,0,1,0,0,0 } } );
			
					
					
	

	static SudokuBoard tableroSencillo = new SudokuBoard(new int[][] {
			{0,0,6,0,9,8,4,0,0},
			{8,9,0,4,0,1,0,0,0},
			{0,1,5,0,3,0,9,0,0},
			{9,5,0,0,2,0,3,0,1},
			{0,7,0,0,0,0,0,8,0},
			{2,0,1,0,8,0,0,5,6},
			{0,0,4,0,7,0,6,9,0},
			{0,0,0,6,0,4,0,2,3},
			{0,0,2,3,1,0,8,0,0} });

	public static void main(String[] args) {

		newSudokuDemo();
	}

	private static void newSudokuDemo() {
		SudokuBoard board = new SudokuBoard(SudokuFileParser.getBoard());
		//SudokuBoard board = new SudokuBoard(tableroComplejo.getBoard());
		//board.printBoard();
		sudokuWithDepthFirstSearch(board);
		//sudokuAG(board);

	}

	private static void sudokuWithDepthFirstSearch(SudokuBoard newBoard) {
		System.out.println("\nSudokuDemo DFS -->");
		try {

			Problem problem = new Problem(newBoard,
					new SudokuSuccessorFunction(), new SudokuGoalTest());
			Search search = new DepthFirstSearch(new TreeSearch());
			SearchAgent agent = new SearchAgent(problem, search);

			System.out.printf("\nLa O(n) temporal es: %d", 
								DepthFirstSearch.cantEvaluaciones);
			
			// printActions(agent.getActions());
			// printBoard(newBoard);
			// printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static void sudokuAG(SudokuBoard newBoard){
		System.out.println("\nSudokuDemo AG -->");
		try {
			int helpArray[] = new int [81];
			
			Set<Character> finiteAlphabet = new HashSet<Character>();
			finiteAlphabet.add('1'); finiteAlphabet.add('2');
			finiteAlphabet.add('3'); finiteAlphabet.add('4');
			finiteAlphabet.add('5'); finiteAlphabet.add('6');
			finiteAlphabet.add('7'); finiteAlphabet.add('8');
			finiteAlphabet.add('9');			
			


			helpArray = boardToArray(newBoard);
			
			GeneticAlgorithm search = new GeneticAlgorithm(81, 
										finiteAlphabet , 0.50 ,helpArray, newBoard);

			String bestIndividual =  search.geneticAlgorithm(newBoard.initPopulation(newBoard), 
										new SudokuFitnessFunction(), new SudokuGoalTest());
			
			//System.out.printf("bestIndividual: %s", bestIndividual);
			
			printBoard(GeneticAlgorithm.stringToBoard(bestIndividual));
			
//			System.out.printf("popMax : %d", SudokuBoard.calculatePopulationMax(newBoard.getBoard()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void printBoard(int [][] board) {
		System.out.println("\n ----------------- ");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(' ');
				System.out.print(board[i][j]);
			}
			System.out.print('\n');
		}

	}
	
	
    public static int[] boardToArray(SudokuBoard board){
            
            int array[] = new int[81];
            int contador = 0;
            int fila, columna;
            
            for (int i = 0 ; i < 9 ;  i=i+3)
                    for (int j = 0 ; j < 9 ;  j=j+3)
                            for (fila = i ; fila < i+3 ; fila++)
                                    for (columna = j ; columna < j+3 ; columna++){
                                            array[contador] = board.board[fila][columna];
                                            contador++;
                                    }
            
            return array;
            
    }
	  

	
	
	
	
	/*private static void sudokuIDLSDemo(SudokuBoard tablero,
			boolean tipo_lista_cerrada) {
	
		if (tipo_lista_cerrada)
			System.out
			.println("\n SudokuDemo Iterative DLS con listas cerradas -->");			
		else
			System.out.println("\n SudokuDemo Iterative DLS -->");			
	
		try {
			Problem problem = new Problem(tablero,
					new SudokuSuccessorFunction(),
					new SudokuGoalTest());
			Search search = new IterativeDeepeningSearch(tipo_lista_cerrada);
	
			SearchAgent agent = new SearchAgent(problem, search);
			String resultado = agent.getInstrumentation().getProperty(
					"pathCost");
			//if (!resultado.equals("0"))
				//porcentaje_IDLS++;
	
			 //printActions(agent.getActions());
			 //printInstrumentation(agent.getInstrumentation());
			 //Instrumentation = new 
			// System.out.println("Final State=\n" +
			//search.getLastSearchState());
	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
}
