package com.ufes.delivery.model.state;

import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public class PedidoEmRotaEntregaState extends PedidoState {

	public PedidoEmRotaEntregaState( Pedido pedido ) {
		super( pedido, "Pedido em rota de entrega" );
	}

	@Override
	public void entregar() {
		this.pedido.setState( new PedidoEntregueState( this.pedido ) );
	}

}
