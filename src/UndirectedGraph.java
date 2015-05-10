///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  SocailNetworkingApp.java
// File:             UndirectedGraph.java
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements a simplified social networking app. Via a command line
 * interface, users can login, create/destroy friendships, make queries about 
 * their social circle, and log out. 
 *
 * @author Thomas Whitburn, Haomin Li
 */
public class UndirectedGraph<V> implements GraphADT<V>{

	// Stores the vertices of this graph, and their adjacency lists.
	// It's protected rather than private so that subclasses can access it.
    protected HashMap<V, ArrayList<V>> hashmap;

    /**
     * Creates and Undirected Graph
     */
    public UndirectedGraph() {
        this.hashmap = new HashMap<V, ArrayList<V>>();
    }

    /**
     * Creates a graph from a preconstructed hashmap. This method will be used 
     * for testing submissions. 
     * @param hashmap
     */
    public UndirectedGraph(HashMap<V, ArrayList<V>> hashmap) {
        if (hashmap == null) throw new IllegalArgumentException();
        this.hashmap = hashmap;
    }

    
    /**
     * Adds Vertex to Graph.
     * 
     * @param vertex Vertex to be added
     * @return true if Vertex added successfully
     */
    @Override
    public boolean addVertex(V vertex) {
    	if (vertex == null) throw new IllegalArgumentException();
    	if (!hashmap.containsKey(vertex)) {
    		ArrayList<V> adjacencyList = new ArrayList<V>();
    		hashmap.put(vertex, adjacencyList);
    		return true;
    	}
        return false;
    }

    /**
     * Makes connection between vertices.
     *
     * @param v1 vertex on one side of the edge
     * @param v2 vertex on the other side of the edge
     * @return true if Edge added successfully
     */
    @Override
    public boolean addEdge(V v1, V v2) {
    	if (v1 == null || v2 == null) throw new IllegalArgumentException();
    	if (!hashmap.containsKey(v1) || !hashmap.containsKey(v2))
    		throw new IllegalArgumentException();
    	if (!v1.equals(v2) && !hashmap.get(v1).contains(v2)) {
    		hashmap.get(v1).add(v2);
    		hashmap.get(v2).add(v1);
    		return true;
    	}	
        return false;
    }

    /**
     * Returns a set of the neighboring vertices of a specified vertex.
     *
     * @param vertex of which to retrieve neighbors for
     * @return set of neighboring vertices
     */
    @Override
    public Set<V> getNeighbors(V vertex) {
    	if (vertex == null || !hashmap.containsKey(vertex)) 
    		throw new IllegalArgumentException();
    	HashSet<V> set = new HashSet<V>(hashmap.get(vertex));
		return set;
    }

    /**
     * Removes a connection between vertices.
     *
     * @param v1
     * @param v2
     */
    @Override
    public void removeEdge(V v1, V v2) {
        if (v1 == null || v2 == null) throw new IllegalArgumentException();
        if (hashmap.containsKey(v1) && hashmap.containsKey(v2) 
        		&& hashmap.get(v1).contains(v2) && hashmap.get(v2)
        		.contains(v1)) {
        	hashmap.get(v1).remove(v2);
        	hashmap.get(v2).remove(v1);
        }
        return;
    }

    /**
     * Returns all vertices in graph.
     * 
     * @return set of all vertices in graph
     */
    @Override
    public Set<V> getAllVertices() {
     
    	return hashmap.keySet();
    	
    }

    /* (non-Javadoc)
     * Returns a print of this graph in adjacency lists form.
     * 
     * This method has been written for your convenience (e.g., for debugging).
     * You are free to modify it or remove the method entirely.
     * THIS METHOD WILL NOT BE PART OF GRADING.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        for (V vertex: this.hashmap.keySet()) {
            writer.append(vertex + " -> " + hashmap.get(vertex) + "\n");
        }
        return writer.toString();
    }

}
