package classes;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;



enum Cor {BRANCO,CINZA,PRETO};//enumerado para as cores dos algoritmos DFS e BFS.

//Classe que representa um grafo.
public class Grafo {
	private Integer nroVertices;
	private boolean orientado;
	private Integer timestamp = 0;
	private HashMap<String,Cor> c;
	private Map<String, Node> vertices;
	private HashMap<String,String> pi; //Indica que String(value) precede String(key) no grafo de Busca.
	private HashMap<String, Integer> d;//timestamp de descoberta de String.
	private HashMap<String,Integer> f;//Timestamp de término da exploração de String e vértices adjacentes a String.



	//Construtor do grafo.
	//Pré-condições: v>0.
	//Pós-condições: nenhuma.
	private Grafo(Integer nroVertices, boolean orientado) {
		this.nroVertices = nroVertices;
		this.orientado = orientado;
		this.vertices = new HashMap<String,Node>();
		this.vertices = new  TreeMap<String,Node>();
	}

	public Grafo() {
		this(0,false);
	}

	public Grafo(boolean orientado) {
		this(0,true);
	}


	//Retorna a quantidade de vértices no grafo.
	//Pré-condições: nenhuma.
	//Pós-condições: quantidade de vertices.
	public Integer getNroVertices() {
		return nroVertices;
	}

	//Verifica se o grafo é orientado
	//Pré-condições: nenhuma.
	//Pós-condições: true se for orientado, caso contrário false.
	public boolean isOrientado() {
		return orientado;
	}

	public Node getNode(String key){
		return this.vertices.get(key);
	}

	public void inserir (Node origem,Node destino, int weight ) {

		if(vertices.containsKey(origem.getId())) {//se origem já existe no grafo, simplesmente adicione a aresta ao nó.
			vertices.get(origem.getId()).addBranch(weight, destino);
		}else {//senão adicione a aresta ao nó e adicione o nó ao grafo!
			origem.addBranch(weight, destino);//adiciona a aresta ao nó origem.
			vertices.put(origem.getId(), origem);//adiciona o nó ao hashMap do grafo.
			this.nroVertices++;
		}

		if(!vertices.containsKey(destino.getId())) {//se destino n está na lista dos vertices, precisa adicioná-lo
			vertices.put(destino.getId(),destino);
			this.nroVertices++;
		}

		if(!orientado) {//se não é orientado, precisa adicionar aresta do destino à origem também.
			vertices.get(destino.getId()).addBranch(weight, origem);
		}

	}


	//retornar false se n encontrar o nó e recurisivamente fazer a lógica para inserir o caminho na lista de 
	//vertices visitados para encontrar o caminho certo para isso.

	//Visita o vértice indice
	//Pré-condições: v != null, indice >= 0, visita_vertices != null;
	//Pós-condições: nenhuma.
	private void DFS_visit(List<Adjacencia> v, String indice, List<String> verticesVisitados) {
		c.replace(indice, Cor.CINZA);
		verticesVisitados.add(indice);
		timestamp++;
		d.replace(indice, timestamp);

		for (Adjacencia adj : v) {
			if(c.get(adj.getV()) == Cor.BRANCO) {//c[adj.getV()] 
				pi.replace(adj.getV(), indice);
				DFS_visit(vertices.get(adj.getV()).nosAdjacentes, adj.getV(), verticesVisitados);
			}
		}
		c.replace(indice, Cor.PRETO);
		timestamp++;
		f.replace(indice, timestamp);
	}

	//	//Busca em profundidade
	//	//pré-requisitos: origem >= 0
	//	//Pós-requisitos: lista na ordem da visitação dos vértices.
	public List<String> DFS(String origem){
		List<String> verticesVisitados = new ArrayList<String>();

		c = new HashMap<String,Cor>();
		pi = new HashMap<String, String>();
		d = new HashMap<String,Integer>();
		f = new HashMap<String,Integer>();


		this.vertices.forEach((key, value) -> {
			c.put(key, Cor.BRANCO);
			pi.put(key, null);
			d.put(key, 0);
			f.put(key, 0);
		});

		timestamp = 0;

		DFS_visit(vertices.get(origem).nosAdjacentes, origem,verticesVisitados);
		return verticesVisitados;
	}


	private boolean DFS_visit(List<Adjacencia> v, String atual, String destino, List<String> verticesVisitados) {
		c.replace(atual, Cor.CINZA);
		//verticesVisitados.add(atual);
		timestamp++;

		System.out.println("Visitando " + atual);

		if(atual.equals(destino)) {
			verticesVisitados.add(0,atual);
			System.out.println("Chegou ao destino " + destino);
			c.replace(atual, Cor.PRETO);
			timestamp++;
			f.replace(atual, timestamp);
			return true;
		}

		d.replace(atual, timestamp);

		for (Adjacencia adj : v) {
			if(c.get(adj.getV()) == Cor.BRANCO) {//c[adj.getV()] 
				pi.replace(adj.getV(), atual);
				if(DFS_visit(vertices.get(adj.getV()).nosAdjacentes, adj.getV(),destino, verticesVisitados)) {
					verticesVisitados.add(0,atual);//adiciona o vértice atual e retorna true.
					c.replace(atual, Cor.PRETO);
					timestamp++;
					f.replace(atual, timestamp);
					return true;
				}
			}
		}
		c.replace(atual, Cor.PRETO);
		timestamp++;
		f.replace(atual, timestamp);
		return false;
	}


	public List<String> DFS(String origem, String destino){
		List<String> verticesVisitados = new ArrayList<String>();

		c = new HashMap<String,Cor>();
		pi = new HashMap<String, String>();
		d = new HashMap<String,Integer>();
		f = new HashMap<String,Integer>();


		this.vertices.forEach((key, value) -> {
			c.put(key, Cor.BRANCO);
			pi.put(key, null);
			d.put(key, 0);
			f.put(key, 0);
		});


		timestamp = 0;

		DFS_visit(vertices.get(origem).nosAdjacentes, origem, destino,verticesVisitados);
		return verticesVisitados;
	}


	//Inicia os vetores auxiliares de acordo com a o vertice de origem.
	//Pré-condições: origem>=0;
	//Pós-condições: nenhuma.
	private void InicializaOrigem(String origem) {
		this.vertices.forEach((key, value) -> {
			d.put(key, Integer.MAX_VALUE);
			pi.put(key, null);
		});

		d.replace(origem,0);
	}


	// Metodo que repetidamente diminui o limite superior do peso do menor caminho.
	//Pré-condições: a!= null.
	//Pós-condições: nenhuma.
	private void Relax(Aresta a) {
		String verticeW = a.getDst();
		String verticeV = a.getSrc();
		Integer pesoAresta = a.getPeso();
		Integer dw = d.get(verticeW);
		Integer dv = d.get(verticeV);


		if(dw > dv + pesoAresta && dv != Integer.MAX_VALUE) {
			d.replace(verticeW, dv + pesoAresta);
			pi.replace(verticeW, verticeV);
		}
	}

	//	//Obtem a aresta formada por u e v (u,v)
	//	//Pré-condições: u >= 0, v>= 0.
	//	//Pós-condições: retorna a Aresta.
	public Aresta getAresta(String u, String v) {
		List<Adjacencia> l = vertices.get(u).nosAdjacentes;
		for (Adjacencia adjacencia : l) {
			if(adjacencia.getV().equals(v)) {
				return new Aresta(u,v,adjacencia.getPeso());
			}
		}
		return null;

	}


	//Preenche o vetor com as arestas de g
	//Pré-condições: a!= null.
	//Pós-condições: Retorna |E|, quantidade de arestas
	private int grafoArestas(Aresta a[]) {
		int  count = 0;
		for (Map.Entry<String, Node> entry : vertices.entrySet()) {
			String key = entry.getKey();
			Node value = entry.getValue();
			// Faça algo com a chave (key) e o valor (value)
			for (Adjacencia adjacencia : value.nosAdjacentes) {
				a[count++] = new Aresta(key, adjacencia.getV(),adjacencia.getPeso());
			}
		}

		return count;
	}

	//	//Obtem as arestas do grafo.
	//	//Pré-condições: nenhuma.
	//	//Pós-condições: Lista de arestas pertencentes ao grafo.
	public List<Aresta> getArestas(){
		List<Aresta> l = new LinkedList<Aresta>();
		if(isOrientado()) {

			this.vertices.forEach((key, value) -> {					
				for (Adjacencia adjacencia : this.vertices.get(key).nosAdjacentes) {
					l.add(new Aresta(key,adjacencia.getV(),adjacencia.getPeso()));
				}
			});

		}else {

			this.vertices.forEach((key, value) -> {
				for (Adjacencia adjacencia : this.vertices.get(key).nosAdjacentes) {
					if(adjacencia.getV().compareTo(key) > 0)
						l.add(new Aresta(key,adjacencia.getV(),adjacencia.getPeso()));
				}
			});

		}
		return l;
	}

	//	//Bellman-Ford: Calcula o menor caminho
	//	//Pré-condições: origem >=0
	//	//Pós-condições: caso exista ciclo de peso negativo, o algoritmo retorna false, caso contrário retorna true.
	public boolean Bellman_Ford(String origem, String destino, List<String> caminho) {
		d = new HashMap<String,Integer>();
		pi = new HashMap<String,String>();

		InicializaOrigem(origem);

		Aresta e[] = new Aresta[nroVertices * nroVertices];
		int count_arestas = grafoArestas(e);

		for(int i = 0; i < nroVertices -1; i++) {
			for(int j = 0; j < count_arestas; j++) {
				Relax(e[j]);
			}
		}


		for(int i = 0; i < count_arestas; i++) {
			Integer dw = d.get(e[i].getDst());
			Integer dv= d.get(e[i].getSrc());
			Integer pesoAresta = e[i].getPeso();
			if(dw > dv + pesoAresta && dv != Integer.MAX_VALUE)
				return false;
		}


		//imprimir os caminhos:

		System.out.println("Origem: " + origem);
		this.vertices.forEach((key, value) -> {
			System.out.print("Destino:  " + key + " dist.:  " + d.get(key) +  " caminho:  ");
			printCaminho(key);
			System.out.println(key);
		});
		formaCaminho(destino,caminho);
		caminho.add(destino);
		System.out.println(caminho);
		return true;
	}


	//imprime o caminho a partir do vértice n.
	//Pré-condições: n >=0
	//Pós-condições: nenhuma.
	private void printCaminho(String n) {
		String piN = pi.get(n);
		if(piN != null) {
			printCaminho(piN);
			System.out.print(piN + " ");
		}
	}
	
	//Forma o caminho a partir do vértice s.
	//Pré-condições: caminho é lista de String instanciada e inicializada.
	//Pós-condiçoes: nenhuma.
	private void formaCaminho(String s, List<String> caminho) {
		String piS = pi.get(s);
		if(piS != null) {
			formaCaminho(piS,caminho);
			caminho.add(piS);
		}
	}

	//imprime o grafo
	//Pré-condições: nenhuma.
	//Pós-condições: nenhuma.
	public void imprimirGrafo() {
		System.out.println("Imprimindo  Grafo: ");
		System.out.println("Número de vértices: " + this.nroVertices);
		this.vertices.forEach((key, value) -> {
			System.out.println("Vértice: "  + key);
			for (Adjacencia adjacencia : value.nosAdjacentes) {
				System.out.println(new Aresta(key,adjacencia.getV(),adjacencia.getPeso()));
			}	      
		});

	}

	//Desenha o grafo
	//Pré-condições: nome_arquivo != null;
	//Pós-condições: nenhuma
	public void desenhaGrafo(String nome_arquivo) {
		MutableGraph G = mutGraph(nome_arquivo).setDirected(this.isOrientado());

		List<Aresta> aux = this.getArestas();
		for (Aresta aresta : aux){
			Link a = mutNode(aresta.getDst().toString()).linkTo();
			a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.BLACK);
			G.add(mutNode(aresta.getSrc().toString()).addLink(a));

		}

		try {
			Graphviz.fromGraph(G).width(1080).height(1920).render(Format.PNG).toFile(new File("desenhos/" + nome_arquivo + ".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//	//Desenha o grafo
	//	//Pré-condições: nome_arquivo != null, l != null;
	//	//Pós-condições: nenhuma
	public void desenhaGrafo(String nome_arquivo,List<Aresta> l) {
		MutableGraph G = mutGraph(nome_arquivo).setDirected(this.isOrientado());

		List<Aresta> aux = this.getArestas();
		for (Aresta aresta : aux){
			Link a = mutNode(aresta.getDst().toString()).linkTo();
			if(l.contains(aresta) || l.contains(new Aresta(aresta.getDst(),aresta.getSrc(),aresta.getPeso()))) {
				a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.RED);

			}else {
				a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.BLACK);
			}
			G.add(mutNode(aresta.getSrc().toString()).addLink(a));
		}

		try {
			Graphviz.fromGraph(G).width(1080).height(1920).render(Format.PNG).toFile(new File("desenhos/" + nome_arquivo + ".png"));
			//new ViewGrafo(Graphviz.fromGraph(G).width(400).height(400).render(Format.PNG).toImage());


		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}



}
