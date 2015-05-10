///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SocailNetworkingApp.java
// File:             SocailGraph.java
// Semester:         CS367 Spring 2015
//
// Author:           Thomas Whitburn twhitburn@wisc.edu
// CS Login:         whitburn
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Haomin Li
// Email:            hli256@wisc.edu
// CS Login:         haomin
// Lecturer's Name:  Jim Skrentny
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class represents a simplified social network. It extends 
 * UndirectedGraph<String> with methods specific to social networking.
 *
 * @author Thomas Whitburn, Haomin Li
 */
public class SocialGraph extends UndirectedGraph<String> {

	/**
	 * Creates an empty social graph.
	 * 
	 * DO NOT MODIFY THIS CONSTRUCTOR.
	 */

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

	/**
	 * Returns a set of 2-degree friends of person, if person exists in this 
	 * graph. Otherwise, throws IllegalArgumentException.
	 *
	 * @param person Person to retrieve friends of friends for
	 * @return Set of the friends of friends of a specified person in network
	 */
	public Set<String> friendsOfFriends(String person) {
		
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

	
	/**
	 * Returns the shortest path between two people or null if there is no path
	 * between them, if both people exist in this graph and they are not the 
	 * same person. Otherwise, throws IllegalArgumentException. The returned 
	 * list should begin with personFrom and end with personTo. Only returns one
	 * path if multiple solutions exist.
	 *
	 * @param pFrom person to start with
	 * @param pTo person to get to by traversing social graph
	 * @return list of the people who connect two users
	 */
	public List<String> getPathBetween(String pFrom, String pTo) {

		if (!this.getAllVertices().contains(pFrom) || 
				!this.getAllVertices().contains(pTo)) {
			throw new IllegalArgumentException();
		}

		Set<String> visited = new HashSet<String>();
		ArrayList<SearchNode<String>> frontier= new ArrayList<SearchNode
				<String>>();

		SearchNode<String> node = new SearchNode<String>(pFrom, new 
				ArrayList<String>());
		frontier.add(node);
		visited.add(node.getName());
		while (!frontier.isEmpty()) {
			SearchNode<String> predNode = frontier.remove(0);
			ArrayList<String> temp = new ArrayList<String>();
			temp.addAll(this.getNeighbors(predNode.getName()));
			//put successors in the frontier queue
			for (int i = 0; i < temp.size(); i++) {
				if (visited.contains(temp.get(i))) continue; 
				ArrayList<String> predList = new ArrayList<String>();
				predList.addAll(predNode.getPred());
					predList.add(predNode.getName());
				SearchNode<String> tempNode = new SearchNode<String> 
				(temp.get(i), predList);
				if (tempNode.getName().equals(pTo)) {
					tempNode.getPred().add(pTo);
					return tempNode.getPred();
				}
				if (!visited.contains(tempNode.getName())) {
					frontier.add(tempNode);
					visited.add(temp.get(i));
				}
			}
		}
		return null;
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
