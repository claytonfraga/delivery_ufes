package com.ufes.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.builder.DiretorCesta;
import com.ufes.delivery.imposto.Imposto;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.model.Produto;

class DenerTest {

	public DenerTest() {}

	private Cliente cliente;
	private Estabelecimento vendedor;

	@Test
	@DisplayName( "Verifica o toString() da model Produto." )
	void Teste001() {
		// Horário de início: 13:03
		// Horário de témino: 13:06
		// Arrange
		var produto = new Produto( 38, "Produto de Teste", 1, 1.50 );

		// Act

		// Assert
		assertEquals( " Produto: Produto de Teste, código: 38, preço unitário: 1,50, quantidade em estoque: 1,00", produto.toString() );

	}

	@Test
	@DisplayName( "Verifica método de getNroPedido() do ItemPedido." )
	void Teste002() {
		// Horário de início: 13:23
		// Horário de témino: 13:27
		// Arrange
		cliente = new Cliente( "Cliente", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		var pedido = new Pedido( 250, cliente, vendedor, LocalTime.of( 9, 25 ) );
		var produto = new Produto( 38, "Produto de Teste", 1, 1.50 );
		var quantidade = 5;
		var itemPedido = new ItemPedido( pedido, produto, quantidade );

		// Act
		pedido.incluir( itemPedido );

		// Assert
		assertEquals( 250, itemPedido.getNroPedido() );

	}

	@Test
	@DisplayName( "Verifica os setters e o getCodigoProduto de itemPedido." )
	void Teste003() {
		// Horário de início: 13:35
		// Horário de témino: 13:40
		// Arrange
		cliente = new Cliente( "Cliente", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		var pedido = new Pedido( 250, cliente, vendedor, LocalTime.of( 9, 25 ) );

		var produto = new Produto( 38, "Produto de Teste", 1, 1.50 );
		var quantidade = 5;
		var itemPedido = new ItemPedido( pedido, produto, quantidade );

		// Act
		itemPedido.setProduto( produto );
		itemPedido.setQuantidade( quantidade );

		// Assert
		assertEquals( "Produto de Teste", itemPedido.getNomeProduto() );
		assertEquals( 38, itemPedido.getCodigoProduto() );

	}

	@Test
	@DisplayName( "Verifica validação de diretor onde a cesta é nula." )
	void Teste004() {
		// Horário de início: 13:47
		// Horário de témino: 13:55
		// Arrange
		var diretor = new DiretorCesta();

		// Act

		// Assert
		assertThrows( RuntimeException.class, () -> diretor.build( null ) );
	}

	@Test
	@DisplayName( "Verifica getHistoricoPedido do pedido." )
	void Teste005() {
		// Horário de início: 14:02
		// Horário de témino: 14:05
		// Arrange
		cliente = new Cliente( "Cliente", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		var pedido = new Pedido( 250, cliente, vendedor, LocalTime.of( 9, 25 ) );

		// Act
		var list = pedido.getHistoricoPedido();

		// Assert
		assertEquals( false, list.isEmpty() );
	}

	@Test
	@DisplayName( "Verifica setValorReembolsado do pedido." )
	void Teste006() {
		// Horário de início: 14:10
		// Horário de témino: 14:14
		// Arrange
		cliente = new Cliente( "Cliente", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		var pedido = new Pedido( 250, cliente, vendedor, LocalTime.of( 9, 25 ) );

		// Act

		// Assert
		assertThrows( RuntimeException.class, () -> pedido.setValorReembolsado( 0 ) );
	}

	@Test
	@DisplayName( "Verifica getters do Imposto." )
	void Teste007() {
		// Horário de início: 14:15
		// Horário de témino: 14:18
		// Arrange
		var imposto = new Imposto( "teste", 0.05, 200 );

		// Act

		// Assert
		assertEquals( "teste", imposto.getNome() );
		assertEquals( 0.05, imposto.getPercentual() );
	}

}
