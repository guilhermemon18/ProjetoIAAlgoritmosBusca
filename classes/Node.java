package classes;

import java.util.ArrayList;
import java.util.List;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    // Id for readability of result purposes
    private T id;//é basicamente o nomedovértice, no caso vai ser string
    // Parent in the path
    public Node<T> parent = null;
    public List<Edge> neighbors;//vizinhos deles, se for grafo não orientado o vizinho tem este como vizinho também.

    // Evaluation functions
    private double f = Double.MAX_VALUE;
    private double g = Double.MAX_VALUE;
    // Hardcoded heuristic
    private double h; //heuristica!

    public Node(T id,double h){
          this.h = h;
          this.id = id;
          this.neighbors = new ArrayList<>();
    }
    
    
    public Node(T id) {
    	this(id, Double.MAX_VALUE);
    }
    
    

    public void setG(double g) {
		this.g = g;
	}


	public double getF() {
		return f;
	}


	public double getG() {
		return g;
	}


	public double getH() {
		return h;
	}


	public void setH(double h) {
		this.h = h;
	}


	public T getId() {
		return id;
	}


	@Override
    public int compareTo(Node<T> n) {
          return Double.compare(this.f, n.f);
    }

    public  class Edge {
          Edge(int weight,Node<T> node){
                this.weight = weight;
                this.node = node;
          }
          
       

          public int weight;
          public Node<T> node;
          
        
    }
    
    
    public List<Node<T>> getNeighbors(){
    	for (Edge edge : neighbors) {
			
		}
    }
    
    

    @Override
	public boolean equals(Object obj) {
		Node<T> n = (Node<T>) obj;
		return this.id.equals(n.id);
	}


	public void addBranch(int weight, Node<T> node){
          Edge newEdge = new Edge(weight, node);
          neighbors.add(newEdge);
    }

    public double calculateHeuristic(Node<T> target){
          return this.h;
    }


	public void setF(double f) {
		this.f = f;
	}
}
