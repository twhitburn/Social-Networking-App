import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class SocialGraph extends UndirectedGraph<String> {

	/**
	 * Creates an empty social graph.
	 * 
	 * DO NOT MODIFY THIS CONSTRUCTOR.
	 */
	//declare  variables for getPath method
	private ArrayList<String> path;
	private Set<String> visited;
	private ArrayList<String> frontier;
	
	public SocialGraph() {
		super();
	}

	/**
	 * Creates a graph from a preconstructed hashmap.
	 * This method will be used to test your submissions. You will not find this
	 * in a regular ADT.
	 * 
	 * DO NOT MODIFY THIS CONSTRUCTOR.
	 * DO NOT CALL THIS CONSTRUCTOR ANYWHERE IN YOUR SUBMISSION.
	 * 
	 * @param hashmap adjacency lists representation of a graph that has no
	 * loops and is not a multigraph.
	 */
	public SocialGraph(HashMap<String, ArrayList<String>> hashmap) {
		super(hashmap);
	}

	public Set<String> friendsOfFriends(String person) {
		//TODO
		//check that the person exists
		if (!this.getAllVertices().contains(person)) {
			throw new IllegalArgumentException();
		}
		Set<String> fof = new HashSet<String>();
		Set<String> friends = new HashSet<String>();
		friends = this.getNeighbors(person);
		Iterator<String> itr = friends.iterator();
		while(itr.hasNext()) {
			Iterator<String> itr3 = this.getNeighbors(itr.next()).iterator();
			while (itr3.hasNext()) {
				fof.add(itr3.next());
			}
		}
		//get rid of the friends that are direct friends of person
		Iterator<String> itr2 = fof.iterator();
		while (itr2.hasNext()) {
			if (friends.contains(itr2.next())) {
				itr2.remove();
			}
		}
		fof.remove(person);
		return fof;
	}

	public List<String> getPathBetween(String pFrom, String pTo) {
		//TODO
		if (!this.getAllVertices().contains(pFrom) || 
				!this.getAllVertices().contains(pTo)) {
			throw new IllegalArgumentException();
		}
		path = new ArrayList<String>();
		visited = new HashSet<String>();
		frontier= new ArrayList<String>();
		
		path.add(pFrom);
		visited.add(pFrom);
		frontier.addAll(this.getNeighbors(pFrom));
		
		// Map every vertex in frontier and explored to its depth
		//HashMap<String, ArrayList<String>> depths;
		while (!frontier.isEmpty()) {
			dfs (frontier, pTo);
		}
		if (path.size() == 1) {
			return null;
		}
		return path;
	}
	private boolean dfs(ArrayList<String> list, String pTo) {
		for (int i = 0; i < list.size(); i++) {
			//mark as visited
			visited.add(list.get(i));
			if (list.get(i).equals(pTo)) {
				path.add(list.get(i));
				return true;
			}
			ArrayList<String> temp = new ArrayList<String>();
			temp.addAll(getNeighbors(list.get(i)));
			if (dfs(temp,pTo)) {
				path.add(list.get(i));
			}
		}
		return false;
	}


	/**
	 * Returns a pretty-print of this graph in adjacency matrix form.
	 * People are sorted in alphabetical order, "X" denotes friendship.
	 * 
	 * This method has been written for your convenience (e.g., for debugging).
	 * You are free to modify it or remove the method entirely.
	 * THIS METHOD WILL NOT BE PART OF GRADING.
	 *
	 * NOTE: this method assumes that the internal hashmap is valid (e.g., no
	 * loop, graph is not a multigraph). USE IT AT YOUR OWN RISK.
	 *
	 * @return pretty-print of this graph
	 */
	public String pprint() {
		// Get alphabetical list of people, for prettiness
		List<String> people = new ArrayList<String>(this.hashmap.keySet());
		Collections.sort(people);

		// String writer is easier than appending tons of strings
		StringWriter writer = new StringWriter();

		// Print labels for matrix columns
		writer.append("   ");
		for (String person: people)
			writer.append(" " + person);
		writer.append("\n");

		// Print one line of social connections for each person
		for (String source: people) {
			writer.append(source);
			for (String target: people) {
				if (this.getNeighbors(source).contains(target))
					writer.append("  X ");
				else
					writer.append("    ");
			}
			writer.append("\n");

		}

		// Remove last newline so that multiple printlns don't have empty
		// lines in between
		String string = writer.toString();
		return string.substring(0, string.length() - 1);
	}

}
