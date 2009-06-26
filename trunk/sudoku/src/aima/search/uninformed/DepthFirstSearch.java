package aima.search.uninformed;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Metrics;
import aima.search.framework.Problem;
import aima.search.framework.QueueSearch;
import aima.search.framework.Search;
import aima.search.nodestore.LIFONodeStore;

/**
 * Artificial Intelligence A Modern Approach (2nd Edition): page 75.
 * 
 * Depth-first search.
 */
public class DepthFirstSearch implements Search {

	QueueSearch search;

	public static int cantEvaluaciones;
	
	public DepthFirstSearch(QueueSearch search) {
		this.search = search;
		this.cantEvaluaciones = 0;

	}

	public List search(Problem p) {

		return search.search(p, new LIFONodeStore());
	}
	//wsdkfaslkfdhaklsjdhflahslfhasdklj
	//@Override
	public Metrics getMetrics() {
		return search.getMetrics();
	}
	

	

}