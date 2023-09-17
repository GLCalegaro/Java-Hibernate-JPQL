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

public class CadastroDePedido {

	public static void main(String[] args) {
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		
		Produto produto = produtoDAO.buscarPorId(1l);
		Produto produto2 = produtoDAO.buscarPorId(2l);
		Produto produto3 = produtoDAO.buscarPorId(3l);
		
		Cliente cliente = clienteDAO.buscarPorId(1l);
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(12, pedido, produto));
		pedido.adicionarItem(new ItemPedido(8, pedido, produto2));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(15, pedido, produto3));
		
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		pedidoDAO.cadastrar(pedido);
		pedidoDAO.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
		System.out.println("Valor Total: " + totalVendido);
		
		List<RelatorioDeVendasVo> relatorio = pedidoDAO.relatorioDeVendas();
		//Percorre cada um dos elementos e exibe através do Systemout
		relatorio.forEach(System.out::println);
		
	}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria informatica = new Categoria("INFORMATICA");
		Categoria games = new Categoria("GAMES");
		
		Produto celular = new Produto("IPhone", "IPhone 11 64GB - Vermelho", new BigDecimal("2780.0"), celulares);
		Produto notebook = new Produto("Notebook Acer", "Notebook Acer Nitro 5", new BigDecimal("3899.0"), informatica);
		Produto game = new Produto("Alice Madness Returns", "Ação, Aventura, Terror", new BigDecimal("99.90"), games);
		
		Cliente cliente = new Cliente("Marilia", "00022215874");
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		
		
		em.getTransaction().begin();
		
		categoriaDAO.cadastrar(celulares);
		categoriaDAO.cadastrar(informatica);
		categoriaDAO.cadastrar(games);
		
		produtoDAO.cadastrar(celular);
		produtoDAO.cadastrar(notebook);
		produtoDAO.cadastrar(game);
		
		clienteDAO.cadastrar(cliente);
		
		em.getTransaction().commit();
		em.close();
	}

}
