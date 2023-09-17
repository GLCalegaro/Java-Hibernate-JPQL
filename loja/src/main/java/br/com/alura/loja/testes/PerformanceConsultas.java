package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.vo.RelatorioDeVendasVo;
import util.JPAUtil;

public class PerformanceConsultas {

	public static void main(String[] args) {
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		//Buscando produto por ID:
//		Pedido pedido = em.find(Pedido.class, 1l);
		//Buscando produto por ID junto com o cliente:
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		Pedido pedido = pedidoDAO.buscarPedidoComCliente(1l);
		
		em.close();
		System.out.println(pedido.getCliente().getNome());
	}
		
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria informatica = new Categoria("INFORMATICA");
		Categoria games = new Categoria("GAMES");
		
		Produto celular = new Produto("IPhone", "IPhone 11 64GB - Vermelho", new BigDecimal("2780.0"), celulares);
		Produto notebook = new Produto("Notebook Acer", "Notebook Acer Nitro 5", new BigDecimal("3899.0"), informatica);
		Produto game = new Produto("Alice Madness Returns", "Ação, Aventura, Terror", new BigDecimal("99.90"), games);
		
		Cliente cliente = new Cliente("Marilia", "00022215874");
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(12, pedido, celular));
		pedido.adicionarItem(new ItemPedido(8, pedido, notebook));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(15, pedido, game));
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		
		em.getTransaction().begin();
		
		categoriaDAO.cadastrar(celulares);
		categoriaDAO.cadastrar(informatica);
		categoriaDAO.cadastrar(games);
		
		produtoDAO.cadastrar(celular);
		produtoDAO.cadastrar(notebook);
		produtoDAO.cadastrar(game);
		
		clienteDAO.cadastrar(cliente);
		
		pedidoDAO.cadastrar(pedido);
		pedidoDAO.cadastrar(pedido2);
		
		em.getTransaction().commit();
		em.close();
	}
		
}
	
