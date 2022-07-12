package com.ufes.delivery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Desconto;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.model.Produto;

public class JuliaTests {

	private Produto produto;
	private Pedido pedido;
	private Cliente cliente;
	private Estabelecimento estabelecimento;
	private ProdutoDAO dao;
	private Desconto desconto;

	@BeforeEach
	void carregaDao() {
		dao = ProdutoDAO.getInstance();
	}

	@Test
	@DisplayName( "Verifica informações do produto" )
	void CT001() {
		// inicio 15:23
		// termino 15:30
		produto = new Produto( 50, "Molho de Tomate", 30, 3.50 );

		DecimalFormat df = new DecimalFormat( "0.00" );
		String info = " Produto: " + produto.getNome() + ", código: " + produto.getCodigo() + ", preço unitário: " + df.format( produto.getPrecoUnitario() ) + ", quantidade em estoque: " + df.format( produto.getQuantidadeEmEstoque() );

		assertThat( info, equalTo( produto.toString() ) );
	}

	@Test
	@DisplayName( "Verifica se um item é excluido corretamente do pedido" )
	void CT002() {
		// inicio 08:53
		// termino 09:12

		cliente = new Cliente( "Julia", 500.00 );
		estabelecimento = new Estabelecimento( "BC Supermercados" );
		pedido = new Pedido( 10, cliente, estabelecimento, LocalTime.of( 10, 00 ) );
		Exception exception = null;

		ItemPedido biscoito = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 5 ), 1 );
		ItemPedido cafe = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 6 ), 1 );
		ItemPedido creme = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 7 ), 1 );

		List<ItemPedido> itens = new ArrayList<>();
		itens.add( biscoito );
		itens.add( cafe );

		pedido.incluir( biscoito );
		pedido.incluir( cafe );
		pedido.incluir( creme );

		try {
			pedido.removeItem( "Creme de Leite" );
		} catch ( Exception e ) {
			exception = e;
		}

		assertThat( itens, equalTo( pedido.getItens() ) );
	}

	@Test
	@DisplayName( "Verificar se o sistema aceita que dois descontos iguais sejam adicionados ao pedido" )
	void CT003() {
		// inicio 10:54
		// termino 10:57

		cliente = new Cliente( "Julia", 500.00 );
		estabelecimento = new Estabelecimento( "BC Supermercados" );
		pedido = new Pedido( 10, cliente, estabelecimento, LocalTime.of( 10, 00 ) );
		Exception exception = null;

		ItemPedido biscoito = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 5 ), 1 );

		pedido.incluir( biscoito );

		desconto = new Desconto( "Dia das mães", 0.10, pedido.getValorTotal() * 0.10 );

		try {
			pedido.add( desconto );
			pedido.add( desconto );
		} catch ( Exception e ) {
			exception = e;
		}

		assertThat( exception.getMessage(), is( "O pedido só aceita uma unidade do tipo de desconto." ) );
	}

	@Test
	@DisplayName( "Verificar se o estoque é reposto corretamente após o cancelamento do pedido" )
	void CT004() {
		// inicio 11:25 11:38
		cliente = new Cliente( "Julia", 500.00 );
		estabelecimento = new Estabelecimento( "BC Supermercados" );
		pedido = new Pedido( 10, cliente, estabelecimento, LocalTime.of( 10, 00 ) );
		Exception exception = null;

		ItemPedido biscoito = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 5 ), 10 );

		pedido.incluir( biscoito );

		pedido.concluir();
		pedido.pagar();
		pedido.cancelar();
		pedido.reembolsar();

		assertThat( 15.0, equalTo( dao.buscaProdutoPorCodigo( 5 ).getQuantidadeEmEstoque() ) );
	}

	@Test
	@DisplayName( "Verificar se a exceção é lançada corretamente ao adicionar um item sem um pedido ser criado" )
	void CT005() {
		// inicio 10:21 termino 10:28

		Exception exception = null;

		try {
			ItemPedido item = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 1 ), 1 );
		} catch ( Exception e ) {
			exception = e;
		}

		assertThat( exception.getMessage(), is( "É necessário informar o pedido!" ) );

	}

	@Test
	@DisplayName( "Verificar se a exceção é lançada corretamente ao adicionar ao pedido um item inexistente" )
	void CT006() {
		// inicio 10:28 termino 10:30

		Exception exception = null;
		cliente = new Cliente( "Julia", 500.00 );
		estabelecimento = new Estabelecimento( "BC Supermercados" );
		pedido = new Pedido( 10, cliente, estabelecimento, LocalTime.of( 10, 00 ) );

		try {
			ItemPedido item = new ItemPedido( pedido, produto, 1 );
		} catch ( Exception e ) {
			exception = e;
		}

		assertThat( exception.getMessage(), is( "Informe um produto valido!" ) );
	}

	@Test
	@DisplayName( "Verificar se as informações em 'toString' da classe 'ItemPedido' são retornadas corretamente" )
	void CT007() {
		// inicio 10:30 termino 10:34

		cliente = new Cliente( "Julia", 500.00 );
		estabelecimento = new Estabelecimento( "BC Supermercados" );
		pedido = new Pedido( 10, cliente, estabelecimento, LocalTime.of( 10, 00 ) );
		Exception exception = null;

		ItemPedido item = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 5 ), 1 );

		DecimalFormat df = new DecimalFormat( "0.00" );
		String info = "Item: " + item.getNomeProduto() + ", quantidade: " + df.format( item.getQuantidade() ) + ", preço unitário: R$ " + df.format( item.getValorUnitario() ) + ", preço total do item: R$ " + df.format( item.getValorTotal() );

		assertThat( info, equalTo( item.toString() ) );
	}

	@Test
	@DisplayName( "Verificar se o número do pedido na classe 'ItemPedido' é retornado corretamente" )
	void CT008() {
		// inicio 10:34 termino 10:35

		cliente = new Cliente( "Julia", 500.00 );
		estabelecimento = new Estabelecimento( "BC Supermercados" );
		pedido = new Pedido( 10, cliente, estabelecimento, LocalTime.of( 10, 00 ) );
		Exception exception = null;

		ItemPedido item = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 5 ), 1 );

		assertThat( 10, equalTo( item.getNroPedido() ) );
	}

	@Test
	@DisplayName( "Verificar se o valor unitário na classe 'ItemPedido' é retornado corretamente" )
	void CT009() {
		// inicio 10:35 termino 10:36

		cliente = new Cliente( "Julia", 500.00 );
		estabelecimento = new Estabelecimento( "BC Supermercados" );
		pedido = new Pedido( 10, cliente, estabelecimento, LocalTime.of( 10, 00 ) );
		Exception exception = null;

		ItemPedido item = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 5 ), 1 );

		assertThat( 4.95, equalTo( item.getValorUnitario() ) );
	}

	@Test
	@DisplayName( "Verificar se a quantidade é atualizada corretamente na classe 'ItemPedido'" )
	void CT010() {
		// inicio 10:36 termino 10:38

		cliente = new Cliente( "Julia", 500.00 );
		estabelecimento = new Estabelecimento( "BC Supermercados" );
		pedido = new Pedido( 10, cliente, estabelecimento, LocalTime.of( 10, 00 ) );
		Exception exception = null;

		ItemPedido item = new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 5 ), 1 );
		item.setQuantidade( 50.0 );

		assertThat( 50.0, equalTo( item.getQuantidade() ) );
	}

}
