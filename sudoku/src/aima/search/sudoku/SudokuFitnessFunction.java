package aima.search.sudoku;

import aima.search.informed.ga.FitnessFunction;

public class SudokuFitnessFunction implements FitnessFunction{

	public static SudokuBoard board = new SudokuBoard();

	
	public double getValue(String individual){
		
		double fitness;
		int valor_filas_y_columnas, valor_columnas, valor_faltantes, fitness_int;
		
		
		stringABorard(individual);// se convierte el string a board	
		valor_filas_y_columnas = filas_y_columnas();
		valor_faltantes = numeros_faltantes();
		fitness_int = valor_faltantes + valor_filas_y_columnas;
		
		fitness  = (double)fitness_int;
		
		
		//System.out.printf("valor : %d",valor_faltantes);
		//System.out.printf("valor : %f",fitness);
		
		
		return fitness;
		
		
	}
	
	//convierte el string al tablero de sudoku
	private static void stringABorard(String individual){
		

		int contador_cadena = 0; 
		int columna, fila;
		char vector_char[] = new char [81];
		
		
		vector_char = individual.toCharArray();

		
		for (int i = 0 ; i < 9 ;  i=i+3)
			for (int j = 0 ; j < 9 ;  j=j+3)
				for (fila = i ; fila < i+3 ; fila++)
					for (columna = j ; columna < j+3 ; columna++){
						board.board[fila][columna] = Character.getNumericValue(vector_char[contador_cadena]);
						contador_cadena++;
					}
				
				
			//	board.printBoard();
		
	}
	
	
	private static int filas_y_columnas(){
		
		int acum = 0;
		int suma_fila = 0;
		int suma_columna = 0;
		
		for (int i = 0 ; i < 9 ; i++ ){
			for (int j = 0 ; j < 9 ; j++ ){
				suma_fila = suma_fila + board.board[i][j];
			}
			if (suma_fila < 45)
				acum = acum + (45 - suma_fila);
			else
				acum = acum + (suma_fila - 45);
			
			suma_fila = 0;
		}
				
		for (int i = 0 ; i < 9 ; i++ ){
			for (int j = 0 ; j < 9 ; j++ ){
				suma_columna = suma_columna + board.board[j][i];
			}
			if (suma_columna < 45)
				acum = acum + (45 - suma_columna);
			else
				acum = acum + (suma_columna - 45);
			
			suma_columna = 0;
		}
				
		
		return acum;
	}
	
	private static int numeros_faltantes(){
		
		int conjunto_valores[] = {1,2,3,4,5,6,7,8,9};
		int faltantes = 0;
		
		for (int i = 0 ; i < 9 ; i++ ){//recorre cada fila
			for (int j = 0 ; j < 9 ; j++ ){
				// borra del conjunto de valores el valor que encuentra en la fila
				conjunto_valores[board.board[i][j] - 1] = 0;
			}
			for (int k = 0 ; k < 9 ; k++){
				if (conjunto_valores[k] != 0)
					faltantes++;
			}
			for (int k = 0 ; k < 9 ; k++)
				conjunto_valores[k] = k + 1;
				
		}		
		
		for (int i = 0 ; i < 9 ; i++ ){//recorre cada columna
			for (int j = 0 ; j < 9 ; j++ ){
				// borra del conjunto de valores el valor que encuentra en la columna
				conjunto_valores[board.board[j][i] - 1] = 0;
			}
			for (int k = 0 ; k < 9 ; k++){
				if (conjunto_valores[k] != 0)
					faltantes++;
			}
			for (int k = 0 ; k < 9 ; k++)
				conjunto_valores[k] = k + 1;
				
		}		
	
		
		return faltantes;
	}
	
	
	public static void main(String[] args){
		
		String prueba = "123456789123456789123456789123456789123456789123456789123456789123456789123456789";
		SudokuFitnessFunction prueba2 = new SudokuFitnessFunction();
		
		prueba2.getValue(prueba);
		
		//stringABorard(prueba);
		
		
	}
	
}


