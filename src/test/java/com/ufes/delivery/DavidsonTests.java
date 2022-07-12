/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufes.delivery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ufes.delivery.builder.CestaEconomicaBuilder;
import com.ufes.delivery.builder.DiretorCesta;
import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.model.Produto;

/**
 *
 * @author Davidson
 */
public class DavidsonTests {

	private ProdutoDAO dao;
	private Cliente cliente;
	private Estabelecimento vendedor;
	private DiretorCesta diretor;
	private Produto produto;

	@BeforeEach
	void carregaDao() {
		dao = ProdutoDAO.getInstance();
	}

	@Test
	@DisplayName( "Confirmar um pedido com uma quantidade de itens maior que a de estoque" )
	void CT001() {
		// Horário de início: 13:00
		// Horário de término: 13:20
		// Arrange
		cliente = new Cliente( "Davidson", 2000.0 );
		vendedor = new Estabelecimento( "Lojas Americanas" );
		Pedido pedido = new Pedido( 1, cliente, vendedor, LocalTime.of( 12, 00 ) );
		Exception exception = null;

		// Act
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 20 ), 10 ) );
		pedido.concluir();
		try {
			pedido.pagar();
		} catch ( Exception e ) {
			exception = e;
		}

		// Assert
		assertThat( exception, instanceOf( OperacaoInvalidaException.class ) );
		// O código funciona, porém uma dica para melhorar a legibilidade do usuário:
		// Código da DAO alterado, pois a palavra "insuficiente" estava colada ao número
		// que antecede
		assertThat( exception.getMessage(), is( "Quantiade (10.0) do produto 20 insuficiente em estoque (7.0)" ) );
		assertThat( pedido.getItens(), hasSize( 1 ) );
	}

	@Test
	@DisplayName( "Confirmar se a consulta está buscando o nome corretamente" )
	void CT002() {
		// Horário de início: 13:20
		// Horário de término: 13:35
		// Arrange
		String nomeTeste = "Achocolatado em Pó";

		// Act
		produto = dao.buscaProdutoPorNome( nomeTeste );

		// Assert
		assertThat( nomeTeste, equalTo( produto.getNome() ) );

	}

	@Test
	@DisplayName( "Induzir ao erro para testar se o método 'buscaProdutoPornome' lança a execesão" )
	void CT003() {
		// Horário de início: 13:35
		// Horário de término: 13:50
		// Arrange
		String nomeTeste = "Achocolata em Pó";
		Exception exception = null;

		// Act
		try {
			produto = dao.buscaProdutoPorNome( nomeTeste );
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception.getMessage(), is( "Produto " + nomeTeste + " não encontrado!" ) );

	}

	@Test
	@DisplayName( "Confirmar se a consulta está buscando o codigo corretamente" )
	void CT004() {
		// Horário de início: 13:50
		// Horário de término: 14:05
		// Arrange
		Integer intTeste = 19;

		// Act
		produto = dao.buscaProdutoPorCodigo( intTeste );

		// Assert
		assertThat( intTeste, equalTo( produto.getCodigo() ) );

	}

	@Test
	@DisplayName( "Induzir ao erro para testar se o método 'buscaProdutoPorCodigo' lança a execesão" )
	void CT005() {
		// Horário de início: 14:05
		// Horário de término: 14:20
		// Arrange
		Integer intTeste = 10000;
		Exception exception = null;

		// Act
		try {
			produto = dao.buscaProdutoPorCodigo( intTeste );
		} catch ( Exception e ) {
			exception = e;
		}
		// Assert
		assertThat( exception.getMessage(), is( "Produto com o código " + intTeste + " não encontrado!" ) );

	}

	@Test
	@DisplayName( "Confirmar se o método 'baixaEstoque' está reduzindo os produtos no estoque" )
	void CT006() {
		// Horário de início: 14:20
		// Horário de término: 14:45
		// Arrange
		Integer codTeste = 19;
		Integer quantTeste = 3;

		// Act
		dao.baixaEstoque( codTeste, quantTeste );

		// Assert
		assertThat( dao.buscaProdutoPorCodigo( codTeste ).getQuantidadeEmEstoque(), equalTo( 2.0 ) );

	}

	@Test
	@DisplayName( "Induzir ao erro para ver se o método 'baixaEstoque' está lançando a execesão" )
	void CT007() {
		// Horário de início: 14:45
		// Horário de término: 14:55
		// Arrange
		Integer codTeste = 19;
		Double quantTeste = 6.0;
		Exception exception = null;

		// Act
		try {
			dao.baixaEstoque( codTeste, quantTeste );
		} catch ( Exception e ) {
			exception = e;
		}

		// Assert
		assertThat( exception.getMessage(), is( "Quantiade (" + quantTeste + ") do produto " + codTeste + " insuficiente em estoque (" + dao.buscaProdutoPorCodigo( codTeste ).getQuantidadeEmEstoque() + ")" ) );

	}

	@Test
	@DisplayName( "Verificar se o método 'adicionaEstoque' está adicionando corretamente" )
	void CT008() {
		// Horário de início: 14:55
		// Horário de término: 15:10
		// Arrange
		Integer codTeste = 21;
		Double quantTeste = 6.0;

		// Act
		dao.adicionaEstoque( codTeste, quantTeste );

		// Assert
		assertThat( dao.buscaProdutoPorCodigo( codTeste ).getQuantidadeEmEstoque(), equalTo( 18.0 ) );

	}

	@Test
	@DisplayName( "Verificar se o método 'getProdutos' está retornando todos os produtos cadastrados" )
	void CT009() {
		// Horário de início: 15:10
		// Horário de término: 15:30
		// Arrange
		Integer quantTeste = 37;
		List<Produto> listaTeste;

		// Act
		listaTeste = dao.getProdutos();

		// Assert
		assertThat( listaTeste, hasSize( quantTeste ) );
	}

	@Test
	@DisplayName( "Verificar se a Ceste Enconima foi criada com todos os pedidos" )
	void CT010() {
		// Horário de início: 15:30
		// Horário de término: 15:50
		// Arrange
		CestaEconomicaBuilder builder = new CestaEconomicaBuilder( cliente, vendedor );

		diretor = new DiretorCesta();

		// Act

		// Assert
		assertThrows( UnsupportedOperationException.class, () -> {
			diretor.build( builder );
		} );
	}

}
