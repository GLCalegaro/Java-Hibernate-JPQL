package br.com.alura.loja.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;

//Utilizando herança na JPA(id não precisa pq já está herdando da classe Produto):
@Entity
public class Informatica extends Produto{
	private String marca;
	private String modelo;
	
	public String getAutor() {
		return marca;
	}
	
	//Construtor default
	public Informatica() {
		// TODO Auto-generated constructor stub
	}
	
	public Informatica(String marca, String modelo) {
		this.marca = marca;
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

}
