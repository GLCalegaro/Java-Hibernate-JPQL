package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import util.JPAUtil;

public class TesteComCriteria {

	public static void main(String[] args) {
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		produtoDAO.buscarPorParametrosComCriteria("Notebook Acer", null, LocalDate.now());
	}
	
		private static void popularBancoDeDados() {
			Categoria celulares = new Categoria("CELULARES");
			Categoria informatica = new Categoria("INFORMATICA");
			Categoria games = new Categoria("GAMES");
			
			Produto celular = new Produto("IPhone", "IPhone 11 64GB - Vermelho", new BigDecimal("2780.0"), celulares);
			Produto notebook = new Produto("Notebook Acer", "Notebook Acer Nitro 5", new BigDecimal("3899.0"), informatica);
			Produto game = new Produto("Alice Madness Returns", "Ação, Aventura, Terror", new BigDecimal("99.90"), games);
			
			EntityManager em = JPAUtil.getEntityManager();
			ProdutoDAO produtoDAO = new ProdutoDAO(em);
			CategoriaDAO categoriaDAO = new CategoriaDAO(em);
			
			em.getTransaction().begin();
			
			categoriaDAO.cadastrar(celulares);
			categoriaDAO.cadastrar(informatica);
			categoriaDAO.cadastrar(games);
			
			produtoDAO.cadastrar(celular);
			produtoDAO.cadastrar(notebook);
			produtoDAO.cadastrar(game);
			
			em.getTransaction().commit();
			em.close();
		}

}
