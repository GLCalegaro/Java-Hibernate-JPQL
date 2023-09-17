package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDAO {

	private EntityManager em;

	public ProdutoDAO(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}

	// Método para consulta de produtos por id:
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}

	// Método para listar todos os produtos do banco de dados(utilizando a linguagem
	// JPQL que nada mais é do que uma string):
	public List<Produto> buscarTodos() {
		String jpql = "SELECT p FROM Produto p";
		return em.createQuery(jpql, Produto.class).getResultList();
	}

	// Método consulta produtos com filtro de nome:
	public List<Produto> buscarPorNome(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, Produto.class).setParameter("nome", nome).getResultList();
	}

	// Método consulta produtos por categoria:
	public List<Produto> buscarPorNomeDaCategoria(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
		return em.createQuery(jpql, Produto.class).setParameter("nome", nome).getResultList();
	}

	// Método consulta produtos com retorno apenas de um atributo, no caso o preço:
	public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, BigDecimal.class).setParameter("nome", nome).getSingleResult();
	}

	// Método de busca com parâmetros opcionais
	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
		String jpql = "SELECT p FROM Produto p WHERE 1=1";
		if (nome != null && !nome.trim().isEmpty()) {
			jpql = "AND p.nome = :nome";
		}
		if (preco != null) {
			jpql = "AND p.preco = :preco";
		}
		if (dataCadastro != null) {
			jpql = "AND p.dataCadastro = :dataCadastro";
		}

		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
		if (nome != null && !nome.trim().isEmpty()) {
			query.setParameter("nome", nome);
		}
		if (preco != null) {
			query.setParameter("preco", preco);
		}
		if (dataCadastro != null) {
			query.setParameter("dataCadastro", dataCadastro);
		}

		return query.getResultList();
	}

	// Método de busca com parametros opcionais sem usar JPQL e usando Criteria
	// API(mais enxuto porém mais complexo para entendimento e de difícil manutenção):
	public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
		// O método getCriteriaBuilder() cria um objeto criteria
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);

		Predicate filtros = builder.and();
		if (nome != null && !nome.trim().isEmpty()) {
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
		}
		if (preco != null) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		}
		if (dataCadastro != null) {
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		}
		query.where(filtros);

		return em.createQuery(query).getResultList();
	}
}
