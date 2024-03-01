package classes;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import classes.Node.Edge;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;



enum Cor {BRANCO,CINZA,PRETO};//enumerado para as cores dos algoritmos DFS e BFS.

//Classe que representa um grafo.
public class Grafo <T extends Comparable<T>>{
	private HashMap<T,List<Adjacencia<T>>> verticesOld;


	private Integer nroVertices;
	private boolean orientado;
	private Integer timestamp = 0;
	private HashMap<T,Cor> c;
	//private Cor c[];
	private HashMap<T, Node<T>> vertices;
	private HashMap<T,T> pi; //Indica que T(value) precede T(key) no grafo de Busca.
	//private Integer pi[] ;

	private HashMap<T, Integer> d;//timestamp de descoberta de T.
	//private Integer d[];
	private HashMap<T,Integer> f;//Timestamp de término da exploração de T e vértices adjacentes a T.
	//private Integer f[];


	//Construtor do grafo.
	//Pré-condições: v>0.
	//Pós-condições: nenhuma.
	private Grafo(Integer nroVertices, boolean orientado) {
		this.nroVertices = nroVertices;
		this.orientado = orientado;
		this.verticesOld = new HashMap<T,List<Adjacencia<T>>>();
		this.vertices = new HashMap<T,Node<T>>();
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

	//Insere uma aresta no grafo.
	//Pré-condições: a != null;
	//Pós-condições: nenhuma
	public void inserir (Aresta<T> a) {

		if(a == null) {//cancela se a == null
			return;
		}
		if(verticesOld.containsKey(a.getV())) {
			verticesOld.get(a.getV()).add(new Adjacencia<T>(a.getW(),a.getPeso()));
		}else {
			List<Adjacencia<T>> aux = new LinkedList<Adjacencia<T>>();
			aux.add(new Adjacencia<T>(a.getW(),a.getPeso()));
			verticesOld.put(a.getV(),aux);
			this.nroVertices++;
		}
		Collections.sort(verticesOld.get(a.getV()));

		if(!verticesOld.containsKey(a.getW())) {//se n tem na lista do vertice, precisa criar ele
			List<Adjacencia<T>> aux = new LinkedList<Adjacencia<T>>();
			//			aux.add(new Adjacencia<T>(a.getV(),a.getPeso()));
			verticesOld.put(a.getW(),aux);
			this.nroVertices++;
		}

		if(!orientado) {
			verticesOld.get(a.getW()).add(new Adjacencia<T>(a.getV(),a.getPeso()));
			Collections.sort(verticesOld.get(a.getW()));
		}

	}

	public Node<T> getNode(T key){
		return this.vertices.get(key);
	}


	public void inserir (Node<T> origem,Node<T> destino, int weight ) {

		if(vertices.containsKey(origem.getId())) {//se origem já existe no grafo, simplesmente adicione a aresta ao nó.
			vertices.get(origem.getId()).addBranch(weight, destino);
		}else {//senão adicione a aresta ao nó e adicione o nó ao grafo!
			origem.addBranch(weight, destino);//adiciona a aresta ao nó origem.
			vertices.put(origem.getId(), origem);//adiciona o nó ao hashMap do grafo.
			//this.nroVertices++;
		}

		if(!vertices.containsKey(destino.getId())) {//se destino n está na lista dos vertices, precisa adicioná-lo
			vertices.put(destino.getId(),destino);
			//this.nroVertices++;
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
	private void DFS_visit(List<Adjacencia<T>> v, T indice, List<T> verticesVisitados) {
		c.replace(indice, Cor.CINZA);
		//c[indice] = Cor.CINZA;
		verticesVisitados.add(indice);
		timestamp++;
		//d[indice] = timestamp;
		d.replace(indice, timestamp);

		for (Adjacencia<T> adj : v) {
			if(c.get(adj.getV()) == Cor.BRANCO) {//c[adj.getV()] 
				pi.replace(adj.getV(), indice);
				//pi[adj.getV()] = indice;
				DFS_visit(verticesOld.get(adj.getV()), adj.getV(), verticesVisitados);
			}
		}
		//c[indice] = Cor.PRETO;
		c.replace(indice, Cor.PRETO);
		timestamp++;
		//f[indice] = timestamp;
		f.replace(indice, timestamp);
	}

	//	//Busca em profundidade
	//	//pré-requisitos: origem >= 0
	//	//Pós-requisitos: lista na ordem da visitação dos vértices.
	public List<T> DFS(T origem){
		List<T> verticesVisitados = new ArrayList<T>();

		c = new HashMap<T,Cor>();
		pi = new HashMap<T, T>();
		d = new HashMap<T,Integer>();
		f = new HashMap<T,Integer>();


		this.verticesOld.forEach((key, value) -> {
			c.put(key, Cor.BRANCO);
			pi.put(key, null);
			d.put(key, 0);
			f.put(key, 0);
		});

		timestamp = 0;

		DFS_visit(verticesOld.get(origem), origem,verticesVisitados);
		return verticesVisitados;
	}


	private boolean DFS_visit(List<Adjacencia<T>> v, T atual, T destino, List<T> verticesVisitados) {
		c.replace(atual, Cor.CINZA);
		//verticesVisitados.add(atual);
		timestamp++;

		System.out.println("Visitando " + atual);
		//			try {
		//				//Thread.sleep(3000);
		//			} catch (InterruptedException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}

		if(atual.equals(destino)) {
			verticesVisitados.add(0,atual);
			System.out.println("Chegou ao destino " + destino);
			c.replace(atual, Cor.PRETO);
			timestamp++;
			f.replace(atual, timestamp);
			return true;
		}

		//d[indice] = timestamp;
		d.replace(atual, timestamp);

		for (Adjacencia<T> adj : v) {
			if(c.get(adj.getV()) == Cor.BRANCO) {//c[adj.getV()] 
				pi.replace(adj.getV(), atual);
				//pi[adj.getV()] = indice;
				if(DFS_visit(verticesOld.get(adj.getV()), adj.getV(),destino, verticesVisitados)) {

					verticesVisitados.add(0,atual);//adiciona o vértice atual e retorna true.
					c.replace(atual, Cor.PRETO);
					timestamp++;
					//f[indice] = timestamp;
					f.replace(atual, timestamp);
					return true;
				}
			}
		}
		//c[indice] = Cor.PRETO;
		c.replace(atual, Cor.PRETO);
		timestamp++;
		//f[indice] = timestamp;
		f.replace(atual, timestamp);
		return false;
	}


	public List<T> DFS(T origem, T destino){
		List<T> verticesVisitados = new ArrayList<T>();

		c = new HashMap<T,Cor>();
		pi = new HashMap<T, T>();
		d = new HashMap<T,Integer>();
		f = new HashMap<T,Integer>();


		this.verticesOld.forEach((key, value) -> {
			c.put(key, Cor.BRANCO);
			pi.put(key, null);
			d.put(key, 0);
			f.put(key, 0);
		});


		timestamp = 0;

		DFS_visit(verticesOld.get(origem), origem, destino,verticesVisitados);
		return verticesVisitados;
	}



	//	//Busca em Largura
	//	//pré-requisitos: origem >= 0
	//	//Pós-requisitos: lista na ordem da visitação dos vértices.
	//	public List<Integer> BFS(Integer origem){
	//		List<Integer> visita_vertices = new ArrayList<Integer>();
	//		Queue<Integer> q = new LinkedList<Integer>();
	//
	//
	//		c = new Cor[nroVertices];
	//		pi = new Integer[nroVertices];
	//		d = new Integer[nroVertices];
	//		f = new Integer[nroVertices];
	//
	//		for(int i = 0; i < nroVertices; i++) {
	//			if(i != origem) {
	//				c[i] = Cor.BRANCO;
	//				d[i] = Integer.MAX_VALUE;
	//				pi[i] = -1;
	//			}
	//		}
	//
	//		c[origem] = Cor.CINZA;
	//		visita_vertices.add(origem);
	//		pi[origem] = -1;
	//		d[origem] = 0;
	//		q.add(origem);
	//
	//		while(!q.isEmpty()) {
	//			Integer u = q.peek();
	//			List<Adjacencia> aux = vertices[u];
	//			for (Adjacencia adjacencia : aux) {
	//				int info = adjacencia.getV();
	//				if(c[info] == Cor.BRANCO) {
	//					c[info] = Cor.CINZA;
	//					visita_vertices.add(info);
	//					d[info] = d[u] + 1;
	//					pi[info] = u;
	//					q.add(info);
	//				}
	//			}
	//			q.poll();
	//			c[u] = Cor.PRETO;
	//		}
	//
	//		return visita_vertices;
	//	}

	//Inicia os vetores auxiliares de acordo com a o vertice de origem.
	//Pré-condições: origem>=0;
	//Pós-condições: nenhuma.
	private void InicializaOrigem(T origem) {
		vertices.forEach((key, value) -> {
			d.put(key, Integer.MAX_VALUE);
			pi.put(key, null);
		});

		d.replace(origem,0);
	}


	// Metodo que repetidamente diminui o limite superior do peso do menor caminho.
	//Pré-condições: a!= null.
	//Pós-condições: nenhuma.
	private void Relax(Aresta<T> a) {
		T verticeW = a.getW();
		T verticeV = a.getV();
		Integer pesoAresta = a.getPeso();
		Integer dw = d.get(verticeW);
		Integer dv = d.get(verticeV);


		if(dw > dv + pesoAresta && dv != Integer.MAX_VALUE) {
			d.replace(verticeW, dv + pesoAresta);
			pi.replace(verticeW, verticeV);
		}
	}
	//
	//	//Obtem a aresta formada por u e v (u,v)
	//	//Pré-condições: u >= 0, v>= 0.
	//	//Pós-condições: retorna a Aresta.
	public Aresta<T> getAresta(T u, T v) {
		List<Adjacencia<T>> l = verticesOld.get(u);
		for (Adjacencia<T> adjacencia : l) {
			if(adjacencia.getV() == v) {
				return new Aresta<T>(u,v,adjacencia.getPeso());
			}
		}
		return null;

	}


	//Preenche o vetor com as arestas de g
	//Pré-condições: a!= null.
	//Pós-condições: Retorna |E|, quantidade de arestas
	private int grafoArestas(Aresta<T> a[]) {
		int  count = 0;
//		vertices.forEach((key, value) -> {
//			for (Edge edge : value.neighbors) {
//				a[count++] = new Aresta(key, edge.node.getId(),Integer.valueOf(edge.weight));
//			}
//
//		});

		for (Map.Entry<T, Node<T>> entry : vertices.entrySet()) {
			T key = entry.getKey();
			Node<T> value = entry.getValue();
			// Faça algo com a chave (key) e o valor (value)
			for (Edge edge : value.neighbors) {
				a[count++] = new Aresta(key, edge.node.getId(),Integer.valueOf(edge.weight));
			}
		}

		return count;
	}

	//	//Obtem as arestas do grafo.
	//	//Pré-condições: nenhuma.
	//	//Pós-condições: Lista de arestas pertencentes ao grafo.
	public List<Aresta<T>> getArestas(){
		List<Aresta<T>> l = new LinkedList<Aresta<T>>();
		if(isOrientado()) {

			this.verticesOld.forEach((key, value) -> {					
				for (Adjacencia<T> adjacencia : this.verticesOld.get(key)) {
					l.add(new Aresta<T>(key,adjacencia.getV(),adjacencia.getPeso()));
				}
			});

		}else {

			this.verticesOld.forEach((key, value) -> {
				for (Adjacencia<T> adjacencia : this.verticesOld.get(key)) {
					if(adjacencia.getV().compareTo(key) > 0)
						l.add(new Aresta<T>(key,adjacencia.getV(),adjacencia.getPeso()));
				}
			});

		}
		return l;
	}

	//	//Retorna a quantidade de arestas do grafo.
	//	//Pré-condições: nenhuma.
	//	//Pós-condições: quantidade de arestas.
	//	public int countArestas() {
	//		int count = 0;
	//		List<Adjacencia> aux;
	//		for(int i = 0; i < nroVertices; i++) {
	//			aux = vertices[i];
	//			for (Adjacencia adjacencia : aux) {
	//				if(adjacencia.getV() > i) 
	//					count++;
	//			}
	//		}
	//		return count;
	//	}
	//
	//	//Bellman-Ford: Calcula o menor caminho
	//	//Pré-condições: origem >=0
	//	//Pós-condições: caso exista ciclo de peso negativo, o algoritmo retorna false, caso contrário retorna true.
	public boolean Bellman_Ford(T origem, T destino) {
		d = new HashMap<T,Integer>();
		pi = new HashMap<T,T>();

		InicializaOrigem(origem);

		Aresta e[] = new Aresta[nroVertices * nroVertices];
		int count_arestas = grafoArestas(e,true);

		for(int i = 0; i < nroVertices -1; i++) {
			for(int j = 0; j < count_arestas; j++) {
				Relax(e[j]);
			}
		}
		for(int i = 0; i < count_arestas; i++) {
			if(d[e[i].getW()] > d[e[i].getV()] + e[i].getPeso() && d[e[i].getV()] != Integer.MAX_VALUE)
				return false;
		}


		//imprimir os caminhos:
		System.out.println("Origem: " + origem);
		for(int i = 0; i < nroVertices; i++) {
			System.out.print("Destino:  " + i + " dist.:  " + d[i] +  " caminho:  ");
			printCaminho(i);
			System.out.println(i);
		}


		return true;
	}
	//
	//	//Encontra o primeiro conjunto que contém o elemento.
	//	//Pré-condições: elemento != null
	//	//Pós-condições: retorna o conjunto se encontrar o elemento, retorna null se não encontrar.
	//	private Conjunto<Integer> findConjuntobyElemento(ArrayList<Conjunto<Integer>> c, Integer elemento){
	//
	//		for (Conjunto<Integer> conjunto : c) {
	//			if(conjunto.contains(elemento)) {
	//				return conjunto;
	//			}
	//		}
	//		return null;
	//	}
	//
	//	//Kruskal: Calcula a árvore geradora mínima.
	//	//Pré-condições: nenhuma.
	//	//Pós-condições: Conjunto com as arestas da árvore geradora mínima.
	//	public Conjunto<Aresta> KRUSKALL() {
	//		Aresta a[] = new Aresta[countArestas()];
	//		int qtdArestas = grafoArestas(a,false);
	//		Conjunto<Aresta> A = new Conjunto<Aresta>();
	//		ArrayList<Conjunto<Integer>> conjutosVertices = new ArrayList<Conjunto<Integer>>();
	//
	//		for(int i = 0; i < nroVertices; i++) {
	//			conjutosVertices.add(new Conjunto<Integer>(i));
	//		}
	//		Arrays.sort(a);
	//
	//		for(int i = 0; i < qtdArestas; i++){
	//			Conjunto<Integer> u = findConjuntobyElemento(conjutosVertices, a[i].getV());
	//			Conjunto<Integer> v = findConjuntobyElemento(conjutosVertices, a[i].getW());
	//
	//			if(!u.equals(v)) {
	//				A.uniao(new Conjunto<Aresta>(a[i]));
	//				u.uniao(v);
	//				conjutosVertices.remove(v);
	//
	//			}
	//		}
	//		return A;
	//	}
	//
	//
	//	//Prim: calcula a árvore geradora mínima.
	//	//Pré-condições: origem >= 0;
	//	//Pós-condições: lista com as arestas da árvore geradora mínima.
	//	public List<Aresta> Prim(int origem){
	//		List<Aresta> l = new LinkedList<Aresta>();
	//		Queue<Integer> q = new LinkedList<Integer>();
	//		Integer key[] = new Integer[nroVertices];
	//		pi = new Integer[nroVertices];
	//
	//		q.add(origem);
	//		for(int i = 0; i < nroVertices; i++) {
	//			if(i != origem)
	//				q.add(i);
	//			key[i] = Integer.MAX_VALUE;
	//		}
	//
	//		key[origem] = 0;
	//		pi[origem] = -1;
	//
	//
	//
	//		while(!q.isEmpty()) {
	//			Integer u = q.poll();
	//			for(Adjacencia adj : vertices[u]) {
	//				Integer v = adj.getV();
	//				Integer w = getAresta(u,v).getPeso();
	//				if(q.contains(v) &&  w < key[v]) {
	//					pi[v] = u;
	//					key[v] = w;
	//				}
	//			}
	//
	//			Collections.sort((List<Integer>)q, new Comparator<Integer>() {
	//				@Override
	//				public int compare(Integer i1, Integer i2) {
	//					if(key[i1] < key[i2]) {
	//						return -1;
	//					}else if(key[i1] > key[i2])
	//						return 1;
	//					return 0;
	//				}
	//			});
	//		}
	//		for(int i = 0; i < nroVertices; i++) {
	//			if( pi[i] != null && pi[i] != -1) {
	//				l.add(getAresta(i, pi[i]));
	//			}
	//		}
	//
	//		return l;
	//	}
	//
	//
	//	//imprime o caminho a partir de n.
	//	//Pré-condições: n >=0
	//	//Pós-condições: nenhuma.
	//	private void printCaminho(int n) {
	//		if(pi[n] != -1) {
	//			printCaminho(pi[n]);
	//			System.out.print(pi[n] + " ");
	//		}
	//	}
	//
	//imprime o grafo
	//Pré-condições: nenhuma.
	//Pós-condições: nenhuma.
	public void imprimirGrafo() {
		this.verticesOld.forEach((key, value) -> {
			System.out.println(key);
			for (Adjacencia<T> adjacencia : value) {
				System.out.println(new Aresta<T>(key,adjacencia.getV(),adjacencia.getPeso()));
			}	      
		});

	}

	//Desenha o grafo
	//Pré-condições: nome_arquivo != null;
	//Pós-condições: nenhuma
	public void desenhaGrafo(String nome_arquivo) {
		MutableGraph G = mutGraph(nome_arquivo).setDirected(this.isOrientado());

		List<Aresta<T>> aux = this.getArestas();
		for (Aresta<T> aresta : aux){
			Link a = mutNode(aresta.getW().toString()).linkTo();
			a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.BLACK);
			G.add(mutNode(aresta.getV().toString()).addLink(a));

		}

		try {
			Graphviz.fromGraph(G).width(200).render(Format.PNG).toFile(new File("desenhos/" + nome_arquivo + ".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//	//Desenha o grafo
	//	//Pré-condições: nome_arquivo != null, l != null;
	//	//Pós-condições: nenhuma
	public void desenhaGrafo(String nome_arquivo,List<Aresta<T>> l) {
		MutableGraph G = mutGraph(nome_arquivo).setDirected(this.isOrientado());

		List<Aresta<T>> aux = this.getArestas();
		for (Aresta<T> aresta : aux){
			Link a = mutNode(aresta.getW().toString()).linkTo();
			if(l.contains(aresta) || l.contains(new Aresta<T>(aresta.getW(),aresta.getV(),aresta.getPeso()))) {
				a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.RED);

			}else {
				a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.BLACK);
			}
			G.add(mutNode(aresta.getV().toString()).addLink(a));
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
