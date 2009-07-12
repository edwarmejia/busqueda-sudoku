package aima.search.demos;


import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import aima.search.framework.GraphSearch;
import aima.search.framework.TreeSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.ga.GeneticAlgorithm;
import aima.search.sudoku.FitnessHeuristicFunction;
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
			{0,0,6,0,9,8,4,0,0},
			{8,9,0,4,0,1,0,0,0},
			{0,1,5,0,3,0,9,0,0},
			{9,5,0,0,2,0,3,0,1},
			{0,7,0,0,0,0,0,8,0},
			{2,0,1,0,8,0,0,5,6},
			{0,0,4,0,7,0,6,9,0},
			{0,0,0,6,0,4,0,2,3},
			{0,0,2,3,1,0,8,0,0} } );
					

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
	
	public static int pasos = 0;
	public static SudokuBoard boardInicial;
	
	private static void newSudokuDemo() {
		SudokuBoard boardInicial = new SudokuBoard(SudokuFileParser.getBoard());
		//SudokuBoard boardInicial = new SudokuBoard(tableroComplejo.getBoard());

		Scanner input = new Scanner(System.in);
		System.out.print("Ingrese \n1:Algoritmo Genetico\n2:Algoritmo DFS: ");
		int opcion = input.nextInt();
		
		if(opcion == 1){
			System.out.print("Ingrese la cantidad maxima de poblacion inicial\n");
			int cantMaxPopulation = input.nextInt();		
			System.out.print("Ingrese la probabilidad de mutacion inicial(entre '0,0' - '1,0')\n");
			double probMutacion = input.nextDouble();	
			System.out.print("Ingrese el tamaño de la poblacion de elite(entre '0' - 'max poblacion inicial')\n");
			System.out.print("El recomendado es el 10% de la maxima poblacion inicial\n");
			int eliteNindividuos = input.nextInt();	
			
			sudokuAG(boardInicial, cantMaxPopulation, probMutacion, eliteNindividuos);			
		}else if(opcion == 2){
			sudokuWithDepthFirstSearch(boardInicial);
		}else if(opcion == 3){
			if(backtracking(boardInicial.getBoard()))
				boardInicial.printBoard();
			System.out.printf("pasos = %d \n", pasos);
		}else{
			System.out.print("Opcion invalida\n");
		}



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

	
	private static void sudokuAG(SudokuBoard board, int cantMaxPopulation, double probMutacion, int eliteNindividuos){
		System.out.println("\nSudokuDemo AG -->");
		long inicio = System.currentTimeMillis();
		try {
			int helpArray[] = new int [81];
			
			Set<Character> finiteAlphabet = new HashSet<Character>();
			finiteAlphabet.add('1'); finiteAlphabet.add('2');
			finiteAlphabet.add('3'); finiteAlphabet.add('4');
			finiteAlphabet.add('5'); finiteAlphabet.add('6');
			finiteAlphabet.add('7'); finiteAlphabet.add('8');
			finiteAlphabet.add('9');			
			
			helpArray = boardToArray(board);
		
			board.setCantMaxPopulation(cantMaxPopulation);
			
			GeneticAlgorithm search = new GeneticAlgorithm(81, 
										finiteAlphabet , probMutacion ,helpArray, board, cantMaxPopulation);

			String bestIndividual =  search.geneticAlgorithm(board.initPopulation(board), 
										new SudokuFitnessFunction(), new SudokuGoalTest(), eliteNindividuos);
			
			//System.out.printf("bestIndividual: %s", bestIndividual);
			long fin = System.currentTimeMillis();
			printBoard(GeneticAlgorithm.stringToBoard(bestIndividual));
			
//			System.out.printf("popMax : %d", SudokuBoard.calculatePopulationMax(newBoard.getBoard()));
			System.out.printf("\nLa O(n) temporal es: %d", 
					SudokuFitnessFunction.cantEvaluaciones);
			
			System.out.printf("\ntiempo de resolucion: %d\n", 
					(((fin - inicio)/1000)/60));
			
			
			
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
	  

	
	
	 private static void sudokuAStarDemo(SudokuBoard newBoard) { 
			 System.out.
			 println("\nSudokuPuzzleDemo AStar Search (3)-->");
			  try {
				  Problem problem = new Problem(newBoard, new
						  SudokuSuccessorFunction(), new SudokuGoalTest(), 
						  new  FitnessHeuristicFunction()); 
				  
				  Search search = new AStarSearch(new TreeSearch()); 
				  SearchAgent agent = new SearchAgent(problem, search);
				  
				  //printActions(agent.getActions());
				  //printInstrumentation(agent.getInstrumentation()); } 
			  }catch (Exception e) {
				  e.printStackTrace(); 
			  }

	 }
	
	
	 private static boolean backtracking(int board[][])
	 {
	 	int i,j,k;
	 	boolean oiko;

	 	for(i = 0; i < board.length; i++){
	 		for(j = 0; j < board.length; j++){
	 			if(board[i][j] != 0){
	 				continue;
	 			}
	 			for(k = 1; k <= board.length; k++){
	 					if(ubicarNumero(board, i, j, k)){
	 						board[i][j] = k;
	 						pasos +=1;
	 						printTablero(board);
	 						oiko = backtracking(board);
	                         if(oiko){
	 							return true;
	 						}
	                         board[i][j] = 0;
	 					}
	 			}
	 			return false;
	 		}
	 	}

	 	return true;



	 }
	
		public static boolean ubicarNumero(int board[][], int fila, int columna, int k) {
			if (board[fila][columna] == 0) {
				if (chequearFilaColumna(board, fila, columna, k)
						&& chequearRegion(board, fila, columna, k))
					return true;
			}
			return false;

		}
		public static boolean chequearRegion(int [][] board, int fila, int columna, int k) {
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

		/* Identifica la region a la que pertenecen dicha fila/columna */
		private static int[] identificarRegion(int fila, int columna) {
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
		private static boolean chequearFilaColumna(int board[][], int fila, int columna, int k) {

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
	
		public static void printTablero(int board[][]) {
			System.out.println("\n ----------------- ");
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					System.out.print(' ');
					System.out.print(board[i][j]);
				}
				System.out.print('\n');
			}

		}
	
}
