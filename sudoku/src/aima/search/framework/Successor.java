package aima.search.framework;

/**
 * @author Ravi Mohan
 * 
 */

public class Successor {

	private final String action;

	private final Object state;

	public Successor(String action, Object state) {
		this.action = action;
		this.state = state;
	}

	public Successor(Object state) {
		this.action = null;
		this.state = state;
	}

	public String getAction() {
		return action;
	}

	public Object getState() {
		return state;
	}
}