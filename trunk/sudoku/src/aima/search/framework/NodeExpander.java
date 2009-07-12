package aima.search.framework;

import java.util.ArrayList;
import java.util.List;

import aima.search.sudoku.SudokuBoard;

/**
 * @author Ravi Mohan
 * 
 */

public class NodeExpander {
	protected Metrics metrics;

	protected static String NODES_EXPANDED = "nodesExpanded";

	//public static List<int [][]> listaCerrada = new ArrayList<int[][]>();
	
	public NodeExpander() {
		metrics = new Metrics();
	}

	public void clearInstrumentation() {
		metrics.set(NODES_EXPANDED, 0);
	}

	public List<Node> expandNode(Node node, Problem problem) {
		//boolean listaCerradaContiene = false;
		List<Node> nodes = new ArrayList<Node>();
		
		//SudokuBoard board = (SudokuBoard)node.getState();
		
	
		List successors = problem.getSuccessorFunction().getSuccessors(
				node.getState());
		
		for (int i = 0; i < successors.size(); i++) {
			Successor successor = (Successor) successors.get(i);
			Node aNode = new Node(node, successor.getState());
			aNode.setAction(successor.getAction());
			Double stepCost = problem.getStepCostFunction().calculateStepCost(
					node.getState(), successor.getState(),
					successor.getAction());
			aNode.setStepCost(stepCost);
			aNode.addToPathCost(stepCost);
			
			//SudokuBoard boardCompare = (SudokuBoard) aNode.getState();
			
			//if(!listaCerrada.contains(boardCompare.getBoard()))
			nodes.add(aNode);

		}
		metrics.set(NODES_EXPANDED, metrics.getInt(NODES_EXPANDED) + 1);

		
		return nodes;
	}
	
	private boolean equalsBoard(int [][]board, int [][] boardCompare){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				if(board[i][j] != boardCompare[i][j])
					return false;
			}
		}
		return true;
		
	}
	public List<Node> expandNode(Node node, Problem problem, List<int [][]> listaCerrada) {
		boolean listaCerradaContiene = false;
		List<Node> nodes = new ArrayList<Node>();
		
		SudokuBoard board = (SudokuBoard)node.getState();
		
		for(int k = 0; k < listaCerrada.size(); k++){
			if(equalsBoard(listaCerrada.get(k), board.getBoard())){
				listaCerradaContiene = true;
				break;
			}
			/*if(listaCerrada.get(k).equals(board.getBoard())){
				listaCerradaContiene = true;
				break;
			}*/
			//System.out.printf("%d",listaCerrada.size());
		}
		
		if(!listaCerradaContiene){
			listaCerrada.add(board.getBoard());
		
			List successors = problem.getSuccessorFunction().getSuccessors(
					node.getState());
			
			for (int i = 0; i < successors.size(); i++) {
				Successor successor = (Successor) successors.get(i);
				Node aNode = new Node(node, successor.getState());
				aNode.setAction(successor.getAction());
				Double stepCost = problem.getStepCostFunction().calculateStepCost(
						node.getState(), successor.getState(),
						successor.getAction());
				aNode.setStepCost(stepCost);
				aNode.addToPathCost(stepCost);
				
				//SudokuBoard boardCompare = (SudokuBoard) aNode.getState();
				
				//if(!2.contains(boardCompare.getBoard()))
					nodes.add(aNode);
	
			}
			
		}else{
			System.out.printf("p = %d \n", node.getDepth());	
		}
		metrics.set(NODES_EXPANDED, metrics.getInt(NODES_EXPANDED) + 1);
		
		
		return nodes;
	}
	
	
	
	
	
	

	public int getNodesExpanded() {
		return metrics.getInt(NODES_EXPANDED);
	}

	public void setNodesExpanded(int nodesExpanded) {
		metrics.set(NODES_EXPANDED, nodesExpanded);
	}

	public Object getSearchMetric(String name) {
		return metrics.get(name);
	}

	public Metrics getMetrics() {
		return metrics;
	}
}