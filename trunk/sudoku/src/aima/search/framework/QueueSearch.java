/*
 * Created on Sep 8, 2004
 *
 */
package aima.search.framework;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import aima.search.sudoku.SudokuBoard;
import aima.search.uninformed.DepthFirstSearch;
/**
 * @author Ravi Mohan
 * 
 */

public abstract class QueueSearch extends NodeExpander {
	private static String QUEUE_SIZE = "queueSize";

	private static String MAX_QUEUE_SIZE = "maxQueueSize";

	private static String PATH_COST = "pathCost";

	public List<String> search(Problem problem, NodeStore fringe) {
		//List<int [][]> listaCerrada = new ArrayList<int [][]>();
		List<int [][]> listaCerrada = new ArrayList<int[][]>();
		
		//boolean cutoff = false;
		clearInstrumentation();
		fringe.add(new Node(problem.getInitialState()));
		setQueueSize(fringe.size());
		
		while (!(fringe.isEmpty())) {
			//cutoff = false;
			Node node =  fringe.remove();
			SudokuBoard board = (SudokuBoard) node.getState();
			
			//sacar esto
			System.gc(); 

			setQueueSize(fringe.size());
			/*Aqui controlamos la cantidad de evaluaciones que hace el algoritmo,
			 * es decir cada vez que pregunta "is a goal state?"               */
			DepthFirstSearch.cantEvaluaciones++;
			if (problem.isGoalState(node.getState())) {
				setPathCost(node.getPathCost());
				return SearchUtils.actionsFromNodes(node.getPathFromRoot());
			}
			
			//node.getState()
			//if(!cutoff){
				//listaCerrada.add(node);
				addExpandedNodesToFringe(fringe, node, problem, listaCerrada);
				
				
				
				
				setQueueSize(fringe.size());
				
			//}else{
				//System.out.println("optimizo");
			//}
			
			//System.out.printf("fringe = %d\n", fringe.size());
			
			
			
		}
		return new ArrayList<String>();// Empty List indicates Failure
	}

	@Override
	public void clearInstrumentation() {
		super.clearInstrumentation();
		metrics.set(QUEUE_SIZE, 0);
		metrics.set(MAX_QUEUE_SIZE, 0);
		metrics.set(PATH_COST, 0);
	}

	public int getQueueSize() {
		return metrics.getInt("queueSize");
	}

	public void setQueueSize(int queueSize) {

		metrics.set(QUEUE_SIZE, queueSize);
		int maxQSize = metrics.getInt(MAX_QUEUE_SIZE);
		if (queueSize > maxQSize) {
			metrics.set(MAX_QUEUE_SIZE, queueSize);
		}
	}

	public int getMaxQueueSize() {
		return metrics.getInt(MAX_QUEUE_SIZE);
	}

	public double getPathCost() {
		return metrics.getDouble(PATH_COST);
	}

	public void setPathCost(Double pathCost) {
		metrics.set(PATH_COST, pathCost);
	}

	public abstract void addExpandedNodesToFringe(NodeStore fringe, Node node,
			Problem p, List<int [][]> listaCerrada);
	
	
	
	
	
	
	
}