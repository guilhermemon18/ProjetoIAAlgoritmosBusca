package classes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Algoritmos  {
	private String verticeOrigem;
	private String verticeDestino;
	private Grafo grafo;
	private List<Aresta> heuristicas;

	//Realiza a leitura do arquivo e seta as configurações no construtor.
	public Algoritmos(String nomeArquivo) throws IOException {
		this.grafo = new Grafo(true);
		this.heuristicas = new LinkedList<Aresta>();
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(nomeArquivo), "UTF-8"));
		String linha = buffRead.readLine();

		while(linha != null) {
			String aux[] = linha.split("\\(");
			String nome = aux[0].trim();
			aux = aux[1].split("\\)");
			String parenteses = aux[0].trim();


			if(nome.equalsIgnoreCase("h")) {
				String vertices[] = parenteses.split(",");
				String verticeV = vertices[0].trim();
				String verticeW = vertices[1].trim();
				Integer heuristica = Integer.valueOf(vertices[2].trim());
				//System.out.println("Vertice v:" + verticeV);
				if(verticeW.equalsIgnoreCase(verticeDestino) ){//|| verticeV.equalsIgnoreCase(verticeDestino)) {
					grafo.getNode(verticeV).setH(heuristica);
					//grafo.getNode(verticeW).setH(distancia);
				}
				heuristicas.add(new Aresta(verticeV,verticeW,heuristica));

			}else if(nome.equalsIgnoreCase("pode_ir")) {
				String vertices[] = parenteses.split(",");
				String verticeV = vertices[0].trim();
				String verticeW = vertices[1].trim();
				Integer distancia = Integer.valueOf(vertices[2].trim());
				grafo.inserir(new Node(verticeV),new Node(verticeW) , distancia);
			}
			else if(nome.equalsIgnoreCase("ponto_inicial")) {
				verticeOrigem = parenteses;
			}
			else { //if(nome.equalsIgnoreCase("ponto_final")){
				verticeDestino = parenteses;
			}
			linha = buffRead.readLine();
		}
		grafo.getNode(verticeOrigem).setG(0);
		grafo.imprimirGrafo();
		grafo.desenhaGrafo("grafo-carregado");
		
		buffRead.close();

	}

	//pior solução: Busca em profundidade
	public float piorSolucaoDFS() {
		long tempoInicial = System.currentTimeMillis();
		List<String> listaCaminho = grafo.DFS(this.verticeOrigem,this.verticeDestino);//retorna a lista de pontos visitados
		float tempoFinal = ( (float) (System.currentTimeMillis() - tempoInicial));
		System.out.println("Nós do trajeto: ");
		for (String string : listaCaminho) {
			System.out.print("(" + string + ")" + " ");
		}
		System.out.println();
		List<Aresta> arestas = new LinkedList<Aresta>();
		//forma as arestas que foram percorridas
		for (int i = 0; i < listaCaminho.size() - 1; i++) {
			arestas.add(grafo.getAresta(listaCaminho.get(i),listaCaminho.get(i+1)));
		}
		//desenha o grafo com o percorrimento
		grafo.desenhaGrafo("piorSolucaoDFS", arestas);
		return tempoFinal;

	}

	public float melhorSolucaoAEstrela(){
		long tempoInicial = System.currentTimeMillis();
		Node n = aStar();
		float tempoFinal = ( (float) (System.currentTimeMillis() - tempoInicial));
		List<Aresta> arestas = new LinkedList<Aresta>();
		//forma as arestas que foram percorridas
		if(n==null)
			return tempoFinal;

		List<String> ids = new ArrayList<>();

		while(n.parent != null){
			ids.add(n.getId());
			n = n.parent;
		}
		ids.add(n.getId());
		Collections.reverse(ids);
		System.out.println("Nós do trajeto: ");
		for (String string : ids) {
			System.out.print("(" + string +")"+ " ");
		}
		System.out.println();
		for(int i = 0; i < ids.size() - 1; i++){
			arestas.add(grafo.getAresta(ids.get(i),ids.get(i+1)));	
			
		}
		
		//desenha o grafo com o percorrimento
		grafo.desenhaGrafo("AEstrela", arestas);
		return tempoFinal;

	}


	public  Node aStar(){
		System.out.println("A Star!");
		Node start = this.grafo.getNode(this.verticeOrigem),target = this.grafo.getNode(this.verticeDestino);
		PriorityQueue<Node> closedList = new PriorityQueue<>();
		PriorityQueue<Node> openList = new PriorityQueue<>();

		start.setF(start.getG() + start.calculateHeuristic(target));
		openList.add(start);

		while(!openList.isEmpty()){
			Node n = openList.peek();
			System.out.println("Node sendo analisado: " + n.getId());
			if(n.equals(target)){
				System.out.println("Chegou ao destino!");
				return n;
			}
			//percorre os vizinhos do nó atual.
			for(Node.Edge edge : n.vizinhos){
				Node m = edge.node;
				
				double totalWeight = n.getG() + edge.weight;
				if(!openList.contains(m) && !closedList.contains(m)){
					m.parent = n;
					m.setG(totalWeight);
					m.setF(m.getG() + m.calculateHeuristic(target));
					
					openList.add(m);
				} else {
					if(totalWeight < m.getG()){
						m.parent = n;
						m.setG(totalWeight);
						m.setF(m.getG() + m.calculateHeuristic(target));

						if(closedList.contains(m)){
							closedList.remove(m);
							openList.add(m);
						}
					}
				}
				System.out.println("Vizinho: " + m.getId());
				
			}

			openList.remove(n);
			closedList.add(n);
		}
		return null;
	}
	
	public float bellmanFord() {
		long tempoInicial = System.currentTimeMillis();
		//grafo.imprimirGrafo();
		List<String> listaCaminho = new LinkedList<String>();
		grafo.Bellman_Ford(this.verticeOrigem,this.verticeDestino,listaCaminho);//retorna a lista de pontos visitados
		float tempoFinal = ( (float) (System.currentTimeMillis() - tempoInicial));
		System.out.println("Nós do trajeto: ");
		for (String string : listaCaminho) {
			System.out.print("(" + string + ")" + " ");
		}
		System.out.println();
		List<Aresta> arestas = new LinkedList<Aresta>();
		//forma as arestas que foram percorridas
		for (int i = 0; i < listaCaminho.size() - 1; i++) {
			arestas.add(grafo.getAresta(listaCaminho.get(i),listaCaminho.get(i+1)));
		}
		System.out.println("Arestas da solução BELLMAN-FORD: " + arestas);
		//desenha o grafo com o percorrimento
		grafo.desenhaGrafo("bellmanFord", arestas);
		return tempoFinal;
	}

	public static void main(String[] args) {
		String fileName = null;
		int option = 0;
		Scanner c = new Scanner(System.in);
		System.out.println("Digite o nome do arquivo a processar!");
		fileName = c.nextLine();
		Algoritmos algoritmos = null;
		
		//análises de desempenho foram realizadas empiricamente por meio do time final - time inicial de processamento do algoritmo.
		
		while(algoritmos == null) {
			try {
				algoritmos = new Algoritmos(fileName);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Erro ao abrir arquivo, tente novamente!");
				System.out.println("Digite o nome do arquivo a processar!");
				fileName = c.nextLine();
			}
			
		}
		do {
			System.out.println("Escolha o algoritmo para: " + fileName);
			System.out.println("1.Pior Solução: Busca em Profundidade (DFS).");
			System.out.println("2.Melhor Solução: A Estrela (A*).");
			System.out.println("3.Bônus: Bellman_Ford.");
			System.out.println("4.Mudar arquivo.");
			System.out.println("5.Sair.");
			option = c.nextInt();


			switch(option) {
			case 1:
				System.out.println("tempo:"+algoritmos.piorSolucaoDFS()+ " milisegundos");
				break;
			case 2:
				
				System.out.println("tempo:"+algoritmos.melhorSolucaoAEstrela()+ " milisegundos");
				break;
			case 3:
				System.out.println("tempo:"+algoritmos.bellmanFord()+ " milisegundos");
				break;
			case 4:
				System.out.println("Digite o nome do arquivo a processar!");
				c.nextLine();
				String fileNameAux = c.nextLine();
				algoritmos = null;
				while(algoritmos == null) {
					try {
						algoritmos = new Algoritmos(fileNameAux);
						fileName = fileNameAux;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("Erro ao abrir arquivo, tente novamente!");
					}
					System.out.println("Digite o nome do arquivo a processar!");
					fileNameAux = c.nextLine();
				}
				break;
			case 5:
				System.out.println("Programa terminado");
				break;
			default:
				System.out.println("Escolha uma opção válida!");
			}
		}while(option != 5);
		c.close();
	}




}



