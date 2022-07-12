package com.ufes.delivery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.dao.PedidoDAO;
import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.imposto.Imposto;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Desconto;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;

import junit.framework.Assert;

/**
 *
 * @author ingrid
 */
public class IngridTests {

	public IngridTests() {}

	private ProdutoDAO produtoDao;
	private PedidoDAO pedidoDao;
	private Cliente cliente;
	private Estabelecimento estabelecimento;

	@BeforeEach
	public void carregaDao() {
		produtoDao = ProdutoDAO.getInstance();
		pedidoDao = PedidoDAO.getInstance();
	}

	@Test
	@DisplayName( "Verifica se o método getValorTotalImpostos da classe Pedido " + "retorna o valor total dos impostos corretamente" )
	public void CT001() {

		// Arrange
		cliente = new Cliente( "Fulano", 1000.0 );
		estabelecimento = new Estabelecimento( "Estabelecimento1" );
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() );
		Imposto imposto = new Imposto( "ICMS", 7, 15 );

		double valorTotalImpostoTeste = 15;
		double valorTotalImpostoReal = 0;

		// Act
		pedido.add( imposto );
		valorTotalImpostoReal = pedido.getValorTotalImpostos();

		// Assert
		assertEquals( valorTotalImpostoTeste, valorTotalImpostoReal, 0.001 );

	}

	@Test
	@DisplayName( "Verifica se o método getValorFinal da classe Pedido " + "retorna o valor final do pedido com os impostos e descontos corretamente" )
	public void CT002() {

		// Arrange
		cliente = new Cliente( "Fulano", 1000.0 );
		estabelecimento = new Estabelecimento( "Estabelecimento1" );
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() );
		Imposto imposto = new Imposto( "ICMS", 7, 1 );
		Desconto desconto = new Desconto( "Desconto 10%", 10.0, 5.5 );

		double valorFinalTeste = 169.3;
		double valorFinalReal = 0;

		// Act
		pedido.incluir( new ItemPedido( pedido, produtoDao.buscaProdutoPorCodigo( 28 ), 5 ) );

		pedido.add( imposto );
		pedido.add( desconto );

		pedido.concluir();

		valorFinalReal = pedido.getValorFinal();

		// Assert
		assertEquals( valorFinalTeste, valorFinalReal, 0.001 );

	}

	@Test
	@DisplayName( "Verifica se o método getValorTotalDescontos da classe Pedido " + "retorna o valor total dos descontos corretamente" )
	public void CT003() {

		// Arrange
		cliente = new Cliente( "Fulano", 1000.0 );
		estabelecimento = new Estabelecimento( "Estabelecimento1" );
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() );
		Desconto desconto = new Desconto( "Desconto 10%", 10.0, 5.5 );

		double valorTotalDescontoTeste = 13.4725;
		double valorTotalDescontoReal = 0;

		// Act
		pedido.incluir( new ItemPedido( pedido, produtoDao.buscaProdutoPorCodigo( 28 ), 5 ) );

		pedido.add( desconto );

		pedido.concluir();

		valorTotalDescontoReal = pedido.getValorTotalDescontos();

		// Assert
		assertEquals( valorTotalDescontoTeste, valorTotalDescontoReal, 0.001 );

	}

	@Test
	@DisplayName( "Verifica se o método getValorTotal da classe Pedido " + "retorna o valor total do pedido corretamente" )
	public void CT004() {

		// Arrange
		cliente = new Cliente( "Fulano", 1000.0 );
		estabelecimento = new Estabelecimento( "Estabelecimento1" );
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() );

		double valorTotalTeste = 159.45;
		double valorTotalReal = 0;

		// Act
		pedido.incluir( new ItemPedido( pedido, produtoDao.buscaProdutoPorCodigo( 28 ), 5 ) );
		pedido.concluir();

		valorTotalReal = pedido.getValorTotal();

		// Assert
		assertEquals( valorTotalTeste, valorTotalReal, 0.001 );

	}

	@Test
	@DisplayName( "Verifica se o método getNroSequencialPedido() da classe PedidoDAO retorna um número de pedidos maior que zero" )
	void CT005() {

		// Arrange
		Integer nroPedidoTeste = 0;
		Integer nroPedidoReal = 0;

		// Act
		nroPedidoReal = PedidoDAO.getInstance().getNroSequencialPedido();

		// Assert
		Assert.assertTrue( nroPedidoReal > nroPedidoTeste );

	}

	@Test
	@DisplayName( "Verifica se o método getInstance da classe PedidoDAO " + "não retorna uma instancia nula" )
	public void CT006() {

		// Assert
		assertThat( pedidoDao, notNullValue() );

	}

	@Test
	@DisplayName( "Verifica se o método getInstance da classe ProdutoDAO " + "não retorna uma instancia nula" )
	public void CT007() {

		// Assert
		assertThat( produtoDao, notNullValue() );

	}

	@Test
	@DisplayName( "Verifica se o sistema lança a exceção quando a quantidade passada " + "para o método verificaQuantidade da classe ProdutoDAO é menor que 0" )
	public void CT008() {

		// Arrange
		Exception ex = null;

		// Act
		try {
			produtoDao.adicionaEstoque( 28, -5 );
		} catch ( Exception e ) {
			ex = e;
		}

		// Assert
		assertThat( ex.getMessage(), is( "Quantidade deve ser > 0" ) );

	}

	@Test
	@DisplayName( "Verifica se o método getDescontosConcedidos da classe Pedido " + "retorna todos os descontos do pedido corretamente" )
	public void CT009() {

		// Arrange
		cliente = new Cliente( "Fulano", 1000.0 );
		estabelecimento = new Estabelecimento( "Estabelecimento1" );
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() );
		Desconto desconto = new Desconto( "Desconto 10%", 10.0, 5.5 );

		Integer descontosTeste = 2;
		List<Desconto> descontosReal;

		// Act
		pedido.incluir( new ItemPedido( pedido, produtoDao.buscaProdutoPorCodigo( 28 ), 5 ) );

		pedido.add( desconto );

		pedido.concluir();

		descontosReal = pedido.getDescontosConcedidos();

		// Assert
		assertThat( descontosReal, hasSize( descontosTeste ) );

	}

	@Test
	@DisplayName( "Verifica se o método getImpostos da classe Pedido " + "retorna os impostos do pedido corretamente" )
	public void CT010() {

		// Arrange
		cliente = new Cliente( "Fulano", 1000.0 );
		estabelecimento = new Estabelecimento( "Estabelecimento1" );
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() );
		Imposto imposto = new Imposto( "ICMS", 7, 1 );

		Integer impostosTeste = 3;
		List<Imposto> impostosReal;

		// Act
		pedido.incluir( new ItemPedido( pedido, produtoDao.buscaProdutoPorCodigo( 28 ), 5 ) );

		pedido.add( imposto );

		pedido.concluir();

		impostosReal = pedido.getImpostos();

		// Assert
		assertThat( impostosReal, hasSize( impostosTeste ) );

	}

}
