package com.ufes.delivery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;

public class TesteState {

	public TesteState() {}

	private ProdutoDAO dao;
	private Cliente cliente;
	private Estabelecimento vendedor;

	@BeforeEach
	void carregaDao() {
		dao = ProdutoDAO.getInstance();
	}

	@Test
	@DisplayName( "Cancelar um pedido que está aguardando pagamento" )
	void CT001() {
		// Horário de início: 8:48
		// Horário de témino: 9:12
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 7 ), 2 ) );
		pedido.concluir();
		pedido.cancelar();
		pedido.reembolsar();
		pedido.avaliar( 3 );

		// Assert
		assertThat( pedido.getItens().size(), is( 1 ) );
		assertThat( pedido.getValorReembolsado(), is( 0.0 ) );
		assertThat( cliente.getSaldo(), is( 2000.0 ) );
	}

	@Test
	@DisplayName( "Cancelar um pedido que está confirmado" )
	void CT002() {
		// Horário de início: 9:15
		// Horário de témino: 9:32
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 32 ), 1 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 14 ), 2 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 20 ), 1 ) );
		pedido.concluir();
		pedido.pagar();
		pedido.cancelar();
		pedido.reembolsar();
		pedido.avaliar( 4 );

		// Assert
		assertThat( pedido.getItens().size(), is( 3 ) );
		assertThat( pedido.getValorReembolsado(), is( ( ( 2 * 3.15 ) + 3.69 + 1.29 ) + pedido.getValorTotalImpostos() - pedido.getValorTotalDescontos() ) );
	}

	@Test
	@DisplayName( "Concluir um novo pedido com a lista de itens vazia" )
	void CT003() {
		// Horário de início: 9:33
		// Horário de témino: 9:39
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		try {
			pedido.concluir();
			pedido.pagar();
			pedido.preparar();
			pedido.sairParaEntrega();
			pedido.entregar();
			pedido.avaliar( 5 );
		} catch ( Exception e ) {
			exception = e;
		}

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "O pedido não pode ser confirmado sem nenhum item na lista" ) );
		assertThat( pedido.getItens(), empty() );
		assertThat( pedido.getValorFinal(), is( 0.0 ) );
	}

	@Test
	@DisplayName( "Cancelar um pedido cancelado após ser confirmado" )
	void CT004() {
		// Horário de início: 9:40
		// Horário de témino: 9:54
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 32 ), 1 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 14 ), 2 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 20 ), 1 ) );
		pedido.concluir();
		pedido.pagar();
		pedido.cancelar();
		try {
			pedido.cancelar();
		} catch ( Exception e ) {
			exception = e;
		}
		pedido.reembolsar();
		pedido.avaliar( 5 );

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "Este pedido não pode ser cancelado no estado \'Pedido confirmado foi cancelado pelo estabelecimento\'" ) );
		assertThat( pedido.getItens(), hasSize( 3 ) );
		assertThat( pedido.getValorTotal(), is( ( 2 * 3.15 ) + 3.69 + 1.29 ) );
		assertThat( pedido.getValorReembolsado(), is( ( ( 2 * 3.15 ) + 3.69 + 1.29 ) + pedido.getValorTotalImpostos() - pedido.getValorTotalDescontos() ) );
		assertThat( cliente.getSaldo(), is( 2000.0 ) );
	}

	@Test
	@DisplayName( "Cancelar um pedido que está em rota de entrega" )
	void CT005() {
		// Horário de início: 9:55
		// Horário de témino: 10:03
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 7 ), 2 ) );
		pedido.concluir();
		pedido.pagar();
		pedido.preparar();
		pedido.sairParaEntrega();
		try {
			pedido.cancelar();
		} catch ( Exception e ) {
			exception = e;
		}
		pedido.entregar();
		pedido.avaliar( 5 );

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "Este pedido não pode ser cancelado no estado \'Pedido em rota de entrega\'" ) );
		assertThat( pedido.getItens(), hasSize( 1 ) );
		assertThat( pedido.getValorTotal(), is( 2 * 2.65 ) );
		assertThat( cliente.getSaldo(), is( 2000 - pedido.getValorFinal() ) );
	}

	@Test
	@DisplayName( "Pagar um pedido cancelado pelo cliente" )
	void CT006() {
		// Horário de início: 10:04
		// Horário de témino: 10:09
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 7 ), 2 ) );
		pedido.cancelar();
		try {
			pedido.pagar();
		} catch ( Exception e ) {
			exception = e;
		}
		pedido.reembolsar();
		pedido.avaliar( 3 );

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "Este pedido não pode ser pago no estado \'Novo pedido cancelado pelo cliente\'" ) );
		assertThat( pedido.getItens(), hasSize( 1 ) );
		assertThat( pedido.getValorTotal(), is( 2 * 2.65 ) );
		assertThat( cliente.getSaldo(), is( 2000.0 ) );
	}

	@Test
	@DisplayName( "Excluir um item do pedido quando o pedido está aguardadno pagamento" )
	void CT007() {
		// Horário de início: 10:10
		// Horário de témino: 10:14
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 32 ), 1 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 14 ), 2 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 20 ), 1 ) );
		pedido.concluir();
		try {
			pedido.removeItem( "Sal Refinado" );
		} catch ( Exception e ) {
			exception = e;
		}
		pedido.pagar();
		pedido.preparar();
		pedido.sairParaEntrega();
		pedido.entregar();
		pedido.avaliar( 5 );

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "Itens não podem ser removidos do pedido no estado \'Aguardando pagamento\'" ) );
		assertThat( pedido.getItens(), hasSize( 3 ) );
	}

	@Test
	@DisplayName( "Incluir um item no pedido quando o pedido está aguardando pagamento" )
	void CT008() {
		// Horário de início: 10:15
		// Horário de témino: 10:20
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 32 ), 1 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 14 ), 2 ) );
		pedido.concluir();
		try {
			pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 20 ), 1 ) );
		} catch ( Exception e ) {
			exception = e;
		}
		pedido.pagar();
		pedido.preparar();
		pedido.sairParaEntrega();
		pedido.entregar();
		pedido.avaliar( 5 );

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "Itens não podem ser adicionados ao pedido no estado \'Aguardando pagamento\'" ) );
		assertThat( pedido.getItens(), hasSize( 2 ) );
	}

	@Test
	@DisplayName( "Avaliar um pedido no estado de confirmado" )
	void CT009() {
		// Horário de início: 10:25
		// Horário de témino: 10:27
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 32 ), 1 ) );
		pedido.concluir();
		pedido.pagar();
		try {
			pedido.avaliar( 5 );
		} catch ( Exception e ) {
			exception = e;
		}

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "Este pedido não pode ser avaliado no estado \'Confirmado\'" ) );

	}

	@Test
	@DisplayName( "Avaliar um pedido com um valor maior que 5 (Teste de valor limite" )
	void CT010() {
		// Horário de início: 10:30
		// Horário de témino: 10:
		// Arrange
		cliente = new Cliente( "Tarcisio", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 32 ), 1 ) );
		pedido.concluir();
		pedido.pagar();
		pedido.preparar();
		pedido.sairParaEntrega();
		pedido.entregar();
		try {
			pedido.avaliar( 6 );
		} catch ( Exception e ) {
			exception = e;
		}

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "Informe uma nota entre 1 e 5" ) );
	}

}
