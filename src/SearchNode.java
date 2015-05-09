import java.util.ArrayList;


public class SearchNode<V> {
	private V state;
	private ArrayList<V> predecessors;

	public SearchNode(V state, ArrayList<V> pred) {
		this.state = state;
		this.predecessors = pred;
		
	}
	
	public V getName() {
		return this.state;
	} 
	
	public ArrayList<V> getPred() {
		return this.predecessors;
	}
	
	
}
