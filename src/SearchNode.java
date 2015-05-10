///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SocailNetworkingApp.java
// File:             SearchNode.java
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

import java.util.ArrayList;

/**
 * We created a generic implementation of a Search Node that keeps track of 
 * the state and predecessors of a node for a graph to help implement BFS in
 * the getPathBetween method in SocialGraph.java.
 *
 *
 * @author Thomas Whitburn, Haomin Li
 */
public class SearchNode<V> {
	private V state;
	private ArrayList<V> predecessors;

	/**
	 * Constructs a generic Search Node.
	 *
	 * @param state information for node
	 * @param pred predecessors of the node
	 */
	public SearchNode(V state, ArrayList<V> pred) {
		this.state = state;
		this.predecessors = pred;
		
	}
	
	/**
	 * Returns the state of the node.
	 * 
	 * @return state (In our case this is a name)
	 */
	public V getName() {
		return this.state;
	} 
	
	/**
	 * Returns the a list of the predecessors of the node.
	 * 
	 * @return pred the predecessors of the node
	 */
	public ArrayList<V> getPred() {
		return this.predecessors;
	}
	
	
}
