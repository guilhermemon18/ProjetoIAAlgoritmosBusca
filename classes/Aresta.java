package classes;

//Classe que representa uma Aresta.
public class Aresta <T extends Comparable<T>> implements Comparable<Aresta<T>> {

	private T v;
	private T w;
	private Integer peso;
	
	//Construtor.
	//Pré-condições: v >= 0, w >= 0, peso != null.
	//Pós-condições: nenhuma.
	public Aresta(T v, T w, Integer peso) {
		this.v = v;
		this.w = w;
		this.peso = peso;
	}

	//Obtém o vértice v da aresta.
	//Pré-condições: nenhuma.
	//Pós-condições: vértice v.
	public T getV() {
		return v;
	}

	//Obtém o vértice w da aresta.
	//Pré-condições: nenhuma.
	//Pós-condições: vértice w.
	public T getW() {
		return w;
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
		return "(" + v + ", " + w + ")";
	}

	//Compara duas arestas.
	//Pré-condições: arg0 != null.
	//Pós-condições: -1 se for menor, 1 se for maior e 0 se forem iguais.
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
	//Pré-condições: obj != null.
	//Pós-condições: true se iguais, false se diferentes.
	@Override
	public boolean equals(Object obj) {
		Aresta<T> a = (Aresta<T>) obj;
		return (this.v.equals(a.v) && this.w.equals(a.w) && this.peso.equals(a.peso));
	}
	
	
	
	
}
