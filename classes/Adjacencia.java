package classes;

//Classe responsável por armazenar um elemento adjacente a um vértice.
public class Adjacencia <T extends Comparable<T>> implements Comparable<Adjacencia<T>>{

	private T vertice;//vértice a ser armazenado.
	private Integer peso;
	
	//Construtor de uma adjacencia.
	//Pré-condiçoes: v >= 0, peso != null.
	//Pós-condições: nenhuma
	public Adjacencia(T v, Integer peso) {
		super();
		this.vertice = v;
		this.peso = peso;
	}

	//Obtém o vértice armazenado neste elemento adjacente.
	//Pré-condiçoes: nenhuma.
	//Pós-condições: vertíce armazenado.
	public T getV() {
		return vertice;
	}

	//Obtem o peso no elemento adjacente a um vértice.
	//Pré-condiçoes: nenhuma.
	//Pós-condições: peso.
	public Integer getPeso() {
		return peso;
	}

	//Compara duas adjacencias.
	//Pré-condições: arg0 != null.
	//Pós-condições: -1 se menor, 1 se maior e 0 se iguais.
	@Override
	public int compareTo(Adjacencia<T> arg0) {
		// TODO Auto-generated method stub
		return this.vertice.compareTo(arg0.vertice);
	}

	//Converte para string
	//Pré-condições: nenhuma.
	//Pós-condições: String representando adjacencia.
	@Override
	public String toString() {
		return "Adjacencia [v=" + vertice + ", peso=" + peso + "]";
	}
	
	

}
