package graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

class Neighbor {
	public int vertexNum;
	public Neighbor next;
	public Neighbor(int vnum, Neighbor nbr) {
		this.vertexNum = vnum;
		next = nbr;
	}
} // end class Neighbor

class Vertex {
	String name;
	Neighbor adjList;
	Vertex(String name, Neighbor neighbors) {
		this.name = name;
		this.adjList = neighbors;
	}
} // end class Vertex


public class Graph {
	
	Vertex[] adjLists;
	
	public Graph(String file) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(file));
		
		String graphType = sc.next();
		boolean undirected=true;
		if (graphType.equals("directed")) {
			undirected=false;
		} // end if
		
		adjLists = new Vertex[sc.nextInt()];
		
		// read vertices
		for (int v=0; v < adjLists.length; v++) {
			adjLists[v] = new Vertex(sc.next(), null);
		}
		
		// read edges
		while (sc.hasNext()) {
			
			// read vertex names and translate to vertex numbers
			int v1 = indexForName(sc.next());
			int v2 = indexForName(sc.next());
			
			// add v2 to front of v1's adjacency list and
			// add v1 to front of v2's adjacency list
			adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);
			if (undirected) {
				adjLists[v2].adjList = new Neighbor(v1, adjLists[v2].adjList);
			} // end if (undirected)
		} // end while (sc.hasNext()
	} // end public Graph(String file) throws FileNotFoundException
	
	int indexForName(String name) {
		for (int v = 0; v < adjLists.length; v++) {
			if (adjLists[v].name.equals(name)) {
				return v;
			} // end if
		} // end for (int v = 0; v < adjLists.length; v++)
		return -1;
	} // end int indexForName(String name)
	
	// recursive dfs
	private void dfs(int v, boolean[] visited) { // can only be called from inside the class
		visited[v] = true;
		System.out.println("visiting " + adjLists[v].name);
		
		for (Neighbor nbr=adjLists[v].adjList; nbr != null; nbr = nbr.next) {
			if (!visited[nbr.vertexNum]) {
				System.out.println(adjLists[v].name + "--" + adjLists[nbr.vertexNum].name);
				dfs(nbr.vertexNum, visited);
			} // end if (!visited[nbr.vertexNum]) 
		} // end for (Neighbor nbr=adjLists[v].adjList
	} // end private void dfs
	
	public void dfs() {
		boolean[] visited = new boolean[adjLists.length];
		for (int v = 0; v < visited.length; v++) {
			if (!visited[v]) {
				System.out.println("\nStarting at " + adjLists[v].name);
				dfs(v, visited);
			} // end if
		} // end for (int v = 0; v < visited.length; v++)
	} // end public void dfs()
	
	public void print() {
		System.out.println();
		for (int v = 0; v < adjLists.length; v++) {
			System.out.println(adjLists[v].name);
			for (Neighbor nbr=adjLists[v].adjList; nbr != null; nbr=nbr.next) {
				System.out.print(" --> " + adjLists[nbr.vertexNum].name);
			}
			System.out.println("\n");
		}
	} // end public void print()

	public static void main(String[] args)
	throws IOException { 
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter graph input file name: ");
		String file = sc.nextLine();
		Graph graph = new Graph(file);
		//graph.print();
		graph.dfs();
		sc.close(); // is this the right line?
	} // end public static void main(String[] args)
	
} // end public class Graph
