package com.ufes.delivery.model.state;

import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public class PedidoConfirmadoState extends PedidoState {

	public PedidoConfirmadoState( Pedido pedido ) {
		super( pedido, "Confirmado" );
	}

	@Override
	public void preparar() {
		this.pedido.setState( new PedidoProntoEntregaState( pedido ) );
	}

	@Override
	public void cancelar() {
		this.pedido.setState( new PedidoCanceladoState( this.pedido, "Pedido confirmado foi cancelado pelo estabelecimento" ) );
	}
}
