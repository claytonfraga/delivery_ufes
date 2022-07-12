/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package com.ufes.delivery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.visitor.IPedidoVisitor;
import com.ufes.delivery.visitor.PedidoVisitor;

/**
 *
 * @author Lucas dos Santos Carvalho
 */
public class LucasTests {

	public LucasTests() {

	}

	private ProdutoDAO dao;
	private Cliente cliente;
	private Estabelecimento estabelecimento;

	@BeforeEach
	void carregaDao() {
		dao = ProdutoDAO.getInstance();
	}

	@Test
	@DisplayName( "Preparar um pedido não pago." )
	void CT001() {
		// Horário de início: 16:37
		// Horário de témino: 16:53
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		Exception exception = null;
		// Act
		try {
			pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 1 ), 1 ) ); // inclui item no pedido
			pedido.concluir(); // conclui pedido
			pedido.preparar(); // prepara pedido
			pedido.sairParaEntrega(); // manda para entrega
			pedido.entregar(); // entrega o produto em rota de entrega
			pedido.avaliar( 5 ); // avalia
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception.getMessage(), is( "Este pedido não pode ser preparado no estado \'Aguardando pagamento\'" ) );
	}

	@Test
	@DisplayName( "Mandar para entrega um pedido não preparado." )
	void CT002() {
		// Horário de início: 16:53
		// Horário de témino: 17:02
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		Exception exception = null;
		// Act
		try {
			pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 1 ), 1 ) ); // inclui item no pedido
			pedido.concluir(); // conclui pedido
			pedido.pagar(); // paga um pedido concluido
			pedido.sairParaEntrega(); // manda para entrega
			pedido.entregar(); // entrega o produto em rota de entrega
			pedido.avaliar( 5 ); // avalia
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception.getMessage(), is( "Este pedido não pode ser enviado para entrega no estado \'Confirmado\'" ) );
	}

	@Test
	@DisplayName( "Entregar um pedido que não está em rota de entrega." )
	void CT003() {
		// Horário de início: 17:05
		// Horário de témino: 17:08
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		Exception exception = null;
		// Act
		try {
			pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 1 ), 1 ) ); // inclui item no pedido
			pedido.concluir(); // conclui pedido
			pedido.pagar(); // paga um pedido concluido
			pedido.preparar(); // prepara pedido
			pedido.entregar(); // entrega o produto em rota de entrega
			pedido.avaliar( 5 ); // avalia
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception.getMessage(), is( "Este pedido não pode ser entregue no estado \'Pronto para entrega\'" ) );
	}

	@Test
	@DisplayName( "Concluir um pedido após o pagamento." )
	void CT004() {
		// Horário de início: 17:10
		// Horário de témino: 17:12
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		Exception exception = null;
		// Act
		try {
			pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 1 ), 1 ) ); // inclui item no pedido
			pedido.concluir(); // conclui pedido
			pedido.pagar(); // paga um pedido concluido
			pedido.concluir(); // conclui pedido novamente
			pedido.preparar(); // prepara pedido
			pedido.sairParaEntrega(); // manda para entrega
			pedido.entregar(); // entrega o produto em rota de entrega
			pedido.avaliar( 5 ); // avalia
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception.getMessage(), is( "Este pedido não pode ser concluído no estado \'Confirmado\'" ) );
	}

	@Test
	@DisplayName( "Reembolsar um pedido não concluido." )
	void CT005() {
		// Horário de início: 17:13
		// Horário de témino: 17:15
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		Exception exception = null;
		// Act
		try {
			pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 1 ), 1 ) ); // inclui item no pedido
			pedido.reembolsar(); // reembolsa o pedido
			pedido.concluir(); // conclui pedido
			pedido.pagar(); // paga um pedido concluido
			pedido.preparar(); // prepara pedido
			pedido.sairParaEntrega(); // manda para entrega
			pedido.entregar(); // entrega o produto em rota de entrega
			pedido.avaliar( 5 ); // avalia
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception.getMessage(), is( "Este pedido não pode ser reembolsado no estado \'Novo\'" ) );
	}

	@Test
	@DisplayName( "Avaliar o programa com nota inferior a 1." )
	void CT006() {
		// Horário de início: 17:16
		// Horário de témino: 17:18
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		Exception exception = null;
		// Act
		try {
			pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 1 ), 1 ) ); // inclui item no pedido
			pedido.concluir(); // conclui pedido
			pedido.pagar(); // paga um pedido concluido
			pedido.preparar(); // prepara pedido
			pedido.sairParaEntrega(); // manda para entrega
			pedido.entregar(); // entrega o produto em rota de entrega
			pedido.avaliar( 0 ); // avalia
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception.getMessage(), is( "Informe uma nota entre 1 e 5" ) );
	}

	@Test
	@DisplayName( "Verificação do toString de TesteState em NovoState." )
	void CT007() {
		// Horário de início: 17:19
		// Horário de témino: 17:25
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		String res;
		// Act
		res = pedido.getEstado();
		// Assert
		Assertions.assertEquals( "Novo", res );
	}

	@Test
	@DisplayName( "Cancelando um pedido pronto para entrega." )
	void CT008() {
		// Horário de início: 17:26
		// Horário de témino: 17:38
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 10 ), 1 ) ); // inclui item no pedido
		pedido.concluir(); // conclui pedido
		pedido.pagar(); // paga um pedido concluido
		pedido.preparar(); // prepara pedido
		pedido.cancelar(); // cancela o pedido
		pedido.reembolsar(); // reembolsa o pedido
		pedido.avaliar( 5 ); // avalia
		// Assert
		assertThat( pedido.getItens().size(), is( 1 ) );
		Assertions.assertEquals( pedido.getValorReembolsado(), pedido.getValorFinal() );
		assertThat( cliente.getSaldo(), is( 1000.0 ) );
	}

	@Test
	@DisplayName( "Verifica se PedidoVisitor aceita parametro inválido ou apresenta mensagem de erro." )
	void CT009() {
		// Horário de início: 17:41
		// Horário de témino: 17:51
		// Arrange
		IPedidoVisitor visitor;
		Exception exception = null;
		// Act
		try {
			visitor = new PedidoVisitor( null );
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		assertThat( exception.getMessage(), is( "Instancia de pedido inválida!" ) );
	}

	@Test
	@DisplayName( "Validação dos dados exibidos no 'toString' de PedidoVisitor." )
	void CT010() {
		// Horário de início: 17:57
		// Horário de témino: 18:11
		// Arrange
		cliente = new Cliente( "Lucas", 1000.0 ); // cria cliente
		estabelecimento = new Estabelecimento( "Loja da Esquina" ); // cria estabelecimento
		Pedido pedido = new Pedido( 1, cliente, estabelecimento, LocalTime.now() ); // cria pedido
		String res;
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern( "HH:mm:ss" );
		DecimalFormat df = new DecimalFormat( "0.00" );
		// Act
		res = pedido.toString();
		// Assert
		Assertions.assertEquals( "\n-------------------------------------------------------------------------------------------------" + "\nPedido: " + pedido.getNumero() + ", data: " + pedido.getData().format( formatterData ) + ", horario: " + pedido.getHora().format( formatterHora ) + ", estado: "
				+ pedido.getEstado() + "\nCliente: " + pedido.getCliente() + "\nEstabelecimento: " + pedido.getEstabelecimento() + "\nItens do pedido:" + "\nValor total dos itens R$: " + df.format( pedido.getValorTotal() ) + "\n\nDescontos concedidos:" + "Descontos não concedidos"
				+ "\nValor total em descontos R$: " + df.format( pedido.getValorTotalDescontos() ) + "\n\nImpostos calculados:" + "Impostos não calculados" + "\nValor total em impostos R$: " + df.format( pedido.getValorTotalImpostos() ) + "\n\nValor total do pedido R$: "
				+ df.format( pedido.getValorFinal() ) + "\n\nEventos no pedido:" + "\n\t" + pedido.getEventos().get( 0 ).toString(), res );
	}
}
