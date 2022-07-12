package com.ufes.delivery.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.imposto.Imposto;
import com.ufes.delivery.model.state.PedidoNovoState;
import com.ufes.delivery.model.state.PedidoState;
import com.ufes.delivery.visitor.IPedidoVisitor;
import com.ufes.delivery.visitor.PedidoVisitor;

/**
 *
 * @author clayton
 */
public final class Pedido {

	private final int numero;
	private final LocalDate data;
	private final LocalTime hora;
	private final Cliente cliente;
	private final ArrayList<Imposto> impostos;
	private final ArrayList<Desconto> descontosConcedidos;
	private final ArrayList<ItemPedido> itens;
	private final Estabelecimento estabelecimento;
	private PedidoState state;
	private double valorReembolsado;
	private final ArrayList<EventoPedido> eventosPedido = new ArrayList<>();
	private IPedidoVisitor visitor;

	public Pedido( int numero, Cliente cliente, Estabelecimento estabelecimento, LocalTime horario ) {
		this.numero = numero;
		this.data = LocalDate.now();
		this.hora = horario;
		this.cliente = cliente;
		this.estabelecimento = estabelecimento;
		itens = new ArrayList<>();
		descontosConcedidos = new ArrayList<>();
		impostos = new ArrayList<>();
		state = new PedidoNovoState( this );
		this.setVisitor( new PedidoVisitor( this ) );
	}

	public void setValorReembolsado( double valorReembolsado ) {
		if( !state.getNomeEstado().toLowerCase().contains( "cancelado" ) ) {
			throw new OperacaoInvalidaException( "Para ser reembolsado o pedido precisa estar cancelado!" + "\nO pedido está " + this.state.getNomeEstado() );
		}
		this.valorReembolsado = valorReembolsado;
	}

	public void setVisitor( IPedidoVisitor visitor ) {
		if( visitor == null ) {
			throw new OperacaoInvalidaException( "Informe um Visitante válido" );
		}
		this.visitor = visitor;
	}

	public double getValorReembolsado() {
		return valorReembolsado;
	}

	public double getValorFinal() {
		double valorTotalPago = 0;
		if( !state.getNomeEstado().equalsIgnoreCase( "novo" ) ) {
			valorTotalPago = this.getValorTotal() - getValorTotalDescontos() + getValorTotalImpostos();
		}
		return valorTotalPago;
	}

	public void setState( PedidoState state ) {
		this.state = state;
	}

	public void add( EventoPedido evento ) {
		if( evento == null ) {
			throw new OperacaoInvalidaException( "Informe um evento válido!" );
		}
		eventosPedido.add( evento );
	}

	private boolean existeDesconto( Desconto desconto ) {
		for( Desconto desc : this.getDescontosConcedidos() ) {
			if( desc.equals( desconto ) ) {
				return true;
			}
		}
		return false;
	}

	public void add( Desconto desconto ) {
		if( desconto == null ) {
			throw new OperacaoInvalidaException( "Informe um desconto válido!" );
		}
		if( !existeDesconto( desconto ) ) {
			this.descontosConcedidos.add( desconto );
		} else {
			throw new OperacaoInvalidaException( "O pedido só aceita uma unidade do tipo de desconto." );
		}
	}

	public void add( Imposto imposto ) {
		if( imposto == null ) {
			throw new OperacaoInvalidaException( "Informe um imposto válido!" );
		}
		this.impostos.add( imposto );
	}

	public void add( ItemPedido item ) {
		itens.add( item );
	}

	public double getValorTotalImpostos() {
		double valorTotalImpostos = 0;
		for( Imposto imposto : impostos ) {
			valorTotalImpostos += imposto.getValor();

		}
		return valorTotalImpostos;
	}

	public double getValorTotalDescontos() {
		double valorTotalDescontos = 0;
		for( Desconto desconto : descontosConcedidos ) {
			valorTotalDescontos += desconto.getValor();

		}
		return valorTotalDescontos;
	}

	public LocalDate getData() {
		return data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public double getValorTotal() {
		double valorTotal = 0;
		for( ItemPedido item : itens ) {
			valorTotal += item.getValorTotal();
		}
		return valorTotal;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public int getNumero() {
		return numero;
	}

	public List<EventoPedido> getHistoricoPedido() {
		return eventosPedido;
	}

	public void pagar() {
		state.pagar();
	}

	public void cancelar() {
		state.cancelar();
	}

	public void preparar() {
		state.preparar();
	}

	public void sairParaEntrega() {
		state.sairParaEntrega();
	}

	public void entregar() {
		state.entregar();
	}

	public void concluir() {
		state.concluir();
	}

	public void reembolsar() {
		state.reembolsar();
	}

	public void incluir( ItemPedido item ) {
		state.incluir( item );
	}

	public void removeItem( String nome ) {
		state.removeItem( nome );
	}

	public void avaliar( int nota ) {
		state.avaliar( nota );
	}

	public String getEstado() {
		return state.getNomeEstado();
	}

	public List<Desconto> getDescontosConcedidos() {
		return Collections.unmodifiableList( descontosConcedidos );
	}

	public List<Imposto> getImpostos() {
		return Collections.unmodifiableList( impostos );
	}

	public List<EventoPedido> getEventos() {
		return Collections.unmodifiableList( eventosPedido );
	}

	public LocalTime getHora() {
		return hora;
	}

	@Override
	public String toString() {
		return visitor.toString();
	}

}
