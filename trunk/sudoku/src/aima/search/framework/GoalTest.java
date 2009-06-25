package aima.search.framework;

/**
 * @author Ravi Mohan
 * 
 */

public interface GoalTest {
	boolean isGoalState(Object state);
	boolean isGoalBoard(int [][] boardCheck);

}