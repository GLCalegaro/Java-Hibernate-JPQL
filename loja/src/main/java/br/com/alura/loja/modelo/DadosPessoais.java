package br.com.alura.loja.modelo;

import javax.persistence.Embeddable;

//Embedded e Embeddable são recursos bem interessantes da JPA, com eles você
// consegue organizar o código, quebrar em classes separadas, ao invés de encher
// uma classe com 300 milhões de atributos.
@Embeddable
public class DadosPessoais {

	private String nome;
	private String cpf;

	//Construtor default
	public DadosPessoais() {
		
	}
	
	public DadosPessoais(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

}
