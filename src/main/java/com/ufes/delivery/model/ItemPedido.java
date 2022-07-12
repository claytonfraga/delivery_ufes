package com.ufes.delivery.model;

import java.text.DecimalFormat;

import com.ufes.delivery.exception.OperacaoInvalidaException;

/**
 *
 * @author clayton
 */
public class ItemPedido {

	private Produto produto;
	private Pedido pedido;
	private double quantidade;
	private double valorUnitario;
	private double valorTotal;

	public ItemPedido( Pedido pedido, Produto produto, double quantidade ) {
		if( pedido == null ) {
			throw new OperacaoInvalidaException( "É necessário informar o pedido!" );
		}
		if( produto == null ) {
			throw new OperacaoInvalidaException( "Informe um produto valido!" );
		}
		this.pedido = pedido;
		this.produto = produto;
		this.quantidade = quantidade;
		obtemValorUnitario();
		calculaValorTotal();
	}

	private void calculaValorTotal() {
		this.valorTotal = valorUnitario * quantidade;
	}

	private void obtemValorUnitario() {
		this.valorUnitario = produto.getPrecoUnitario();
	}

	public String getNomeProduto() {
		return produto.getNome();
	}

	public int getCodigoProduto() {
		return produto.getCodigo();
	}

	public double getQuantidade() {
		return quantidade;
	}

	public int getNroPedido() {
		return pedido.getNumero();
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setProduto( Produto produto ) {
		this.produto = produto;
		obtemValorUnitario();
		calculaValorTotal();
	}

	public void setQuantidade( double quantidade ) {
		this.quantidade = quantidade;
		calculaValorTotal();
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat( "0.00" );
		return "Item: " + getNomeProduto() + ", quantidade: " + df.format( getQuantidade() ) + ", preço unitário: R$ " + df.format( getValorUnitario() ) + ", preço total do item: R$ " + df.format( getValorTotal() );
	}

}
