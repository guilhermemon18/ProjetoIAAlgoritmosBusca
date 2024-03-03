package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node implements Comparable<Node> {
    // Id for readability of result purposes
    private String id;//é basicamente o nomedovértice, no caso vai ser string
    // Parent in the path
    public Node parent = null;
    public List<Edge> vizinhos;//vizinhos deles, se for grafo não orientado o vizinho tem este como vizinho também.
    public List<Adjacencia> nosAdjacentes; //lista com nós adjacentes.

    //Funções de avaliação:
    private double f = Double.MAX_VALUE;
    private double g = Double.MAX_VALUE;
    private double heuristica; //heuristica!

    public Node(String id,double h){
          this.heuristica = h;
          this.id = id;
          this.vizinhos = new ArrayList<>();
          this.nosAdjacentes = new ArrayList<>();
    }
    
    
    public Node(String id) {
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
		return heuristica;
	}


	public void setH(double h) {
		this.heuristica = h;
	}


	public String getId() {
		return id;
	}
	


	@Override
    public int compareTo(Node n) {
          return Double.compare(this.f, n.f);
    }

    public  class Edge implements Comparable<Edge>{
          Edge(int weight,Node node){
                this.weight = weight;
                this.node = node;
          }
          
       

          public int weight;
          public Node node;
		@Override
		public int compareTo(Edge o) {
			// TODO Auto-generated method stub
			return this.node.id.compareTo(o.node.id);
		}
          
        
    }
    	
    
    

    @Override
	public boolean equals(Object obj) {
		Node n = (Node) obj;
		return this.id.equals(n.id);
	}


	public void addBranch(int peso, Node node){
          Edge newEdge = new Edge(peso, node);
          vizinhos.add(newEdge);
          nosAdjacentes.add(new Adjacencia(node.getId(), peso));
          Collections.sort(nosAdjacentes);
          Collections.sort(vizinhos);
          
    }

    public double calculateHeuristic(Node target){
          return this.heuristica;
    }


	public void setF(double f) {
		this.f = f;
	}
}
