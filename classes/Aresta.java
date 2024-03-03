package classes;

//Classe que representa uma Aresta.
public class Aresta implements Comparable<Aresta> {

	private String src;
	private String dst;
	private Integer peso;
	
	//Construtor.
	//Pré-condições: v >= 0, w >= 0, peso != null.
	//Pós-condições: nenhuma.
	public Aresta(String src, String dst, Integer peso) {
		this.src = src;
		this.dst = dst;
		this.peso = peso;
	}

	//Obtém o vértice v da aresta.
	//Pré-condições: nenhuma.
	//Pós-condições: vértice v.
	public String getSrc() {
		return src;
	}

	//Obtém o vértice w da aresta.
	//Pré-condições: nenhuma.
	//Pós-condições: vértice w.
	public String getDst() {
		return dst;
	}

	//Obtém o peso da aresta.
	//Pré-condições: nenhuma.
	//Pós-condições: o peso.
	public Integer getPeso() {
		return peso;
	}

	//Converte aresta para string.
	//Pré-condições: nenhuma.
	//Pós-condições: String representando uma aresta.
	@Override
	public String toString() {
		return "(" + src + ", " + dst + ")";
	}

	//Compara duas arestas.
	//Pré-condições: arg0 != null.
	//Pós-condições: -1 se for menor, 1 se for maior e 0 se forem iguais.
	@Override
	public int compareTo(Aresta arg0) {
		if(this.peso < arg0.peso) {
			return -1;
		}else if(this.peso > arg0.peso) {
			return 1;
		}
		return 0;
	}

	//Compara duas arestas.
	//Pré-condições: obj != null.
	//Pós-condições: true se iguais, false se diferentes.
	@Override
	public boolean equals(Object obj) {
		Aresta a = (Aresta) obj;
		return (a != null && this.src.equals(a.src) && this.dst.equals(a.dst) && this.peso.equals(a.peso));
	}


	
	
	
}
