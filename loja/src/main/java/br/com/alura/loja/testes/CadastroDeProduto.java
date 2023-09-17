package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		cadastrarProduto();
		Long id = 1l;
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		
		Produto p = produtoDAO.buscarPorId(1l);
		System.out.println(p.getPreco());
		
		List<Produto> buscarTodos = produtoDAO.buscarPorNomeDaCategoria("CELULARES");
		buscarTodos.forEach(prod -> System.out.println(p.getNome()));
		
		//Buscando preço dos produtos a partir do nome:
		BigDecimal precoDoProduto = produtoDAO.buscarPrecoDoProdutoComNome("IPhone");
		System.out.println("Preço encontrado: " + precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("IPhone", "IPhone 11 64GB - Vermelho", new BigDecimal("2780.0"), celulares);
		
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		
		em.getTransaction().begin();
		
		categoriaDAO.cadastrar(celulares);
		produtoDAO.cadastrar(celular);
		
		em.getTransaction().commit();
		em.close();
	}

}
