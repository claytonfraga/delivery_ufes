package com.ufes.delivery.model.state;

import java.time.LocalDateTime;

import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.model.EventoPedido;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public abstract class PedidoState {

	protected Pedido pedido;
	private String nomeEstado;

	public PedidoState( Pedido pedido, String nomeEstado ) {
		this.pedido = pedido;
		this.nomeEstado = nomeEstado;
		pedido.add( new EventoPedido( LocalDateTime.now(), nomeEstado ) );
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void pagar() {
		throw new OperacaoInvalidaException( getMensagemFalha( getNomeEstado(), "pago" ) );
	}

	public void cancelar() {
		throw new OperacaoInvalidaException( getMensagemFalha( getNomeEstado(), "cancelado" ) );
	}

	public void preparar() {
		throw new OperacaoInvalidaException( getMensagemFalha( getNomeEstado(), "preparado" ) );
	}

	public void sairParaEntrega() {
		throw new OperacaoInvalidaException( getMensagemFalha( getNomeEstado(), "enviado para entrega" ) );
	}

	public void entregar() {
		throw new OperacaoInvalidaException( getMensagemFalha( getNomeEstado(), "entregue" ) );
	}

	public void concluir() {
		throw new OperacaoInvalidaException( getMensagemFalha( getNomeEstado(), "concluído" ) );
	}

	public void reembolsar() {

		throw new OperacaoInvalidaException( getMensagemFalha( getNomeEstado(), "reembolsado" ) );
	}

	public void incluir( ItemPedido item ) {
		throw new OperacaoInvalidaException( "Itens não podem ser adicionados ao pedido no estado \'" + getNomeEstado() + "\'" );
	}

	public void removeItem( String nome ) {
		throw new OperacaoInvalidaException( "Itens não podem ser removidos do pedido no estado \'" + getNomeEstado() + "\'" );
	}

	public void avaliar( int nota ) {
		if( getNomeEstado().toLowerCase().contains( "entregue" ) || getNomeEstado().toLowerCase().contains( "reembolsado" ) ) {
			if( nota >= 1 && nota <= 5 ) {
				pedido.add( new EventoPedido( LocalDateTime.now(), "Avaliação do pedido: " + nota ) );
			} else {
				throw new OperacaoInvalidaException( "Informe uma nota entre 1 e 5" );
			}
		} else {
			throw new OperacaoInvalidaException( getMensagemFalha( getNomeEstado(), "avaliado" ) );
		}
	}

	private String getMensagemFalha( String nomeEstado, String estado ) {
		return "Este pedido não pode ser " + estado + " no estado \'" + nomeEstado + "\'";
	}

	@Override
	public String toString() {
		return getNomeEstado();
	}
}
