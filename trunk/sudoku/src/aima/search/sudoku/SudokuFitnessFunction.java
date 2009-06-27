package aima.search.sudoku;

import aima.search.informed.ga.FitnessFunction;



//FITNESS FUNCTION PARA SUDOKU
public class SudokuFitnessFunction implements FitnessFunction{

       public static SudokuBoard board = new SudokuBoard();


       public double getValue(String individual){

               double fitness;
 
               stringToBoard(individual);// se convierte el string a board
               
               //valor_faltantes = numerosFaltantes();
/*             fitness_int = 20000000 -(valor_filas_columnas2 + valor_faltantes +
				valor_filas_y_columnas)*(+1);*/
               
               fitness = 20000000 - 10*(sumFilasColumnas() + multFilasColumnas() +
            		   			50*numerosFaltantes());

               //fitness  = (double)fitness_int;

               //if (fitness_int < 0)
                       //System.out.println("ooooooooooooooooooooooooooooooooop");
               //System.out.println(fitness_int);
               //System.out.printf("valor : %d",valor_faltantes);
               //System.out.printf("valor : %f",fitness);


               return fitness;


       }



       private static double sumFilasColumnas(){
               double acum = 0;
               double sumaFila = 0;
               double sumaColumna = 0;

               for (int i = 0 ; i < 9 ; i++ ){
                   	   sumaFila = 0;
            	   	   for (int j = 0 ; j < 9 ; j++ ){
            	   		   sumaFila = sumaFila + board.board[i][j];
                       }
            	   	   acum += Math.abs(45 - sumaFila);
               }

               for (int i = 0 ; i < 9 ; i++ ){
                       sumaColumna = 0;
                   	   for (int j = 0 ; j < 9 ; j++ ){
                    	   sumaColumna = sumaColumna + board.board[j][i];
                       }
                   	   
                   	   acum += Math.abs(45 - sumaColumna);
               }

               return acum;
       }


       private static double multFilasColumnas(){
               double acum = 0;
               double multFila = 1;
               double multColumna = 1;

               for (int i = 0 ; i < 9 ; i++ ){
            	   	   multFila = 1;    
            	       for (int j = 0 ; j < 9 ; j++ ){
                    	   multFila = multFila*board.board[i][j];
                       }

            	       acum += Math.sqrt(Math.abs(362880.0 - multFila));
               }

               for (int i = 0 ; i < 9 ; i++ ){
                   	   multColumna = 1;
                   	   for (int j = 0 ; j < 9 ; j++ ){
                    	   multColumna = multColumna*board.board[j][i];
                       }
                   	   
                   	   acum += Math.sqrt(Math.abs(362880.0 - multColumna));

               }

               return acum;
       }

       private static int numerosFaltantes(){

               int conjuntoValores[] = {1,2,3,4,5,6,7,8,9};
               int faltantes = 0;

               for (int i = 0 ; i < 9 ; i++ ){//recorre cada fila
                       for (int j = 0 ; j < 9 ; j++ ){
                               // borra del conjunto de valores el valor que encuentra en la fila
                    	   conjuntoValores[board.board[i][j] - 1] = 0;
                       }
                       for (int k = 0 ; k < 9 ; k++){
                               if (conjuntoValores[k] != 0)
                                       faltantes++;
                       }
                       for (int k = 0 ; k < 9 ; k++)
                    	   conjuntoValores[k] = k + 1;

               }

               for (int i = 0 ; i < 9 ; i++ ){//recorre cada columna
                       for (int j = 0 ; j < 9 ; j++ ){
                               // borra del conjunto de valores el valor que encuentra en la columna
                    	   	   conjuntoValores[board.board[j][i] - 1] = 0;
                       }
                       for (int k = 0 ; k < 9 ; k++){
                               if (conjuntoValores[k] != 0)
                                       faltantes++;
                       }
                       for (int k = 0 ; k < 9 ; k++)
                    	   conjuntoValores[k] = k + 1;

               }


               return faltantes;
       }

       //convierte el string al tablero de sudoku
       private static void stringToBoard(String individual){


               int contador_cadena = 0;
               int columna, fila;
               char vector_char[] = new char [81];

               vector_char = individual.toCharArray();

               for (int i = 0 ; i < 9 ;  i=i+3)
                       for (int j = 0 ; j < 9 ;  j=j+3)
                               for (fila = i ; fila < i+3 ; fila++)
                                       for (columna = j ; columna < j+3 ; columna++){
                                               board.board[fila][columna] =
                                            	   Character.getNumericValue(vector_char[contador_cadena]);
                                               contador_cadena++;
                                       }


                       //      board.printBoard();

       }

       
       
      /*public static void main(String[] args){

               String prueba =
"123456789123456789123456789123456789123456789123456789123456789123456789123456789";
               SudokuFitnessFunction prueba2 = new SudokuFitnessFunction();

               System.out.printf("%d", prueba2.getValue(prueba));



       }*/

}