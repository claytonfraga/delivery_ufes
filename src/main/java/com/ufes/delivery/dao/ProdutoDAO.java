package com.ufes.delivery.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.model.Produto;

/**
 *
 * @author clayton
 */
public class ProdutoDAO {

	private ArrayList<Produto> produtos;
	private static ProdutoDAO instance;

	private ProdutoDAO() {
		produtos = new ArrayList<>();
		produtos.add( new Produto( 1, "Achocolatado em Pó", 6, 11.99 ) );
		produtos.add( new Produto( 2, "Açúcar Refinado", 15, 29.9 ) );
		produtos.add( new Produto( 3, "Arroz Agulhinha Tipo 1", 8, 24.10 ) );
		produtos.add( new Produto( 4, "Biscoito Recheado (Sabores)", 5, 3.78 ) );
		produtos.add( new Produto( 5, "Biscoito Cream Cracker", 15, 4.95 ) );
		produtos.add( new Produto( 6, "Café Torrado e Moído", 7, 19.50 ) );
		produtos.add( new Produto( 7, "Creme de Leite", 13, 2.65 ) );
		produtos.add( new Produto( 8, "Ervilha em Conserva", 9, 19.30 ) );
		produtos.add( new Produto( 9, "Farinha de Trigo", 7, 3.55 ) );
		produtos.add( new Produto( 10, "Farinha de Mandioca Temperada", 6, 2.70 ) );
		produtos.add( new Produto( 11, "Feijão Carioca Tipo 1", 6, 6.65 ) );
		produtos.add( new Produto( 12, "Fubá Mimoso", 14, 3.71 ) );
		produtos.add( new Produto( 13, "Leite em Pó Integral", 5, 25.79 ) );
		produtos.add( new Produto( 14, "Macarrão Espaguete", 13, 3.15 ) );
		produtos.add( new Produto( 15, "Macarrão Parafuso", 10, 5.95 ) );
		produtos.add( new Produto( 16, "Mistura para Bolo (Sabores)", 12, 41.10 ) );
		produtos.add( new Produto( 17, "Óleo de Soja", 13, 7.89 ) );
		produtos.add( new Produto( 18, "Pó para Gelatina (Sabores)", 5, 1.45 ) );
		produtos.add( new Produto( 19, "Polpa de Tomate", 5, 33.95 ) );
		produtos.add( new Produto( 20, "Sal Refinado", 7, 1.29 ) );
		produtos.add( new Produto( 21, "Sardinha", 12, 18.80 ) );
		produtos.add( new Produto( 22, "Tempero Completo/ Alho e Sal", 15, 2.57 ) );
		produtos.add( new Produto( 23, "Amaciante Líquido (Fragrâncias)", 5, 10.48 ) );
		produtos.add( new Produto( 24, "Creme Dental", 9, 1.30 ) );
		produtos.add( new Produto( 25, "Desinfetante (Fragrâncias)", 10, 27.99 ) );
		produtos.add( new Produto( 26, "Papel Higiênico", 14, 11.19 ) );
		produtos.add( new Produto( 27, "Sabonete (Fragrâncias)", 11, 1.89 ) );
		produtos.add( new Produto( 28, "1 KG de Carne Bovina", 11, 31.89 ) );
		produtos.add( new Produto( 29, "1 KG de Carne Suína", 11, 21.39 ) );
		produtos.add( new Produto( 30, "1 KG de Carne de Frango", 11, 14.39 ) );
		produtos.add( new Produto( 31, "1 dúzia de ovos", 16, 7.19 ) );
		produtos.add( new Produto( 32, "Leite Integral UHT 1 Litro", 100, 3.69 ) );
		produtos.add( new Produto( 33, "Manteiga com Sal 100G", 32, 4.98 ) );
		produtos.add( new Produto( 34, "1 Kg Pão Francês", 20, 7.00 ) );
		produtos.add( new Produto( 35, "1 Kg batata", 200, 1.71 ) );
		produtos.add( new Produto( 36, "1 Kg tomate", 120, 6.00 ) );
		produtos.add( new Produto( 37, "1 dúzia de banana", 20, 2.50 ) );
	}

	public static ProdutoDAO getInstance() {
		if( instance == null ) {
			instance = new ProdutoDAO();
		}
		return instance;
	}

	private void verificaQuantidade( double quantidade ) {
		if( quantidade <= 0 ) {
			throw new OperacaoInvalidaException( "Quantidade deve ser > 0" );
		}
	}

	public void adicionaEstoque( int codigo, double quantidade ) {
		verificaQuantidade( quantidade );
		Produto produto = buscaProdutoPorCodigo( codigo );
		produto.incrementaEstoque( quantidade );
	}

	private double getQuantidadeEmEstoque( int codigo ) {
		return buscaProdutoPorCodigo( codigo ).getQuantidadeEmEstoque();
	}

	public void baixaEstoque( int codigo, double quantidade ) {
		verificaQuantidade( quantidade );
		Produto produto = buscaProdutoPorCodigo( codigo );
		double quantidadeEmEstoque = getQuantidadeEmEstoque( codigo );
		if( quantidadeEmEstoque >= quantidade ) {
			produto.decrementaEstoque( quantidade );
		} else {
			throw new OperacaoInvalidaException( "Quantiade (" + quantidade + ") do produto " + codigo + " insuficiente em estoque (" + quantidadeEmEstoque + ")" );
		}

	}

	public Produto buscaProdutoPorNome( String nome ) {
		for( Produto produto : produtos ) {
			if( produto.getNome().equalsIgnoreCase( nome ) ) {
				return produto;
			}
		}
		throw new OperacaoInvalidaException( "Produto " + nome + " não encontrado!" );
	}

	public Produto buscaProdutoPorCodigo( int codigo ) {
		for( Produto produto : produtos ) {
			if( produto.getCodigo() == codigo ) {
				return produto;
			}
		}
		throw new OperacaoInvalidaException( "Produto com o código " + codigo + " não encontrado!" );
	}

	public List<Produto> getProdutos() {
		return Collections.unmodifiableList( produtos );
	}

	public int getNroProdutos() {
		return produtos.size();
	}

}
