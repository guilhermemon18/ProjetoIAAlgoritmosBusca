package classes;

import java.util.LinkedList;
import java.util.List;

//Classe que representa um Conjunto e suas operações.
public class Conjunto <T extends Comparable<T>>{

	private List<T> elementos;

	//Construtor-Padrão de Conjunto
	//Pré-condições: nenhuma.
	//Pós-condições: nenhuma.
	public Conjunto() {
		elementos = new LinkedList<T>();
	}

	//Construtor.
	//Pré-condições: elemento!= null.
	//Pós-condições: nenhuma.
	public Conjunto(T elemento) {
		this();
		elementos.add(elemento);
	}
	
	//Retorna a lista dos elementos do conjunto.
	//Pré-condições: nenhuma.
	//Pós-condições: lista de elementos.
	public List<T> getElementos() {
		return elementos;
	}
	
	//Insere um elemento no conjunto.
	//Pré-condições: elemento != null.
	//Pós-condições: nenhuma.
	public void inserir(T elemento) {
		elementos.add(elemento);
	}
	
	//Faz união entre conjuntos de elementos.
	//Pré-condições: Other != null.
	//Pós-condições: nenhuma.
	public void uniao(Conjunto<T> other) {
		List<T> list = other.elementos;
		for (T t : list) {
			if(!this.elementos.contains(t)) {
				this.inserir(t);
			}
		}
	}

	//Compara 2 conjuntos.
	//Pré-condições: obj != null.
	//Pós-condições: false se diferentes, true se iguais.
	@Override
	public boolean equals(Object obj) {
		Conjunto<T> other = (Conjunto<T>) obj;
		if(this.elementos.size() != other.elementos.size()) {
			return false;
		}
		
		return this.elementos.equals(other.elementos);
	}
	
	//Remove um elemento do conjunto.
	//Pré-condições: elemento != null.
	//Pós-condições: nenhuma.
	public void remover(T elemento) {
		elementos.remove(elemento);
	}
	
	//Verifica se um elemento está no conjunto.
	//Pré-condições: elemento != null.
	//Pós-condições: true se está, false se não está.
	public boolean contains(T elemento) {
		return elementos.contains(elemento);
	}
	
	//Imprime o conjunto.
	//Pré-condições: nenhuma.
	//Pós-condições: nenhuma.
	public void printConjunto() {
		for (T t : elementos) {
			System.out.print(t + " ");
		}
		System.out.println();
	}
	
	
}
