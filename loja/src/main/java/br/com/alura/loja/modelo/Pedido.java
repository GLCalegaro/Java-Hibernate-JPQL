package br.com.alura.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Uma entidade JPA funciona como um espelho de uma tabela no banco de dados
@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	// Com o LocalDate.now, sempre que um produto for instanciado, ele já irá pegar
	// a data atual automaticamente, sendo mapeado instantaneamento no banco de
	// dados;
	private LocalDate data = LocalDate.now();

	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	// O mappedBy serve para indicar: olha, JPA, esse relacionamento, ele já está
	// mapeado lá do outro lado. Que outro lado? Na classe ItemPedido, pelo atributo
	// chamado pedido. Quando você tem esse relacionamento bidirecional, você tem
	// que colocar o mappedBy, geralmente do lado do @OneToMany.
	// Tudo o que eu fizer no pedido, faz também no ItemPedido. Isso vale também
	// para a exclusão, se eu excluir um pedido, não faz sentido eu ter um item
	// pedido voando, se eu matei, se eu deletei o pedido, apaga todos os itens
	// pedidos, não faz mais sentido ter um item pedido sem o pedido, um não existe
	// sem o outro. Então eu vou colocar cascade = CascadeType.ALL.
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	// Aqui já colocar private List<ItemPedido> itens = new ArrayList<>(); para
	// inicializar
	// essa lista como uma lista vazia, porque senão teremos que sempre ficar
	// fazendo aquele if,
	// if lista foi instanciada? Se a lista é nula, dá new na lista; if a lista é
	// nula, dá new na lista. Então teríamos que fazer isso o tempo inteiro. Para
	// evitar
	// essa checagem, jáinicializamos aqui a coleção.
	private List<ItemPedido> itens = new ArrayList<>();

	// Construtor default
	public Pedido() {

	}

	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}

	// Método onde o item conheçe o pedido e o pedido conheçe o item(relação
	// bidirecional).
	// Para evitar espalhar esse tipo de código, é comum, e uma boa prática, aqui,
	// por exemplo, no nosso caso, na classe "Pedido", criarmos um método e esse
	// método é que vai adicionar um item nessa lista. Nesse método utilitário, ele
	// já faz esse vínculo dos dois lados do relacionamento.
	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
}
