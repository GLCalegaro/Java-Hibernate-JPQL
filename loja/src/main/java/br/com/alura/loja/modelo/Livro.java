package br.com.alura.loja.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;

//Utilizando herança na JPA(id não precisa pq já está herdando da classe Produto):
@Entity
public class Livro extends Produto{
	
	private String autor;
	private Integer numeroDePaginas;
	
	//Construtor default
	public Livro() {
		// TODO Auto-generated constructor stub
	}
	
	public Livro(String nome, String descricao, BigDecimal preco, Categoria categoria, String autor,
			Integer numeroDePaginas) {
		super(nome, descricao, preco, categoria);
		this.autor = autor;
		this.numeroDePaginas = numeroDePaginas;
	}
	
	public String getAutor() {
		return autor;
	}
	

	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Integer getNumeroDePaginas() {
		return numeroDePaginas;
	}
	public void setNumeroDePaginas(Integer numeroDePaginas) {
		this.numeroDePaginas = numeroDePaginas;
	}
	
}
