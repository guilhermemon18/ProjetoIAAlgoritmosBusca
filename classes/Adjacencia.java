package classes;

//Classe respons�vel por armazenar um elemento adjacente a um v�rtice.
public class Adjacencia <T extends Comparable<T>> implements Comparable<Adjacencia<T>>{

	private T vertice;//v�rtice a ser armazenado.
	private Integer peso;
	
	//Construtor de uma adjacencia.
	//Pr�-condi�oes: v >= 0, peso != null.
	//P�s-condi��es: nenhuma
	public Adjacencia(T v, Integer peso) {
		super();
		this.vertice = v;
		this.peso = peso;
	}

	//Obt�m o v�rtice armazenado neste elemento adjacente.
	//Pr�-condi�oes: nenhuma.
	//P�s-condi��es: vert�ce armazenado.
	public T getV() {
		return vertice;
	}

	//Obtem o peso no elemento adjacente a um v�rtice.
	//Pr�-condi�oes: nenhuma.
	//P�s-condi��es: peso.
	public Integer getPeso() {
		return peso;
	}

	//Compara duas adjacencias.
	//Pr�-condi��es: arg0 != null.
	//P�s-condi��es: -1 se menor, 1 se maior e 0 se iguais.
	@Override
	public int compareTo(Adjacencia<T> arg0) {
		// TODO Auto-generated method stub
		return this.vertice.compareTo(arg0.vertice);
	}

	//Converte para string
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: String representando adjacencia.
	@Override
	public String toString() {
		return "Adjacencia [v=" + vertice + ", peso=" + peso + "]";
	}
	
	

}
