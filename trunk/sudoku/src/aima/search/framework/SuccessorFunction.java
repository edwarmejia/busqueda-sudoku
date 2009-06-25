package aima.search.framework;

import java.util.ArrayList;
import java.util.List;

import aima.search.sudoku.SudokuBoard;

/**
 * @author Ravi Mohan
 * 
 */

public interface SuccessorFunction {

	List<Successor> getSuccessors(Object state);

}