package com.ufes.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.builder.CestaTopBuilder;
import com.ufes.delivery.builder.DiretorCesta;
import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;

public class CestaTopBuilderTest {

	public CestaTopBuilderTest() {}

	private ProdutoDAO dao;
	private Cliente cliente;
	private Estabelecimento vendedor;
	private DiretorCesta diretor;
	private Pedido pedido;

	@BeforeEach
	public void carregaDao() {
		dao = ProdutoDAO.getInstance();
	}

	@Test
	@DisplayName( "Cria um pedido do tipo Cesta básica TOP usando o Builder" )
	public void CT001() {

		// Arrange
		cliente = new Cliente( "Fulano", 1000.0 );
		vendedor = new Estabelecimento( "Casa XPTO" );
		diretor = new DiretorCesta();
		pedido = diretor.build( new CestaTopBuilder( cliente, vendedor ) );

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 16 ), 0.6 ) );

		pedido.concluir();
		pedido.pagar();
		pedido.preparar();
		pedido.sairParaEntrega();
		pedido.entregar();
		pedido.avaliar( 5 );

		// Assert
		assertEquals( 17, pedido.getItens().size() );
		assertEquals( LocalDate.now(), pedido.getData() );
		assertEquals( LocalTime.of( 14, 00 ), pedido.getHora() );
		assertEquals( "Fulano", pedido.getCliente().getNome() );
		assertEquals( 772.10, pedido.getValorFinal(), 0.01 );
		assertEquals( "Pedido entregue ao cliente", pedido.getEstado() );
	}

	@Test
	@DisplayName( "Não deve permitir o pagamento de um pedido que não foi concluido (pedido Novo)" )
	void CT002() {

		// Arrange
		cliente = new Cliente( "Fulano", 1000.0 );
		vendedor = new Estabelecimento( "Casa XPTO" );
		diretor = new DiretorCesta();

		pedido = diretor.build( new CestaTopBuilder( cliente, vendedor ) );

		// Act
		OperacaoInvalidaException thrown = assertThrows( OperacaoInvalidaException.class, () -> pedido.pagar(), "Espera-se que pagar() levante uma exceção" );

		// Assert
		assertEquals( "Este pedido não pode ser pago no estado 'Novo'", thrown.getMessage() );
	}

}
