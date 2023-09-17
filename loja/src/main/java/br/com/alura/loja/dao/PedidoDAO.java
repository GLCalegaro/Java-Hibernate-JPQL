package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PedidoDAO {

	private EntityManager em;

	public PedidoDAO(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	// Método para pegar o valor total vendido no site:
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}

	// Método para mostrar um relatório de vendas exibindo nome dos produtos,
	// quantidades e data do pedido;
	// Join está sendo feito para unir itens da classe Pedido, com item.quantidade;
	public List<RelatorioDeVendasVo> relatorioDeVendas() {
		String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo( " + "produto.nome, "
				+ "SUM(item.quantidade) as quantidadeTotal, " + "MAX(pedido.data))" + "FROM Pedido pedido "
				+ "JOIN pedido.itens item " + "JOIN item.produto produto " + "GROUP BY produto.nome "
				+ "ORDER BY item.quantidade DESC";

		return em.createQuery(jpql, RelatorioDeVendasVo.class).getResultList();
	}

	// Método de busca por id junto com cliente:
	public Pedido buscarPedidoComCliente(Long id) {
		// O JOIN FETCH não é um join porque você quer filtrar alguma coisa do
		// relacionamento, é um join fetch. Eu já estou falando: JPA, já faça o fetch,
		// já carregue, já busque junto esse relacionamento com o cliente. O join fetch
		// permite escolher quais relacionamentos serão carregados em determinada
		// consulta, ao invés de sempre os carregar
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
				.setParameter("id", id).getSingleResult();
	}
}
