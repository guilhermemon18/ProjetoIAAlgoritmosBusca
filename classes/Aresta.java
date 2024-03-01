package classes;

//Classe que representa uma Aresta.
public class Aresta <T extends Comparable<T>> implements Comparable<Aresta<T>> {

	private T v;
	private T w;
	private Integer peso;
	
	//Construtor.
	//Pr�-condi��es: v >= 0, w >= 0, peso != null.
	//P�s-condi��es: nenhuma.
	public Aresta(T v, T w, Integer peso) {
		this.v = v;
		this.w = w;
		this.peso = peso;
	}

	//Obt�m o v�rtice v da aresta.
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: v�rtice v.
	public T getV() {
		return v;
	}

	//Obt�m o v�rtice w da aresta.
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: v�rtice w.
	public T getW() {
		return w;
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
		return "(" + v + ", " + w + ")";
	}

	//Compara duas arestas.
	//Pr�-condi��es: arg0 != null.
	//P�s-condi��es: -1 se for menor, 1 se for maior e 0 se forem iguais.
	@Override
	public int compareTo(Aresta<T> arg0) {
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
		Aresta<T> a = (Aresta<T>) obj;
		return (this.v.equals(a.v) && this.w.equals(a.w) && this.peso.equals(a.peso));
	}
	
	
	
	
}
