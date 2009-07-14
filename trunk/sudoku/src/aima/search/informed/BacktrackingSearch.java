package aima.search.informed;

public class BacktrackingSearch {
	 public static int pasos = 0;
	
	 public boolean search(int board[][])
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
	 						oiko = search(board);
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
	
		private boolean ubicarNumero(int board[][], int fila, int columna, int k) {
			if (board[fila][columna] == 0) {
				if (chequearFilaColumna(board, fila, columna, k)
						&& chequearRegion(board, fila, columna, k))
					return true;
			}
			return false;

		}
		
		private boolean chequearRegion(int [][] board, int fila, int columna, int k) {
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
		private int[] identificarRegion(int fila, int columna) {
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
		private boolean chequearFilaColumna(int board[][], int fila, int columna, int k) {

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
		
		private void printTablero(int board[][]) {
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
