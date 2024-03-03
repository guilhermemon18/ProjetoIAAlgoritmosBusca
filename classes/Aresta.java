package classes;

//Classe que representa uma Aresta.
public class Aresta implements Comparable<Aresta> {

	private String src;
	private String dst;
	private Integer peso;
	
	//Construtor.
	//Pr�-condi��es: v >= 0, w >= 0, peso != null.
	//P�s-condi��es: nenhuma.
	public Aresta(String src, String dst, Integer peso) {
		this.src = src;
		this.dst = dst;
		this.peso = peso;
	}

	//Obt�m o v�rtice v da aresta.
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: v�rtice v.
	public String getSrc() {
		return src;
	}

	//Obt�m o v�rtice w da aresta.
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: v�rtice w.
	public String getDst() {
		return dst;
	}

	//Obt�m o peso da aresta.
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: o peso.
	public Integer getPeso() {
		return peso;
	}

	//Converte aresta para string.
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: String representando uma aresta.
	@Override
	public String toString() {
		return "(" + src + ", " + dst + ")";
	}

	//Compara duas arestas.
	//Pr�-condi��es: arg0 != null.
	//P�s-condi��es: -1 se for menor, 1 se for maior e 0 se forem iguais.
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
	//Pr�-condi��es: obj != null.
	//P�s-condi��es: true se iguais, false se diferentes.
	@Override
	public boolean equals(Object obj) {
		Aresta a = (Aresta) obj;
		return (a != null && this.src.equals(a.src) && this.dst.equals(a.dst) && this.peso.equals(a.peso));
	}


	
	
	
}
