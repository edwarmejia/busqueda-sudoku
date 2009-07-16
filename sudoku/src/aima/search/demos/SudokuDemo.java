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
import aima.search.informed.BacktrackingSearch;
import aima.search.informed.ga.GeneticAlgorithm;
import aima.search.sudoku.SudokuBoard;
import aima.search.sudoku.SudokuFitnessFunction;
import aima.search.sudoku.SudokuGoalTest;
import aima.search.sudoku.SudokuSuccessorFunction;
import aima.search.sudoku.SudokuFileParser;
import aima.search.uninformed.DepthFirstSearch;
import aima.search.uninformed.IterativeDeepeningSearch;
import aima.search.informed.ga.FitnessFunction;

import java.awt.*; //para el beep nomas


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
	
	public static SudokuBoard boardInicial;
	
	private static void newSudokuDemo() {
		SudokuBoard boardInicial = new SudokuBoard(SudokuFileParser.getBoard());
		//SudokuBoard boardInicial = new SudokuBoard(tableroComplejo.getBoard());

		Scanner input = new Scanner(System.in);
		System.out.print("Ingrese \n1:Algoritmo Genetico\n2:Algoritmo Backtracking: ");
		int opcion = input.nextInt();
		
		if(opcion == 1){
			System.out.print("Ingrese la cantidad maxima de poblacion inicial\n");
			int cantMaxPopulation = input.nextInt();		
			System.out.print("Ingrese la probabilidad de mutacion inicial(entre '0,0' - '1,0')\n");
			double probMutacion = input.nextDouble();	
			System.out.print("Ingrese el tamaño de la poblacion de elite(entre '0' - 'max poblacion inicial')\n");
			System.out.print("El recomendado es el 25% de la maxima poblacion inicial\n");
			int eliteNindividuos = input.nextInt();	
			
			sudokuAG(boardInicial, cantMaxPopulation, probMutacion, eliteNindividuos);
			
		}else if(opcion == 2){
			sudokuBacktrackingDemo(boardInicial);
			System.out.printf("pasos = %d \n", BacktrackingSearch.pasos);
		}else if(opcion == 3){
			sudokuWithDepthFirstSearch(boardInicial);
		}else{
			System.out.print("Opcion invalida\n");
		}
		/*Beep's para anunciar que termino el algoritmo*/
		Toolkit.getDefaultToolkit().beep();
		Toolkit.getDefaultToolkit().beep();
		Toolkit.getDefaultToolkit().beep();
		Toolkit.getDefaultToolkit().beep();
		Toolkit.getDefaultToolkit().beep();
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
			
			long fin = System.currentTimeMillis();
			printBoard(GeneticAlgorithm.stringToBoard(bestIndividual));
			
//			System.out.printf("popMax : %d", SudokuBoard.calculatePopulationMax(newBoard.getBoard()));
			System.out.printf("\nLa O(n) temporal es: %d", 
					SudokuFitnessFunction.cantEvaluaciones);
			
			System.out.printf("\ntiempo de resolucion: %d\n", 
					(((fin - inicio)/1000)/60));
			
			System.out.printf("La cantidad de generaciones fue: %f", search.getIterations());;			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	 private static void sudokuBacktrackingDemo(SudokuBoard board) { 
		 System.out.
		 println("\nSudokuPuzzleDemo Backtracking Search ()-->");
		  try {
			  BacktrackingSearch backtracking = new BacktrackingSearch();
			  
			  if( !backtracking.search(board.getBoard()) )
				  System.out.println("\nNo se pudo resolver por este metodo");

		  }catch (Exception e) {
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
	  

	
}
